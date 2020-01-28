package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin();

    }

    @Bean
    @Profile("affirmative")
    public AccessDecisionManager affirmativeBased(FilterChainProxy filterChainProxy) {

        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false); // 접근 승인 거부 보류시 접근 허용은 true 접근 거부는 false
        setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager);

        return accessDecisionManager;
    }

    @Bean
    @Profile("unanimous")
    public AccessDecisionManager unanimousBased(FilterChainProxy filterChainProxy) {

        UnanimousBased accessDecisionManager = new UnanimousBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager);

        return accessDecisionManager;
    }

    @Bean
    @Profile("consensus")
    public AccessDecisionManager consensusBased(FilterChainProxy filterChainProxy) {

        ConsensusBased accessDecisionManager = new ConsensusBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        accessDecisionManager.setAllowIfEqualGrantedDeniedDecisions(false);
        setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager);

        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        Grantedvoter grantedvoter = new Grantedvoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(grantedvoter, grantedvoter, deniedVoter, deniedVoter);
        return accessDecisionVoterList;
    }

    private void setFilterSecurityInterceptor(FilterChainProxy filterChainProxy, AccessDecisionManager accessDecisionManager) {
        FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) getSecurityFilter(filterChainProxy, FilterSecurityInterceptor.class);
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
    }

    private Filter getSecurityFilter(FilterChainProxy filterChainProxy, Class<?> filterClass) {

        List<SecurityFilterChain> filterChains = filterChainProxy.getFilterChains();
        SecurityFilterChain securityFilterChain = filterChains.get(0);
        List<Filter> filters = securityFilterChain.getFilters();

        for(Filter filter :filters){
            if(filter.getClass().isAssignableFrom(filterClass)){
                return filter;
            };
        }
        return null;
    }
}
