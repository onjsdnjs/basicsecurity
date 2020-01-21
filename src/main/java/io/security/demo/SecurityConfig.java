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

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        });
    }

    public AuthenticationManager parentAuthenticationManager() {
        List<AuthenticationProvider> authProviderList = new ArrayList<>();
        authProviderList.add(new ParentAuthenticationProvider());
        authProviderList.add(new DaoAuthenticationProvider());
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
                .rememberMe()
        .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(childAuthenticationManager());
        return authenticationFilter;
    }
}