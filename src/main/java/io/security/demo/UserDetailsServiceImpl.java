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

        // User user = UserRepository.findByUsername(username);

        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword("1111");
        user.setAddress("서울");
        user.setTel("000-0000-0000");
        user.setRoles(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return new UserContext(user);
    }
}