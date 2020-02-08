package io.security.demo;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = User.builder().username("홍길동")
                .password("1111")
                .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        if(user == null){
            throw new UsernameNotFoundException("No user found with username : " + username);
        }
        return user;
    }
}
