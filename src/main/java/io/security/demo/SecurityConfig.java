package io.security.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
        .and()
                .formLogin()
                .defaultSuccessUrl("/home")
                .failureUrl("/login.html?error=true")
                .and()
                .logout()
	            .logoutSuccessUrl("/login")
        .and()
                .sessionManagement()
                .invalidSessionUrl("/invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
        .and()
                .sessionFixation().migrateSession();
    }
}
