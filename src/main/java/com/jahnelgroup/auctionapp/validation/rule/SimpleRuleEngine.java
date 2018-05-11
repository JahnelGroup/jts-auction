package com.jahnelgroup.auctionapp.validation.rule;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SimpleRuleEngine<T> implements RuleEngine<T> {

    private List<RuleDescription> rules = new ArrayList<>();

    @Override
    public void addRule(Rule rule, Boolean continueOnError) {
        rules.add(new RuleDescription(rule, continueOnError));
    }

    @Override
    public void execute(T target, Map<Object, Object> context) {
        BindingResult errors = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());

        for(RuleDescription r : rules){
            r.getRule().execute(target, context, errors);
            if( errors.hasErrors() && !r.getContinueOnError() ){
                break;
            }
        }

        // collect errors and throw
        if( errors.hasErrors() ){
            throw new RuleFailedException("Your request could not be completed.", errors);
        }
    }

}