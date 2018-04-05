package com.jahnelgroup.auctionapp.domain.auction;

import java.util.Optional;

/**
 * Auction service contract.
 */
public interface AuctionService {

    Iterable<Auction> findAll();

    Optional<Auction> findById(Long id);

    Auction save(Auction auction);

    Auction update(Auction incoming, Auction current);

    void delete(Auction auction);

    void deleteById(Long id);

}