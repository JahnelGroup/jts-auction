package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import com.jahnelgroup.auctionapp.domain.auction.Auction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Bid Entity for the Auction Aggregate.
 */
@Entity
@Data
@ToString(exclude = "auction")
@EqualsAndHashCode(exclude = "auction")
public class Bid extends AbstractEntity {

    @NotNull
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    private Auction auction;
}