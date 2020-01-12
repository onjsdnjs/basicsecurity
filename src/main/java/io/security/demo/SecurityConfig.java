package io.security.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/loginPage").permitAll()
                .anyRequest().authenticated()
        .and()
                .rememberMe()
                .alwaysRemember(false) //form 에서 파라미터를 넘겨주지 않아도 항상 리멤버 함. Default 는 false
                .rememberMeParameter("remember") // Default 는 remember-me
                .tokenValiditySeconds(3600) // 메모리 저장소 Default 는 14일
                .key("security"); // Default 는 UUID

    }
}
