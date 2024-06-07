package org.home.dev.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private MyUserDetailsService myUserDetailsService;
    private PasswordEncoder encoder;

    public CustomAuthenticationProvider(MyUserDetailsService myUserDetailsService,
                                        PasswordEncoder encoder) {
        this.myUserDetailsService = myUserDetailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName = authentication.getName();
        final String password = authentication.getCredentials().toString();

        final UserDetails user = myUserDetailsService.loadUserByUsername(userName);
        if (user == null) {
            throw new BadCredentialsException("Details not found");
        }

        if (encoder.matches(password, user.getPassword())) {
            return createAuthentication(authentication, user);
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private Authentication createAuthentication(Authentication authentication, UserDetails user) {
        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(),
                        authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

}
