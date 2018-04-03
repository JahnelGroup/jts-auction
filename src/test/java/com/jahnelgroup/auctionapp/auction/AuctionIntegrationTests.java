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

    @Autowired
    AuctionService auctionService;

    @Value("classpath:/json/createAuction.json")
    Resource createAuction;

    @Test
    public void integrationTest() throws Exception{
        // 1. Validate nothing exists
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

        // 2. Create one auction
        // TODO: Implement.

        // 3. Validate it is returned now
        // TODO: Implement.

        // 4. Update it
        // TODO: Implement.

        // 5. Delete it
        // TODO: Implement.

        // Validate nothing exists
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

    }

}

