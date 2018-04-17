package com.jahnelgroup.auctionapp.aspect;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@Configuration
public class AspectConfig {

}
