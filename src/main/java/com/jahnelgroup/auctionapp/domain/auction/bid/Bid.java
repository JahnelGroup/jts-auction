package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import com.jahnelgroup.auctionapp.domain.auction.Auction;
import com.jahnelgroup.auctionapp.domain.auction.bid.validation.constraint.BidIncrement;
import com.jahnelgroup.auctionapp.security.acl.AclSecured;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Bid Entity for the Auction Aggregate.
 */
@Entity
@AclSecured
@Data
@ToString(exclude = "auction")
@EqualsAndHashCode(exclude = "auction")
public class Bid extends AbstractEntity implements Comparable<Bid> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @BidIncrement
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    private Auction auction;

    @Override
    public int compareTo(Bid bid) {
        double amount = bid.getAmount().doubleValue();
        return Double.compare(this.amount.doubleValue(), amount);
    }

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
