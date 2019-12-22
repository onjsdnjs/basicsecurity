package io.security.demo;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

public class ParentAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(new User("user", "1111", roles), null, roles);
        user.setAuthenticated(false);
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
