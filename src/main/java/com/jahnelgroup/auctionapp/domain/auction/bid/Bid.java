package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import com.jahnelgroup.auctionapp.domain.auction.Auction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * Bid Entity for the Auction Aggregate.
 */
@Entity
@Data
@ToString(exclude = "auction")
@EqualsAndHashCode(exclude = "auction")
public class Bid extends AbstractEntity {

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
