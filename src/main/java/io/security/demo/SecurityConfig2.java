package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.ConsensusBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterChainProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.sendRedirect("/denied");
                    }
                })
                ;
    }

    @Bean
    public AccessDecisionManager consensusBased(FilterChainProxy filterChainProxy) {

        ConsensusBased accessDecisionManager = new ConsensusBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfEqualGrantedDeniedDecisions(false);
        CommonUtil.setFilterSecurityInterceptor(filterChainProxy, accessDecisionManager, "/manager");

        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        GrantedVoter grantedVoter = new GrantedVoter();
        DeniedVoter deniedVoter = new DeniedVoter();
        AbstainVoter abstainVoter = new AbstainVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(deniedVoter, grantedVoter, grantedVoter);
        return accessDecisionVoterList;
    }
}
