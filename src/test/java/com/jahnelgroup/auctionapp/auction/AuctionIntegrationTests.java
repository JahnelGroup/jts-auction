package com.jahnelgroup.auctionapp.auction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Auction integration tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class AuctionIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:/json/createAuction.json")
    Resource createAuction;

    @Test
    public void integrationTest() throws Exception{
        // Validate nothing exists
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

        // Create one auction
        mockMvc.perform(post("/auctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new String(Files.readAllBytes(Paths.get(createAuction.getURI())))))
            .andDo(print())
            .andExpect(status().isCreated()) // 201
            .andExpect(jsonPath("$.id",     Matchers.is(1))) // this was actually auto-generated by db
            .andExpect(jsonPath("$.name",   Matchers.is("My Auction")))
            .andExpect(jsonPath("$.description",   Matchers.is("My First Auction")));

        // Validate it is returned now
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("My Auction")));

        // Update it
        Auction updateAuction = new Auction();
        updateAuction.setName("My New Auction");
        updateAuction.setDescription("My New Description");

        mockMvc.perform(put("/auctions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateAuction)))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.name",   Matchers.is("My New Auction")))
                .andExpect(jsonPath("$.description",   Matchers.is("My New Description")));

        // Delete it
        mockMvc.perform(delete("/auctions/1"))
                .andDo(print())
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString()));

        // Validate nothing exists
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

    }

}

