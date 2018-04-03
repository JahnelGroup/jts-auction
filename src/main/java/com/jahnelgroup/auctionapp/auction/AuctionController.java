package com.jahnelgroup.auctionapp.auction;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * REST mappings for the Auction Aggregate.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionService auctionService;

    /**
     * Find all Auctions.
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
      * @param auction
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Auction> findById(@PathVariable("id") Optional<Auction> auction){
        return auction.isPresent() ? new ResponseEntity<>(auction.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create an Auction.
     *
     * @param auction
     * @return
     */
    @PostMapping
    public ResponseEntity<Auction> save(@Valid @RequestBody Auction auction){
        return new ResponseEntity<>(auctionService.save(auction), HttpStatus.CREATED);
    }

    /**
     * Update an Auction.
     *
     * @param current
     * @param incoming
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Auction> update(@PathVariable("id") Optional<Auction> current, @Valid @RequestBody Auction incoming){
        if ( current.isPresent() ){
            return ResponseEntity.ok(auctionService.update(incoming, current.get()));
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete an Auction.
     *
     * @param before
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Optional<Auction> before, @PathVariable Long id){
        if( before.isPresent() ){
            auctionService.delete(before.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

