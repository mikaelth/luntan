package se.uu.ebc.luntan.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.Authentication;

public interface LuntanUserService extends AuthenticationUserDetailsService {
    
    public UserDetails loadUserByUsername(String username);
    public UserDetails loadUserDetails(Authentication token);

}
