package pl.wsei.dto.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.wsei.dto.DtoConverter;
import pl.wsei.model.authorization.User;

@Component
public class RegisterConverter implements DtoConverter<RegisterDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterDto createFrom(User entity) {
        return null;
    }

    @Override
    public User createFrom(RegisterDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return user;
    }
}
