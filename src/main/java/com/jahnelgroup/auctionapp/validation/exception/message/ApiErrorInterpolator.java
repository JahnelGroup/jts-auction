package com.jahnelgroup.auctionapp.validation.exception.message;

import org.springframework.context.support.DefaultMessageSourceResolvable;

public interface ApiErrorInterpolator {

    String interpolate(DefaultMessageSourceResolvable e);
}
