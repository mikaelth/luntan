package se.uu.ebc.luntan;

import org.apereo.cas.client.validation.Cas30ServiceTicketValidator;
import org.apereo.cas.client.validation.TicketValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import se.uu.ebc.luntan.security.SecurityService;
import se.uu.ebc.luntan.security.RESTAuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

@Slf4j
@Configuration
@EnableWebSecurity
@Profile("prod")
public class LuntanSecurityConfig {

    @Value("${luntan.base.url}")
    String baseUrl;

    @Value("${luntan.environment.dev}")
    boolean devEnv = false;

    @Bean
    public AuthenticationUserDetailsService<org.springframework.security.cas.authentication.CasAssertionAuthenticationToken> authenticationUserDetailsService() {
        log.debug("authenticationUserDetailsService()");
        return new SecurityService();
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(baseUrl + "/login/cas");
        serviceProperties.setSendRenew(false);
        log.debug("serviceProperties() " + serviceProperties);
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        log.debug("casAuthenticationProvider()");
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("ThisIsSomeKindOfKey");
        return casAuthenticationProvider;
    }

/*
    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator("https://weblogin.uu.se/idp/profile/cas");
    }
 */
    /**
     * CAS ticket validator
     */
    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(casServerUrl);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(casAuthenticationProvider());
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        log.debug("casAuthenticationFilter() " + casAuthenticationFilter);
        return casAuthenticationFilter;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl("https://weblogin.uu.se/idp/profile/cas/login");
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    @Bean
    public RESTAuthenticationEntryPoint restCasAuthenticationEntryPoint() {
        RESTAuthenticationEntryPoint restCasAuthenticationEntryPoint = new RESTAuthenticationEntryPoint();
        restCasAuthenticationEntryPoint.setCasAuthenticationEntryPoint(casAuthenticationEntryPoint());
        return restCasAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("configure()");

        http.addFilter(casAuthenticationFilter())
            .csrf(csrf -> csrf.disable());

        if (devEnv) {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/bemanning/**").access(
                    new org.springframework.security.web.access.expression.WebExpressionAuthorizationManager("hasIpAddress('::1')")
                )
                .requestMatchers("/index.*").authenticated()
                .requestMatchers("/loginredirect.html").authenticated()
                .requestMatchers("/InREST.html").authenticated()
                .requestMatchers("/Luntan/index.html").authenticated()
                .requestMatchers("/Luntan/**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/styles/main.css").permitAll()
                .requestMatchers("/rest/bulk/**").hasRole("REGISTRATIONUPDATER")
                .requestMatchers("/rest/**").authenticated()
                .requestMatchers("/view/**").authenticated()
                .anyRequest().authenticated()
            );
        }

        http.exceptionHandling(ex -> ex.authenticationEntryPoint(restCasAuthenticationEntryPoint()));

        return http.build();
    }
}
