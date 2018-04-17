package com.jahnelgroup.auctionapp.domain.auction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Auction service implementation.
 */
@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private AuctionRepository auctionRepository;

    @Override
    public Iterable<Auction> findAll(){
        return auctionRepository.findAll();
    }

    @Override
    public Optional<Auction> findById(Long id) {
        return auctionRepository.findById(id);
    }

    @Override
    public Auction save(Auction auction){
        return auctionRepository.save(auction);
    }

    @Override
    public Auction update(Auction incoming, Auction current) {
        return save(current.copyFields(incoming, "bids"));
    }

    @Override
    public void delete(Auction auction){
        auctionRepository.delete(auction);
    }

    @Override
    public void deleteById(Long id) {
        auctionRepository.deleteById(id);
    }
}
