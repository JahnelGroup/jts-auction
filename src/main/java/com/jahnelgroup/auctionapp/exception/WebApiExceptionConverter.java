package com.jahnelgroup.auctionapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface WebApiExceptionConverter {

    ResponseEntity<ApiError> convert(ResponseEntity resp, Exception ex, BindingResult bindingResult) throws IllegalAccessException;

}
