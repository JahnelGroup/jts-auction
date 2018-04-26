package com.jahnelgroup.auctionapp.auditing.context;

import java.time.ZonedDateTime;

public class CurrentDateTimeService implements DateTimeService{

    @Override
    public ZonedDateTime getCurrentDateTime() {
        return ZonedDateTime.now();
    }

}
