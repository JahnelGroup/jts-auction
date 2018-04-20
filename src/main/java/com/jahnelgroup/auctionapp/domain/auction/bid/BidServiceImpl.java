package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.AuctionNotFoundException;
import com.jahnelgroup.auctionapp.domain.auction.AuctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Bid service implementation.
 */
@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private AuctionRepository auctionRepository;
    private BidRepository bidRepository;

    @Override
    public Iterable<Bid> findAll(Auction auction) {
        return auctionRepository.findById(auction.getId()).get().getBids();
    }

    @Override
    public Iterable<Bid> findAllByAuctionId(Long auctionId) {
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        if( auction.isPresent() ){
            return auction.get().getBids();
        }else{
            throw new AuctionNotFoundException();
        }
    }

    @Override
    public Optional<Bid> findById(Auction auction, Long id) {
        return auctionRepository.findById(auction.getId()).get().getBidById(id);
    }

    @Override
    public Bid save(Auction auction, Bid bid) {
        if( !auction.getBidById(bid.getId()).isPresent() ){
            auction.addBid(bid);
        }

        // could have relied on save cascade from auction but then we wouldn't
        // have access to the generated bid id.
        Bid savedBid = bidRepository.save(bid);

        // still want to save auction to indicate that it has changed
        auctionRepository.save(auction);

        return savedBid;
    }

    @Override
    public Bid update(Auction auction, Bid incoming, Bid current) {
        return save(auction, current.copyFields(incoming, "auction"));
    }

    @Override
    public void delete(Auction auction, Bid bid) {
        auctionRepository.save(auction.removeBid(bid));
    }

    @Override
    public void deleteById(Auction auction, Long id) {
        Optional<Bid> bid = auction.getBidById(id);

        if( !bid.isPresent() ){
            throw new BidNotFoundException();
        }

        delete(auction, bid.get());
    }
}
