package se.uu.ebc.luntan.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
//    public String getCurrentAuditor() {
    public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
//			return currentUserName;
			return Optional.of(currentUserName);
		} else {
//			return "ANONYMOUS";
			return Optional.of("ANONYMOUS");
		}
    }

}
