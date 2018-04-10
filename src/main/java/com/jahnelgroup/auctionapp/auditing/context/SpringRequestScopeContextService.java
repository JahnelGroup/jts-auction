package com.jahnelgroup.auctionapp.auditing.context;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@RequestScope
public class SpringRequestScopeContextService implements RequestContextService {

    @Autowired
    private DateTimeService dateTimeService;

    private String uuid = UUID.randomUUID().toString();
    private ZonedDateTime timestamp;

    @PostConstruct
    void init(){
        uuid = UUID.randomUUID().toString();
        timestamp = dateTimeService.getCurrentDateTime();
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

}
