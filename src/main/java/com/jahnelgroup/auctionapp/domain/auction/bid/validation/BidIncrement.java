package com.jahnelgroup.auctionapp.domain.auction.bid.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BidIncrementConstraint.class)
public @interface BidIncrement {

    String message() default "{com.jahnelgroup.auctionapp.domain.auction.bid.validation.BidIncrement.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    double value() default 0.25D;

}
