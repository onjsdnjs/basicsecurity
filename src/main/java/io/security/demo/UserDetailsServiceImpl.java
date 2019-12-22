package io.security.demo;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword("1111");
        user.setRoles(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return new UserContext(user);
    }
}