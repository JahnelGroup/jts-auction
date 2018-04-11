package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.auditing.context.UserContextService;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.validation.rule.SimpleRuleEngine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
public class BidRuleEngine extends SimpleRuleEngine<Bid>{

    private UserContextService userContextService;

    @PostConstruct
    void init(){
        addRule(new BidOnceRule(userContextService), false);
        addRule(new BidAmountRule(), false);
    }

}
