package com.jahnelgroup.auctionapp.domain.auction.bid;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Bid repository.
 */
@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {

}
