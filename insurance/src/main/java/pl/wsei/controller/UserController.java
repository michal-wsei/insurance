package pl.wsei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.wsei.controller.responses.ApiResponse;
import pl.wsei.controller.responses.JwtAuthenticationResponse;
import pl.wsei.dto.DtoConverter;
import pl.wsei.dto.authentication.LoginDto;
import pl.wsei.dto.authentication.RegisterConverter;
import pl.wsei.dto.authentication.RegisterDto;
import pl.wsei.model.authorization.User;
import pl.wsei.resource.EmailMessage;
import pl.wsei.services.user.UserService;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final DtoConverter<RegisterDto, User> registerConverter;

    @Autowired
    public UserController(UserService userService, RegisterConverter registerConverter) {
        this.userService = userService;
        this.registerConverter = registerConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String jwt = userService.login(loginDto.getEmail(), loginDto.getPassword());

        RestTemplate restTemplate = new RestTemplate();

        String subject = "Zalogowano w Insurance!";
        String message = String.format("Witaj %s! Właśnie zalogowałeś się do swojego konta w Insurance. Jeżeli to nie ty....", loginDto.getEmail());
        EmailMessage email = new EmailMessage(loginDto.getEmail(), subject, message);

        String response = restTemplate.postForObject("http://server-email-sender:8081/send-email", email, String.class);
        log.info(response);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDTO) throws JSONException {
        User user = registerConverter.createFrom(registerDTO);
        this.userService.register(user, registerDTO.getAuthority());

        RestTemplate restTemplate = new RestTemplate();

        String subject = "Witaj w Insurance!";
        String message = String.format("Witaj %s! Dziękujemy za rejestrację w Insurance.", registerDTO.getName());
        EmailMessage email = new EmailMessage(registerDTO.getEmail(), subject, message);

        String response = restTemplate.postForObject("http://server-email-sender:8081/send-email", email, String.class);
        log.info(response);

        return ResponseEntity.ok(new ApiResponse(true, "Successfully registered!"));
    }
}
