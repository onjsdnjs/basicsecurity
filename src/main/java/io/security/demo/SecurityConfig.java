package io.security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired(required = false)
    private ObjectPostProcessor<Object> objectObjectPostProcessor;

//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    public AuthenticationManager parentAuthenticationManager() {
        List<AuthenticationProvider> authProviderList = new ArrayList<>();
        authProviderList.add(new ParentAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authProviderList);
        return providerManager;
    }

    public AuthenticationManager childAuthenticationManager() {
        List<AuthenticationProvider> authProviderList = new ArrayList<>();
        authProviderList.add(new ChildAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authProviderList, parentAuthenticationManager());
        authenticationManagerBuilder.parentAuthenticationManager(providerManager);
        return providerManager;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
        .and()
                .formLogin()
        .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//        FilterSecurityInterceptor filterSecurityInterceptor = http.getSharedObject(FilterSecurityInterceptor.class);
//        filterSecurityInterceptor.setAlwaysReauthenticate(true);
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(childAuthenticationManager());
        return authenticationFilter;
    }
}