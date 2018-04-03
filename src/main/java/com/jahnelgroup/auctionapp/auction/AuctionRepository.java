package com.jahnelgroup.auctionapp.auction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Action repository.
 */
@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {

}
