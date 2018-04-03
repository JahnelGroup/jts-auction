package com.jahnelgroup.auctionapp.auction;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        BeanUtils.copyProperties(incoming, current, "id", "bids");
        return save(current);
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
