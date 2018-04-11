package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.auditing.context.UserContextService;
import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.validation.rule.Rule;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class BidOnceRule implements Rule<Bid> {

    private UserContextService userContextService;

    @Override
    public void execute(Bid target, Map<Object, Object> context, Errors errors) {
        Auction auction = (Auction) context.get("auction");

        // A user can only bid once on an Auction.
        Optional<Bid> currentBid = auction.getBidByUsername(userContextService.getCurrentUsername());
        if( currentBid.isPresent() ){
            errors.reject("BidAlreadyExists", new Object[]{currentBid.get()}, null);
        }
    }

}
