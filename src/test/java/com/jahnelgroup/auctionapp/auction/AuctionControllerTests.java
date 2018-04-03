package com.jahnelgroup.auctionapp.auction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
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

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Auction WebMvcTest Controller tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@WebMvcTest(AuctionController.class)
public class AuctionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuctionService auctionService;

    /**
     * GET (read)
     *
     * @throws Exception
     */
    @Test
    public void findAllWithOneResultShouldReturnIt() throws Exception {
        Auction auction = new Auction();
        auction.setName("myAuction");

        when(auctionService.findAll()).thenReturn(Arrays.asList(auction));

        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("myAuction")));

    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/auctions/1"))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    /**
     * POST (create)
     *
     * @throws Exception
     */
    @Test
    public void createAndReturnEntityFromService() throws Exception {
        Auction auction = new Auction();
        auction.setName("myAuction");

        when(auctionService.save(any(Auction.class))).thenAnswer(invocation -> {
            Auction auctionArg = invocation.getArgument(0);

            Auction savedAuction = new Auction(); // create a new Entity to prove this was returned by service
            savedAuction.setId(1L);
            savedAuction.setName(auctionArg.getName());
            return savedAuction;
        });

        mockMvc.perform(post("/auctions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(auction)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.name",   Matchers.is("myAuction")));
    }

    /**
     * PUT (update)
     *
     * @throws Exception
     */
    @Test
    public void updateAndReturnEntityFromService() throws Exception {
        when(auctionService.update(any(Auction.class), any(Auction.class))).thenAnswer(invocation -> {
            Auction incomingArg = invocation.getArgument(0);
            Auction currentArg = invocation.getArgument(1);

            Auction updatedAuction = new Auction(); // create a new Entity to prove this was returned by service
            updatedAuction.setId(currentArg.getId()); // prove Id was taken from mock repo call
            updatedAuction.setName(incomingArg.getName()); // prove incoming was passed in correctly.
            return updatedAuction;
        });

        Auction auction = new Auction();
        auction.setName("updatedAuction");

        mockMvc.perform(put("/auctions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auction)))
                .andDo(print())
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.id",     Matchers.is(1)))
                .andExpect(jsonPath("$.name",   Matchers.is("updatedAuction")));
    }

    /**
     * DELETE (delete)
     *
     * @throws Exception
     */
    @Test
    public void deleteShouldCallDelete() throws Exception {
        mockMvc.perform(delete("/auctions/1"))
                .andDo(print())
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString()));

        ArgumentCaptor<Auction> argument = ArgumentCaptor.forClass(Auction.class);
        verify(auctionService, times(1)).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1L);
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
                    registry.addConverter(String.class, Auction.class, id -> {
                        Auction auction = new Auction();
                        auction.setId(Long.parseLong(id));
                        return auction;
                    });
                }
            };
        }
    }

}
