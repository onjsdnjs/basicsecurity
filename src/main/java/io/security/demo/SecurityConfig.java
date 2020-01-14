package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/invalid","/expired").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .failureUrl("/login.html?error=true")
                .and()
                .logout()
	            .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
        .and()
                .sessionManagement()
                .sessionFixation().changeSessionId()
//                .invalidSessionUrl("/invalid")
//                .maximumSessions(1)
//                .expiredUrl("/expired")
//                .maxSessionsPreventsLogin(true)

        ;
    }
}
