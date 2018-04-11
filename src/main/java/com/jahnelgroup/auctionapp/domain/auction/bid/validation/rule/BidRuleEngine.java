package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.validation.rule.SimpleRuleEngine;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class BidRuleEngine extends SimpleRuleEngine<Bid>{

    @PostConstruct
    void init(){
        addRule(new BidTooLowRule(), false);
    }

}
