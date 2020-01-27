package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(!authentication.getCredentials().equals(userDetails.getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }

        //로그인실패횟수 : 5 이상이면 예외발생
        //계정활성화여부 : 계정이  활성화 안되면 예외발생
        //IP 체크 : 특정한 IP 만 접속 가능..
        //2창 인증
        // SMS 인증, 캡처 이미지 인증...

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
