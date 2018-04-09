package com.jahnelgroup.auctionapp.domain.auction.bid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jahnelgroup.auctionapp.AbstractIntegrationTest;
import com.jahnelgroup.auctionapp.domain.auction.AuctionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BidIntegrationTests extends AbstractIntegrationTest {

    @Autowired
    AuctionService auctionService;

    @Value("classpath:/json/createAuction.json")
    Resource createAuction;

    @Before
    public void before() throws Exception{
        login("admin", "admin");
        setupAuction();
    }

    @Test
    public void integrationTest() throws Exception {

        // Validate no bids exist
        mockMvc.perform(_get("/auctions/1/bids"))
                .andDo(print())
                .andExpect(status().isNotFound()); // 200

        // Submit first bid
        Bid bid1 = new Bid();
        bid1.setAmount(new BigDecimal("100.00"));
        mockMvc.perform(_post("/auctions/1/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid1)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.amount",   Matchers.is(100.00)));

        // Submit second bid
        Bid bid2 = new Bid();
        bid2.setAmount(new BigDecimal("200.00"));
        mockMvc.perform(_post("/auctions/1/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid2)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(2)))
                .andExpect(jsonPath("$.amount",   Matchers.is(200.00)));

        // Validate 2 bids exist
        mockMvc.perform(_get("/auctions/1/bids"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id", Matchers.contains(1, 2)))                // order matters
                .andExpect(jsonPath("$[*].amount", Matchers.contains(100.00, 200.00)));

        // Update bid 1
        bid1.setAmount(new BigDecimal("150.00"));
        mockMvc.perform(_put("/auctions/1/bids/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid1)))
                .andDo(print())
                .andExpect(status().isOk()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.amount",   Matchers.is(150.00)));

        // Delete bid 2
        mockMvc.perform(_delete("/auctions/1/bids/2"))
                .andDo(print())
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString()));

        // Submit third bid
        Bid bid3 = new Bid();
        bid3.setAmount(new BigDecimal("300.00"));
        mockMvc.perform(_post("/auctions/1/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid3)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(3)))
                .andExpect(jsonPath("$.amount",   Matchers.is(300.00)));

        // Validate bid 1 was updated, bid 2 was deleted, and bid 3 was added
        mockMvc.perform(_get("/auctions/1/bids"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id", Matchers.contains(1, 3)))
                .andExpect(jsonPath("$[*].amount", Matchers.contains(150.00, 300.00)));
    }

    /**
     * Sets up an Auction.
     *
     * @throws Exception
     */
    private void setupAuction() throws Exception {
        // Create one auction
        mockMvc.perform(_post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new String(Files.readAllBytes(Paths.get(createAuction.getURI())))))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(1))) // this was actually auto-generated by db
                .andExpect(jsonPath("$.name",   Matchers.is("My Auction")))
                .andExpect(jsonPath("$.description",   Matchers.is("My First Auction")));
    }

}
