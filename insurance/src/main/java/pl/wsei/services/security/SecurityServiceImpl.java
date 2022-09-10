package pl.wsei.services.security;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.wsei.model.Insurance;
import pl.wsei.model.authorization.AuthorityType;
import pl.wsei.model.authorization.User;
import pl.wsei.model.generic.BaseEntity;
import pl.wsei.repository.UserRepository;
import pl.wsei.security.UserPrincipal;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserPrincipal> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserPrincipal) {
            return Optional.of((UserPrincipal) principal);
        }

        return Optional.empty();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Optional<UserPrincipal> optionalUser = getCurrentUser();

        if (optionalUser.isEmpty()) {
            /* Return an empty immutable list. */
            return Collections.emptyList();
        }

        return optionalUser.get().getAuthorities();
    }

    @Override
    public boolean isAgent() {
        return this.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(AuthorityType.AGENT.name()));
    }

    @Override
    public boolean isClient() {
        return this.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(AuthorityType.CLIENT.name()));
    }

    @Override
    public boolean isInsuranceOwner(int insuranceId) {
        /* Pre-emptive return if the user is not a agent. */
        if (!isAgent()) {
            return false;
        }

        val optUser = getCurrentUser();
        if (optUser.isEmpty()) {
            return false;
        }

        UserPrincipal userPrincipal = optUser.get();
        int userId = userPrincipal.getId();

        User user = userRepository.getOne(userId);
        logger.info("isCourseOwner: user '{}' with for userId={} knocks at the door.", user.getName(), userId);

        Set<Insurance> insurance = user.getInsurance();

        logger.info(
                "isCourseOwner: {}'s set of insurance is {}.",
                user.getName(),
                insurance.stream().map(BaseEntity::getId).collect(Collectors.toList())
        );

        return insurance.stream().anyMatch(c -> c.getId() == insuranceId);
    }
}
