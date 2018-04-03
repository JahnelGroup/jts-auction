package com.jahnelgroup.auctionapp.auction;

import com.jahnelgroup.auctionapp.auction.bid.Bid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

/**
 * Auction service unit tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTests {

    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    AuctionServiceImpl auctionService;

    @Test
    public void findAll(){
        Auction auction = new Auction();
        auction.setName("myAuction");

        when(auctionRepository.findAll()).thenReturn(Arrays.asList(auction));
        assertThat(auctionService.findAll()).containsExactly(auction);
    }

    @Test
    public void findById(){
        Auction auction = new Auction();
        auction.setId(1L);
        auction.setName("myAuction");

        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        assertThat(auctionService.findById(1L).get()).isEqualTo(auction);
    }

    @Test
    public void save(){
        Auction auction = new Auction();
        auction.setName("My Auction");

        when(auctionRepository.save(auction)).thenAnswer(invocation -> {
            Auction auctionArg = invocation.getArgument(0);
            Auction savedAuction = new Auction(); // create a new Entity to prove this was returned by service
            savedAuction.setId(1L);
            savedAuction.setName(auctionArg.getName());
            return savedAuction;
        });

        assertThat(auctionService.save(auction))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "My Auction");
    }

    @Test
    public void update(){
        Auction current = new Auction();
        current.setId(1L);
        current.setName("My Auction");
        current.setDescription("My Description");
        Bid bid1 = new Bid();
        bid1.setId(1L);
        bid1.setAmount(new BigDecimal("100.00"));
        current.addBid(bid1);

        Auction incoming = new Auction();
        incoming.setName("My New Auction");
        incoming.setDescription("My New Description");
        // bids are ignored on direct update of an auction

        when(auctionRepository.save(any(Auction.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        assertThat(auctionService.update(incoming, current))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "My New Auction")
                .hasFieldOrPropertyWithValue("description", "My New Description");

    }

    @Test
    public void delete(){
        Auction auction = new Auction();

        auctionService.delete(auction);

        ArgumentCaptor<Auction> argument = ArgumentCaptor.forClass(Auction.class);
        verify(auctionRepository, times(1)).delete(argument.capture());
        assertThat(argument.getValue()).isSameAs(auction);
    }

    @Test
    public void deleteById(){
        Auction auction = new Auction();
        auction.setId(1L);

        auctionService.deleteById(1L);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(auctionRepository, times(1)).deleteById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1L);
    }

}
