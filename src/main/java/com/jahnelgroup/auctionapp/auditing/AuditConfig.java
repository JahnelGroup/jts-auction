package com.jahnelgroup.auctionapp.auditing;

import com.jahnelgroup.auctionapp.auditing.context.CurrentDateTimeService;
import com.jahnelgroup.auctionapp.auditing.context.DateTimeService;
import com.jahnelgroup.auctionapp.auditing.context.SpringSecurityUserContextService;
import com.jahnelgroup.auctionapp.auditing.context.UserContextService;
import com.jahnelgroup.auctionapp.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "userContextProvider", dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {

    @Bean
    UserContextService userContextService(UserRepository userRepository){
        return new SpringSecurityUserContextService(userRepository);
    }

    @Bean
    DateTimeService dateTimeService(){
        return new CurrentDateTimeService();
    }




    /**
     * Register the DateTimeProvider for Spring Data.
     *
     * @param dateTimeService
     * @return
     */
    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService){
        return () -> Optional.of(dateTimeService.getCurrentDateTime());
    }

    /**
     * Register the AuditAware for Spring Data.
     * @param userContextService
     * @return
     */
    @Bean
    AuditorAware<String> userContextProvider(UserContextService userContextService){
        return () -> Optional.of(userContextService.getCurrentUsername());
    }
}
