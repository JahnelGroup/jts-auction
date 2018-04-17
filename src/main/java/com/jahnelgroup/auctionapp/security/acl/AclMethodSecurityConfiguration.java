package com.jahnelgroup.auctionapp.security.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * http://www.baeldung.com/spring-security-acl
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclMethodSecurityConfiguration
        extends GlobalMethodSecurityConfiguration {

    @Autowired
    MethodSecurityExpressionHandler
            defaultMethodSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return defaultMethodSecurityExpressionHandler;
    }



}