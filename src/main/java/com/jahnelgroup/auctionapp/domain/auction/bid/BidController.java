package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.jahnelgroup.auctionapp.domain.auction.Auction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<Bid> save(@PathVariable("auctionId") Optional<Auction> auction, @Valid @RequestBody Bid bid){
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
            Set<Bid> bids = auction.get().getBids();
            return !bids.isEmpty() ? new ResponseEntity<>(bids, HttpStatus.OK)
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
        if( auction.isPresent() && bid.isPresent() && bid.get().isAssociated(auction.get()) ){
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
     * [Security]
     * Role  - ADMIN can do this for anyone.
     * ACL   - If not an ADMIN then you must have the write privilege.
     *
     * @param auction
     * @param current
     * @param incoming
     * @return
     */
    @PutMapping("/{bidId}")
    @PreAuthorize("hasRole('ADMIN') or hasPermission(#current.get(), 'write')")
    public ResponseEntity<Bid> update(@PathVariable("auctionId") Optional<Auction> auction,
            @PathVariable("bidId") Optional<Bid> current, @RequestBody Bid incoming){
        if( auction.isPresent() && current.isPresent() && current.get().isAssociated(auction.get()) ){
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
     * [Security]
     * Role  - ADMIN can do this for anyone.
     * ACL   - If not an ADMIN then you must have the write privilege.
     *
     * @param auction
     * @param bid
     * @return
     */
    @DeleteMapping("/{bidId}")
    @PreAuthorize("hasRole('ADMIN') or hasPermission(#bid.get(), 'delete')")
    public ResponseEntity delete(@PathVariable("auctionId") Optional<Auction> auction, @PathVariable("bidId") Optional<Bid> bid){
        if( auction.isPresent() && bid.isPresent() && bid.get().isAssociated(auction.get()) ){
            bidService.delete(auction.get(), bid.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
