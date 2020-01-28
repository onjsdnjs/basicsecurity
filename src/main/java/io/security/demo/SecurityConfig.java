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
import org.springframework.security.web.FilterChainProxy;

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
    public AccessDecisionManager affirmativeBased(FilterChainProxy filterChainProxy) {

        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false); // 접근 승인 거부 보류시 접근 허용은 true 접근 거부는 false
        CommonUtil.setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager, "/user");

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
