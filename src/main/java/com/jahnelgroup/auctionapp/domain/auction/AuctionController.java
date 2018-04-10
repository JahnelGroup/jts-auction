package com.jahnelgroup.auctionapp.domain.auction;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
     * Create an Auction.
     *
     * POST /auctions
     *
     * @param auction
     * @return
     */
    @PostMapping
    public ResponseEntity<Auction> save(@Valid @RequestBody Auction auction){
        return new ResponseEntity<>(auctionService.save(auction), HttpStatus.CREATED);
    }

    /**
     * Find all Auctions.
     *
     * GET /auctions
     *
     * @return
     */
    @GetMapping
    public Iterable<Auction> findAll(){
        return auctionService.findAll();
    }

    /**
     * Find an Auction.
     *
     * GET /auctions/1
     *
      * @param auction
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Auction> findById(@PathVariable("id") Optional<Auction> auction){
        return auction.isPresent() ? new ResponseEntity<>(auction.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update an Auction.
     *
     * PUT /auctions/1
     *
     * @param current
     * @param incoming
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Auction> update(@PathVariable("id") Optional<Auction> current, @RequestBody Auction incoming){
        if ( current.isPresent() ){
            return ResponseEntity.ok(auctionService.update(incoming, current.get()));
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an Auction.
     *
     * DELETE /auctions/1
     *
     * @param before
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Optional<Auction> before){
        if( before.isPresent() ){
            auctionService.delete(before.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

