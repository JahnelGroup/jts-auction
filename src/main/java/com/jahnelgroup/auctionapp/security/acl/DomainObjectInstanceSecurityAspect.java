package com.jahnelgroup.auctionapp.security.acl;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DomainObjectInstanceSecurityAspect {

    @Pointcut("target(com.jahnelgroup.auctionapp.domain.AbstractEntity) && " +
            "execution(public * *(..)) && !within(DomainObjectInstanceSecurityAspect)")
    public void domainObjectInstanceExecution() {}

    @Around("domainObjectInstanceExecution()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        log.trace("Action taken on AbstractEntity");
        return pjp.proceed();
    }


}
