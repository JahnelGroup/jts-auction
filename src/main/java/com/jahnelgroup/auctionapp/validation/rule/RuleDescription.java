package com.jahnelgroup.auctionapp.validation.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Data
public class RuleDescription<T>{
    private Rule<T> rule;
    private Boolean continueOnError;
}
