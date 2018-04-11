package com.jahnelgroup.auctionapp.validation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

public interface WebApiExceptionConverter {

    ResponseEntity<ApiError> convert(WebRequest request, ResponseEntity resp, Exception ex, BindingResult bindingResult) throws IllegalAccessException;

}
