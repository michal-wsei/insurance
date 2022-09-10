package pl.wsei.dto.authentication;

import org.springframework.stereotype.Component;
import pl.wsei.dto.DtoConverter;
import pl.wsei.model.authorization.User;

@Component
public class LoginConverter implements DtoConverter<LoginDto, User> {

    @Override
    public LoginDto createFrom(User entity) {
        return null;
    }

    @Override
    public User createFrom(LoginDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
