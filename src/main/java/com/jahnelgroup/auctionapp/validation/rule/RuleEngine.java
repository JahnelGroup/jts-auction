package com.jahnelgroup.auctionapp.validation.rule;

import java.util.Map;

public interface RuleEngine<T> {

    void addRule(Rule<T> rule, Boolean continueOnError);
    void execute(T target, Map<Object, Object> context);

}
