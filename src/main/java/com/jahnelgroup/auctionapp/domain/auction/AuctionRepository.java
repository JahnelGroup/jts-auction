package com.jahnelgroup.auctionapp.domain.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Action repository.
 */
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

}
