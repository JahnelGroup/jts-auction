package com.jahnelgroup.auctionapp.auction;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * REST mappings for the Auction Aggregate.
 *
 * Create   POST    /auctions
 *
 * Read     GET     /auctions
 *          GET     /auctions/1
 *
 * Update   PUT     /auctions/1
 *
 * Delete   DELETE  /auctions/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionService auctionService;

    /**
     * Find all Auctions.
     *
     * Read: GET /auctions
     *
     * @return
     */
    @GetMapping
    public Iterable<Auction> findAll(){
        return auctionService.findAll();
    }

    // TODO: Implement the rest.

}

