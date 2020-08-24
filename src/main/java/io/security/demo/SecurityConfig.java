package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    private final String REMEMBER_ME_KEY = "onjsdnjs";

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();
        http
                .formLogin();
        http    .rememberMe()
                .rememberMeParameter("remember-me")
                .key(REMEMBER_ME_KEY)
                .rememberMeServices(persistentTokenBasedRememberMeServices());
    }

    @Bean
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
        PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices =
                new PersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, persistentTokenRepository());
        return persistentTokenBasedRememberMeServices;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        TokenRepositoryImpl tokenRepositoryImpl = new TokenRepositoryImpl();
        return tokenRepositoryImpl;
    }

}