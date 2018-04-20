package com.jahnelgroup.auctionapp.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.auction.Auction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Bid Entity for the Auction Aggregate.
 */
@Entity
@Data
@ToString(exclude = "auction")
@EqualsAndHashCode(exclude = "auction")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    private Auction auction;

    /**
     * Determines if this Bid is associated to the supplied Auction by
     * comparing primary ID's.
     *
     * @param auction
     * @return
     */
    public boolean isAssociated(Auction auction){
        if( this.auction == null || this.auction.getId() == null ||
                auction == null || auction.getId() == null ) return false;
        return this.auction.getId() == auction.getId();
    }
}
