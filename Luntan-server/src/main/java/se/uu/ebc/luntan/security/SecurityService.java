package se.uu.ebc.luntan.security;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import se.uu.ebc.luntan.enums.UserRoleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("securityService")
@Transactional(readOnly = true)
public class SecurityService implements LuntanUserService {

    @Autowired
    UserRepo userRepo;

    private static final boolean ENABLED = true;
    private static final boolean ACCOUNT_NON_EXPIRED = true;
    private static final boolean CREDENTIALS_NON_EXPIRED = true;
    private static final boolean ACCOUNT_NON_LOCKED = true;
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername, processing {}", username);

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "loadUserByUsername(String username) - 'username' can not be null or empty");
        }
        try {
            User localUser = userRepo.findUserByUsername(username);
            log.debug("loadUserByUsername, got user {}", localUser);

            if (localUser == null) {
                throw new UsernameNotFoundException("User " + username + " not found!");
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (UserRoleType userRole : localUser.getUserRoles()) {
                authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole.toString().toUpperCase()));
            }

            return new org.springframework.security.core.userdetails.User(
                localUser.getUsername(), "token",
                ENABLED, ACCOUNT_NON_EXPIRED, CREDENTIALS_NON_EXPIRED, ACCOUNT_NON_LOCKED,
                authorities);
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Throwable th) {
            throw new SecurityServiceException(
                "Error performing 'SecurityService.loadUserByUsername(String username)' --> " + th, th);
        }
    }

    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        log.debug("loadUserDetails, got token {}", token);
        String username = token.getPrincipal() instanceof String s ? s : "";
        return loadUserByUsername(username);
    }
}
