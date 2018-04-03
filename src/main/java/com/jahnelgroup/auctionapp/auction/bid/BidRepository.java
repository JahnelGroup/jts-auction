package com.jahnelgroup.auctionapp.auction.bid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Bid repository.
 */
@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {

}
