package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;

public interface BidRuleEngine {

    void execute(Auction auction, Bid bid);

}
