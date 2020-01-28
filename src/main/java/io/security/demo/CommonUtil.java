package io.security.demo;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import java.util.List;

public class CommonUtil {

    public static void setFilterSecurityInterceptor(FilterChainProxy filterChainProxy, AccessDecisionManager accessDecisionManager, String pattern) {
        FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) getSecurityFilter(filterChainProxy, FilterSecurityInterceptor.class, pattern);
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
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
    }
}
