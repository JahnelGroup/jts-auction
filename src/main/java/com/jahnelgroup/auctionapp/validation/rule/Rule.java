package com.jahnelgroup.auctionapp.validation.rule;

import org.springframework.validation.Errors;

import java.util.Map;

public interface Rule <T> {

    void execute(T target, Map<Object, Object> context, Errors errors);

}
