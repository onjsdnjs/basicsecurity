package io.security.demo;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserContext user = new UserContext("user","1111", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        user.setName("홍길동");
        user.setAge("10");
        user.setTel("000-0000-0000");

        if(user == null){
            throw new UsernameNotFoundException("No user found with username : " + username);
        }
        return user;
    }
}
