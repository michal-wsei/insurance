package pl.wsei.services.user;

import pl.wsei.model.authorization.AuthorityType;
import pl.wsei.model.authorization.User;

public interface UserService {
    String login(String email, String password);

    void register(User user, AuthorityType authority);
}
