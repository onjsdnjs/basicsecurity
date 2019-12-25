package io.security.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
        .and()
                .formLogin();
    }

    @Bean
    @Profile("affirmative")
    public AccessDecisionManager affirmativeBased() {
        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        return accessDecisionManager;
    }

    @Bean
    @Profile("unanimous")
    public AccessDecisionManager unanimousBased() {
        UnanimousBased accessDecisionManager = new UnanimousBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        return accessDecisionManager;
    }

    @Bean
    @Profile("consensus")
    public AccessDecisionManager consensusBased() {
        ConsensusBased accessDecisionManager = new ConsensusBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false);
        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {

        AuthenticatedVoter authenticatedVoter = new AuthenticatedVoter();
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        RoleVoter roleVoter = new RoleVoter();

        List<AccessDecisionVoter<? extends Object>> accessDecisionVoterList = Arrays.asList(authenticatedVoter, webExpressionVoter, roleVoter);
        return accessDecisionVoterList;
    }
}