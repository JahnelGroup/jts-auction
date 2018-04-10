package com.jahnelgroup.auctionapp.auditing.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
@RequestScope
@Getter
public class SpringRequestScopeContextService implements RequestContextService {

    @Autowired
    @Getter(AccessLevel.NONE)
    private DateTimeService dateTimeService;

    private String uuid = UUID.randomUUID().toString();
    private ZonedDateTime timestamp;
    private String path;

    @PostConstruct
    void init(){
        uuid = UUID.randomUUID().toString();
        timestamp = dateTimeService.getCurrentDateTime();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            path = request.getServletPath();
        }
    }


}
