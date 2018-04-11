package com.jahnelgroup.auctionapp.validation.exception.message;

import org.springframework.validation.FieldError;

public interface ApiErrorInterpolator {

    String interpolate(FieldError e);
}
