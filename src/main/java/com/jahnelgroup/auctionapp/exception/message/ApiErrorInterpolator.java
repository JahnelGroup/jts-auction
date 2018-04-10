package com.jahnelgroup.auctionapp.exception.message;

import org.springframework.validation.FieldError;

public interface ApiErrorInterpolator {

    String interpolate(FieldError e);
}
