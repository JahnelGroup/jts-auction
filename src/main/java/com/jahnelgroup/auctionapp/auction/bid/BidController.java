package com.jahnelgroup.auctionapp.auction.bid;

import com.jahnelgroup.auctionapp.auction.Auction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST mappings for the Bid Entity.
 *
 * Create   POST    /auctions/{auctionId}/bids
 *
 * Read     GET     /auctions/{auctionId}/bids
 *          GET     /auctions/{auctionId}/bids/1
 *
 * Update   PUT     /auctions/{auctionId}/bids/1
 *
 * Delete   DELETE  /auctions/{auctionId}/bids/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auctions/{auctionId}/bids")
public class BidController {

    private BidService bidService;

    /**
     * Create a bid.
     *
     * POST /auctions/{auctionId}/bids
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
     * Find all Bids.
     *
     * GET /auctions/{auctionId}/bids
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
     * GET /auctions/{auctionId}/bids/1
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
     * Update a bid.
     *
     * PUT /auctions/{auctionId}/bids/1
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
     * DELETE /auctions/{auctionId}/bids/1
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
