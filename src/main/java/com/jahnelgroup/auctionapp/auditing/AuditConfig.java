package com.jahnelgroup.auctionapp.auditing;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "userContextProvider", dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {
}
