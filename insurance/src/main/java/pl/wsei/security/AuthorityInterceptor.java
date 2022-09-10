package pl.wsei.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import pl.wsei.annotations.IsClient;
import pl.wsei.annotations.IsInsuranceOwner;
import pl.wsei.annotations.IsAgent;
import pl.wsei.services.security.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {
    private final SecurityService securityService;
    private final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Autowired
    public AuthorityInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IsAgent isAgent = handlerMethod.getMethodAnnotation(IsAgent.class);
        IsInsuranceOwner isInsuranceOwner = handlerMethod.getMethodAnnotation(IsInsuranceOwner.class);
        IsClient isClient = handlerMethod.getMethodAnnotation(IsClient.class);

        if (isAgent != null) {
            if (securityService.isAgent())
                return true;
        }

        if (isClient != null) {
            if (securityService.isClient())
                return true;
        }

        /* Get a map of request attributes. */
        Map<String, String> attributes =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (isInsuranceOwner != null) {
            logger.info("picked up an isInsuranceOwner.");
            int insuranceId = Integer.parseInt(attributes.get(isInsuranceOwner.pathVariable()));
            logger.info("knows that the insurance it has been called upon is insuranceId={}.", insuranceId);

            if (securityService.isInsuranceOwner(insuranceId)) {
                return true;
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }
        }

        return true;
    }
}
