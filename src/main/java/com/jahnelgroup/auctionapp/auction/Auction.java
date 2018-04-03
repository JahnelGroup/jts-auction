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

    public Optional<Bid> getBidById(Long bidId){
        return bidId == null ? Optional.empty() : bids.stream().filter(bid -> bid.getId().equals(bidId)).findFirst();
    }

    /**
     * Adds a bid to this auction and establishes the bi-directional relationship.
     *
     * @param bid
     * @return
     */
    public Auction addBid(Bid bid){
        bid.setAuction(this);
        bids.add(bid);
        return this;
    }

    /**
     * Removes a bid to this auction and establishes the bi-directional relationship.
     *
     * @param bid
     * @return
     */
    public Auction removeBid(Bid bid){
        bid.setAuction(null);
        bids.remove(bid);
        return this;
    }

}
