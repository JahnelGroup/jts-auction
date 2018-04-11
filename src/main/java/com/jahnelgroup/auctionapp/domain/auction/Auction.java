package com.jahnelgroup.auctionapp.domain.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import com.jahnelgroup.auctionapp.domain.auction.bid.Bid;
import com.jahnelgroup.auctionapp.domain.auction.bid.BidComparator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Auction Aggregate Root.
 */
@Entity
@Data
@ToString(exclude = "bids")
@EqualsAndHashCode(exclude = "bids")
public class Auction extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    private String description;

    /**
     * Bids are sorted from highest to lowest via the BidComparator.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortComparator(BidComparator.class)
    private SortedSet<Bid> bids = new TreeSet<>();

    /**
     * Get Bid by Bid Id.
     *
     * @param bidId
     * @return
     */
    public Optional<Bid> getBidById(Long bidId){
        return bidId == null ? Optional.empty() : bids.stream().filter(bid -> bid.getId().equals(bidId)).findFirst();
    }

    /**
     * Get Bid by Username.
     *
     * @param username
     * @return
     */
    public Optional<Bid> getBidByUsername(String username){
        return username == null ? Optional.empty() : bids.stream().filter(bid -> bid.getCreatedBy().equals(username)).findFirst();
    }

    /**
     * Get the highest Bid by amount.
     *
     * @return
     */
    public Optional<Bid> getHighestBid(){
        // they are sorted from highest to lowest
        return bids.isEmpty() ? Optional.empty() : Optional.of(bids.first());
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
