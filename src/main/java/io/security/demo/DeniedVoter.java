package io.security.demo;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

public class DeniedVoter implements AccessDecisionVoter {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {
        return ACCESS_DENIED;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}
