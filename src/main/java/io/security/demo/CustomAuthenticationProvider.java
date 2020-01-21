package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserContext userContext = (UserContext)userDetailsService.loadUserByUsername(authentication.getName());

        if(!authentication.getCredentials().equals(userContext.getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getUser().getRoles());
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
