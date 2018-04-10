package com.jahnelgroup.auctionapp.domain.auction.bid.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BidIncrementConstraint implements ConstraintValidator<BidIncrement, BigDecimal> {

    private double increment;

    @Override
    public void initialize(BidIncrement bidAmountRounded) {
        this.increment = bidAmountRounded.value();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value.doubleValue() % increment == 0;
    }

}