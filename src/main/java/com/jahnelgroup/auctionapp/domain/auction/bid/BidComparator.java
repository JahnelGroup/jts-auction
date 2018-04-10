package com.jahnelgroup.auctionapp.domain.auction.bid;

import java.util.Comparator;

/**
 * Sorts Bids from highest to lowest.
 */
public class BidComparator implements Comparator<Bid> {

    @Override
    public int compare(Bid b1, Bid b2) {
        return Double.compare(b1.getAmount().doubleValue(), b2.getAmount().doubleValue());
    }
}
