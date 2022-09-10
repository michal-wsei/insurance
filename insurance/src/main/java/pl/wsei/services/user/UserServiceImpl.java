package pl.wsei.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.wsei.controller.exceptions.AppException;
import pl.wsei.controller.exceptions.user.EmailException;
import pl.wsei.model.authorization.Authority;
import pl.wsei.model.authorization.AuthorityType;
import pl.wsei.model.authorization.User;
import pl.wsei.model.authorization.UserAuthority;
import pl.wsei.repository.AuthorityRepository;
import pl.wsei.repository.UserRepository;
import pl.wsei.security.JwtTokenProvider;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
                           AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }


    @Override
    public String login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public void register(User user, AuthorityType authority) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailException("Email address already in use!");
        }

        Authority userAuthority = authorityRepository.findByName(authority)
                .orElseThrow(() -> new AppException("User authority not found."));

        //add a client authority for each registered user
        if (authority != AuthorityType.CLIENT) {
            Authority clientAuthority = authorityRepository.findByName(AuthorityType.CLIENT)
                    .orElseThrow(() -> new AppException("User authority not found."));

            user.getUserAuthorities().add(new UserAuthority(user, clientAuthority));
        }
        user.getUserAuthorities().add(new UserAuthority(user, userAuthority));

        userRepository.save(user);
    }
}
