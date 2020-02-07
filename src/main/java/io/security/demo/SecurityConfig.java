package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(0)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/user")
                .authorizeRequests()
                .anyRequest().permitAll()
        ;
    }

    @Bean
    public AccessDecisionManager affirmativeBased(FilterChainProxy filterChainProxy){

        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
        FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor)getFilter(filterChainProxy);
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased);

        return affirmativeBased;
    }

    private Filter getFilter(FilterChainProxy filterChainProxy) {

        List<Filter> filters = filterChainProxy.getFilters("/user");
        FilterSecurityInterceptor filterSecurityInterceptor = null;
        for(Filter filter : filters){
            if(filter.getClass().isAssignableFrom(FilterSecurityInterceptor.class)){
                filterSecurityInterceptor = (FilterSecurityInterceptor)filter;
            }
        }

        return filterSecurityInterceptor;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        GrantedVoter grantedVoter = new GrantedVoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        return Arrays.asList(abstainVoter);
    }




    /*@Bean
    public AccessDecisionManager affirmativeBased(FilterChainProxy filterChainProxy) {

        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) getSecurityFilter(filterChainProxy, FilterSecurityInterceptor.class, "/user");
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);

        return accessDecisionManager;
    }
    
    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        GrantedVoter grantedVoter = new GrantedVoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(deniedVoter, grantedVoter, deniedVoter);
        return accessDecisionVoterList;
    }

    public static Filter getSecurityFilter(FilterChainProxy filterChainProxy, Class<?> filterClass, String pattern) {

        List<SecurityFilterChain> filterChains = filterChainProxy.getFilterChains();
        for(SecurityFilterChain filterChain :filterChains){
            AntPathRequestMatcher requestMatcher = (AntPathRequestMatcher) ((DefaultSecurityFilterChain) filterChain).getRequestMatcher();
            if(requestMatcher.getPattern().equals(pattern)){
                List<Filter> filters = filterChain.getFilters();
                for(Filter filter :filters){
                    if(filter.getClass().isAssignableFrom(filterClass)){
                        return filter;
                    };
                }
            }
        }
        return null;
    }*/
}
