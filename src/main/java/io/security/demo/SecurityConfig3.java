package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterChainProxy;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig3 extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin")
                .authorizeRequests()
                .anyRequest().permitAll()
        ;

    }

    @Bean
//    @Profile("unanimous")
    public AccessDecisionManager unanimousBased(FilterChainProxy filterChainProxy) {

        UnanimousBased accessDecisionManager = new UnanimousBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        CommonUtil.setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager, "/admin");

        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        GrantedVoter grantedVoter = new GrantedVoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(grantedVoter, grantedVoter, deniedVoter);
        return accessDecisionVoterList;
    }
}
