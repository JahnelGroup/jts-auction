package com.jahnelgroup.auctionapp.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.auction.bid.Bid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Auction Aggregate Root.
 */
@Entity
@Data
@ToString(exclude = "bids")
@EqualsAndHashCode(exclude = "bids")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids = new ArrayList<>();

}
