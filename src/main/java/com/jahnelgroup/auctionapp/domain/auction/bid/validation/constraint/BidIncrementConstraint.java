package com.jahnelgroup.auctionapp.domain.auction.bid.validation.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-customconstraints
 */
public class BidIncrementConstraint implements ConstraintValidator<BidIncrement, BigDecimal> {

    private double increment;

    @Override
    public void initialize(BidIncrement bidAmountRounded) {
        this.increment = bidAmountRounded.value();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value == null || value.doubleValue() % increment == 0;
    }

}