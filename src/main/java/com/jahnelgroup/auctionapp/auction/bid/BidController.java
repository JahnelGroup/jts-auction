package com.jahnelgroup.auctionapp.auction.bid;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * Find all Bids.
     *
     * Read: GET /auction/{auctionId}/bids
     *
     * @param auctionId
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<Bid>> findAll(@PathVariable("auctionId") Long auctionId){
        Iterable<Bid> bids = bidService.findAllByAuctionId(auctionId);
        return bids.iterator().hasNext() ? new ResponseEntity<>(bids, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // TODO: Implement the rest.

}
