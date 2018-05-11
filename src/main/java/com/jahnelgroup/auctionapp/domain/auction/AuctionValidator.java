package com.jahnelgroup.auctionapp.domain.auction;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuctionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Auction.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Auction auction = (Auction) target;

    }
}
