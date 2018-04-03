package com.jahnelgroup.auctionapp.auction.bid;

import com.jahnelgroup.auctionapp.auction.Auction;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST mappings for the Bid Entity.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auctions/{auctionId}/bids")
public class BidController {

    private BidService bidService;

    /**
     * Find all Bids.
     *
     * @param auction
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<Bid>> findAll(@PathVariable("auctionId") Optional<Auction> auction){
        if( auction.isPresent() ){
            List<Bid> bids = auction.get().getBids();
            return bids.iterator().hasNext() ? new ResponseEntity<>(bids, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Find a Bid.
     *
     * @param auction
     * @param bid
     * @return
     */
    @GetMapping("/{bidId}")
    public ResponseEntity<Bid> findById(@PathVariable("auctionId") Optional<Auction> auction, @PathVariable("bidId") Optional<Bid> bid){
        if( auction.isPresent() && bid.isPresent() ){
            return new ResponseEntity<>(bid.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a bid.
     *
     * @param auction
     * @param bid
     * @return
     */
    @PostMapping
    public ResponseEntity<Bid> save(@PathVariable("auctionId") Optional<Auction> auction, @RequestBody Bid bid){
        return auction.isPresent() ? new ResponseEntity<>(bidService.save(auction.get(), bid), HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update a bid.
     *
     * @param auction
     * @param current
     * @param incoming
     * @return
     */
    @PutMapping("/{bidId}")
    public ResponseEntity<Bid> update(@PathVariable("auctionId") Optional<Auction> auction,
            @PathVariable("bidId") Optional<Bid> current, @Valid @RequestBody Bid incoming){
        if( auction.isPresent() && current.isPresent() ){
            return new ResponseEntity<>(bidService.update(auction.get(), incoming, current.get()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a bid.
     *
     * @param auction
     * @param bid
     * @return
     */
    @DeleteMapping("/{bidId}")
    public ResponseEntity delete(@PathVariable("auctionId") Optional<Auction> auction, @PathVariable("bidId") Optional<Bid> bid){
        if( auction.isPresent() && bid.isPresent() ){
            bidService.delete(auction.get(), bid.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
