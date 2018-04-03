package com.jahnelgroup.auctionapp.auction.bid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jahnelgroup.auctionapp.auction.Auction;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Bid WebMvcTest Controller tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@WebMvcTest(BidController.class)
public class BidControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BidService bidService;

    private static Auction auction1 = new Auction();
    private static Bid bid1 = new Bid();
    private static Bid bid2 = new Bid();

    @Before
    public void before(){
        auction1.setId(1L);
        bid1.setId(1L);
        bid2.setId(2L);
    }

    /**
     * GET (read)
     *
     * @throws Exception
     */
    @Test
    public void findAllShouldReturnTwoBids() throws Exception {
        auction1.addBid(bid1);
        auction1.addBid(bid2);

        mockMvc.perform(get("/auctions/1/bids"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id", Matchers.contains(1, 2)));
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/auctions/1/bids/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)));
    }

    /**
     * POST (create)
     *
     * @throws Exception
     */
    @Test
    public void createAndReturnEntityFromService() throws Exception {
        when(bidService.save(eq(auction1), any(Bid.class))).thenAnswer(invocation -> {
            Bid bidArg = invocation.getArgument(1);

            Bid savedBid = new Bid();
            savedBid.setId(1L);
            savedBid.setAmount(bidArg.getAmount());
            return savedBid;
        });

        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("300.00"));

        mockMvc.perform(post("/auctions/1/bids")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(bid)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.amount", Matchers.is(300.00)));
    }

    /**
     * PUT (update)
     *
     * @throws Exception
     */
    @Test
    public void updateAndReturnEntityFromService() throws Exception {
        when(bidService.update(eq(auction1), any(Bid.class), any(Bid.class))).thenAnswer(invocation -> {
            Bid incomingArg = invocation.getArgument(1);
            Bid currentArg = invocation.getArgument(2);

            Bid updatedBid = new Bid();
            updatedBid.setId(currentArg.getId());
            updatedBid.setAmount(incomingArg.getAmount());
            return updatedBid;
        });

        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("400.00"));

        mockMvc.perform(put("/auctions/1/bids/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid)))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.id",       Matchers.is(2)))
                .andExpect(jsonPath("$.amount",   Matchers.is(400.00)));
    }

    /**
     * DELETE (delete)
     *
     * @throws Exception
     */
    @Test
    public void deleteShouldCallDelete() throws Exception {
        mockMvc.perform(delete("/auctions/1/bids/2"))
                .andDo(print())
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString()));

        ArgumentCaptor<Auction> arg1 = ArgumentCaptor.forClass(Auction.class);
        ArgumentCaptor<Bid> arg2 = ArgumentCaptor.forClass(Bid.class);
        verify(bidService, times(1)).delete(arg1.capture(), arg2.capture());
        assertThat(arg1.getValue().getId()).isEqualTo(1L);
        assertThat(arg2.getValue().getId()).isEqualTo(2L);
    }

    /**
     * This is needed because we are not loading the entire Spring Context
     * just the minimum needed for Controller testing. It facilitates the
     * JPA type conversion for automatically pull the entity out the database.
     */
    @TestConfiguration
    static class InternalConfig {
        @Bean
        WebMvcConfigurer configurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addFormatters(FormatterRegistry registry) {
                    registry.addConverter(String.class, Auction.class, id -> auction1);
                    registry.addConverter(String.class, Bid.class, id -> {
                        switch(id){
                            case "1": return bid1;
                            case "2": return bid2;
                            default:
                                Bid bid = new Bid();
                                bid.setId(Long.parseLong(id));
                                return bid;
                        }
                    });
                }
            };
        }
    }

}
