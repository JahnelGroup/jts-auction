package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.validation.rule.Rule;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

class BidAmountRule implements Rule<Bid> {

    @Override
    public void execute(Bid target, Map<Object, Object> context, Errors errors) {
        Auction auction = (Auction) context.get("auction");

        // Bid must be larger than current highest
        Optional<Bid> highestBid = auction.getHighestBid();
        if( highestBid.isPresent() ){
            if ( target.compareTo(highestBid.get()) <= 0 ){
                errors.rejectValue("amount", "BidTooLow", new Object[]{highestBid.get().getAmount().doubleValue()}, null);
            }
        }
    }

}
