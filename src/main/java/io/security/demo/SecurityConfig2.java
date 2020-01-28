package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterChainProxy;

import java.util.Arrays;
import java.util.List;

@Configuration
@Order(1)
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/manager")
                .authorizeRequests()
                .anyRequest().permitAll()
                ;

    }

    @Bean
//    @Profile("consensus")
    public AccessDecisionManager consensusBased(FilterChainProxy filterChainProxy) {

        ConsensusBased accessDecisionManager = new ConsensusBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        accessDecisionManager.setAllowIfEqualGrantedDeniedDecisions(false);
        CommonUtil.setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager, "/manager");

        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        GrantedVoter grantedVoter = new GrantedVoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(deniedVoter, grantedVoter, deniedVoter);
        return accessDecisionVoterList;
    }
}
