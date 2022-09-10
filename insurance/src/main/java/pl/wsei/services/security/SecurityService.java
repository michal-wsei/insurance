package pl.wsei.services.security;

import org.springframework.security.core.GrantedAuthority;
import pl.wsei.security.UserPrincipal;

import java.util.Collection;
import java.util.Optional;

public interface SecurityService {
    Optional<UserPrincipal> getCurrentUser();

    Collection<? extends GrantedAuthority> getAuthorities();

    boolean isAgent();

    boolean isClient();

    boolean isInsuranceOwner(int insuranceId);
}
