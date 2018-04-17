package com.jahnelgroup.auctionapp.security.acl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@Slf4j
public class AclContext {

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Bean
    public MethodSecurityExpressionHandler
    defaultMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler
                = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator
                = new AclPermissionEvaluator(aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Bean
    public SpringCacheBasedAclCache aclCache(){
        return new SpringCacheBasedAclCache(
                aclSpringCache(),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy());
    }

    @Bean
    public ConcurrentMapCache aclSpringCache(){
        return new ConcurrentMapCache("aclCache");
    }

    @Bean
    public JdbcMutableAclService aclService() {
        return new JdbcMutableAclService(
                dataSource, lookupStrategy(), aclCache());
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(
                new ConsoleAuditLogger());
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(
                dataSource,
                aclCache(),
                aclAuthorizationStrategy(),
                new ConsoleAuditLogger()
        );
    }

}
