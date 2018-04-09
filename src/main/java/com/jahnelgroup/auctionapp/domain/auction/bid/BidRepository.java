package com.jahnelgroup.auctionapp.domain.auction.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Bid repository.
 */
@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
