package se.uu.ebc.luntan.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;

public interface LuntanUserService extends AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    UserDetails loadUserByUsername(String username);
    UserDetails loadUserDetails(CasAssertionAuthenticationToken token);
}
