package com.jahnelgroup.auctionapp.domain.auction.bid.validation.rule;

import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.domain.auction.bid.exception.BidTooLowException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleBidRuleEngine implements BidRuleEngine {

    @Override
    public void execute(Auction auction, Bid bid) {

        // Bid must be larger than current highest
        Optional<Bid> highestBid = auction.getHighestBid();
        if( highestBid.isPresent() ){
            if ( bid.compareTo(highestBid.get()) <= 0 ){
                throw new BidTooLowException();
            }
        }

    }

}
