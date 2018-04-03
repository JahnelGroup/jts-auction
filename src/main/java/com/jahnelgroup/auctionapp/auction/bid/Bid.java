package com.jahnelgroup.auctionapp.auction.bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.auction.Auction;
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
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    private Auction auction;
}
