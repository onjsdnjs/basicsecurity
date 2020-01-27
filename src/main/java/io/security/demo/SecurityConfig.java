package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //5
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)

        ;
    }

    // 1
    public AuthenticationManager parentAuthenticationManager() {
        List<AuthenticationProvider> authProviderList = new ArrayList<>();
        authProviderList.add(new ParentAuthenticationProvider());
        authProviderList.add(new DaoAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authProviderList);
        return providerManager;
    }

    // 2
    public AuthenticationManager childAuthenticationManager() {
        List<AuthenticationProvider> authProviderList = new ArrayList<>();
        authProviderList.add(new ChildAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authProviderList, parentAuthenticationManager());
        authenticationManagerBuilder.parentAuthenticationManager(providerManager);
        return providerManager;
    }
    //4
    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(childAuthenticationManager());
        return authenticationFilter;
    }
}