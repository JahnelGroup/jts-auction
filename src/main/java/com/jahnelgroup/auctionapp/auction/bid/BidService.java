package com.jahnelgroup.auctionapp.auction.bid;

import com.jahnelgroup.auctionapp.auction.Auction;

import java.util.Optional;

/**
 * Bid service contract.
 */
public interface BidService {

    Iterable<Bid> findAll(Auction auction);

    Optional<Bid> findById(Auction auction, Long id);

    Bid save(Auction auction, Bid bid);

    Bid update(Auction auction, Bid incoming, Bid current);

    void delete(Auction auction, Bid bid);

    void deleteById(Auction auction, Long id);

}