package com.jahnelgroup.auctionapp.data.auction.bid;

import com.jahnelgroup.auctionapp.data.auction.Auction;
import com.jahnelgroup.auctionapp.data.auction.AuctionRepository;
import com.jahnelgroup.auctionapp.data.auction.AuctionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Auction service unit tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(MockitoJUnitRunner.class)
public class BidServiceTests {

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    BidRepository bidRepository;

    @InjectMocks
    BidServiceImpl bidService;

    Auction auction = new Auction();

    @Before
    public void before(){
        auction.setId(1L);
    }

    @Test
    public void findAll(){
        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("100.00"));
        auction.addBid(bid);

        when(auctionRepository.findById(eq(auction.getId()))).thenReturn(Optional.of(auction));
        assertThat(bidService.findAll(auction)).containsExactly(bid);
    }

    @Test
    public void findById(){
        Bid bid = new Bid();
        bid.setId(1L);
        bid.setAmount(new BigDecimal("100.00"));

        when(bidRepository.findById(1L)).thenReturn(Optional.of(bid));
        assertThat(bidRepository.findById(1L).get()).isEqualTo(bid);
    }

    @Test
    public void save(){
        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("100.00"));

        when(bidRepository.save(eq(bid))).thenAnswer(invocation -> {
            Bid bidArg = invocation.getArgument(0);
            Bid savedBid = new Bid(); // create a new Entity to prove this was returned by service
            savedBid.setId(1L);
            savedBid.setAmount(bidArg.getAmount());
            return savedBid;
        });

        assertThat(bidService.save(auction, bid))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("amount", new BigDecimal("100.00"));
    }

    @Test
    public void update(){
        Bid current = new Bid();
        current.setId(1L);
        current.setAmount(new BigDecimal("100.00"));

        Bid incoming = new Bid();
        incoming.setAmount(new BigDecimal("200.00"));

        when(bidRepository.save(eq(current))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(bidService.update(auction, incoming, current))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("amount", new BigDecimal("200.00"));
    }

    @Test
    public void delete(){
        Bid bid = new Bid();

        Auction spyAuction = Mockito.spy(Auction.class);
        spyAuction.setId(1L);
        spyAuction.addBid(bid);

        bidService.delete(spyAuction, bid);

        verify(spyAuction).removeBid(bid);

        ArgumentCaptor<Auction> argument = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository, times(1)).save(argument.capture());
        assertThat(argument.getValue()).isSameAs(spyAuction);
    }

    @Test
    public void deleteById(){
        Bid bid = new Bid();
        bid.setId(1L);

        Auction spyAuction = Mockito.spy(Auction.class);
        spyAuction.addBid(bid);

        bidService.deleteById(spyAuction, 1L);

        verify(spyAuction).removeBid(bid);

        ArgumentCaptor<Auction> argument = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository, times(1)).save(argument.capture());
        assertThat(argument.getValue()).isSameAs(spyAuction);
    }

}
