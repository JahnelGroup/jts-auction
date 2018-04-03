package com.jahnelgroup.auctionapp.auction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    /**
     * GET (read)
     *
     * @throws Exception
     */
    @Test
    public void findAllWithOneResultShouldReturnIt() throws Exception {
        // TODO: Implement.
    }

    /**
     * GET (read) by id
     *
     * @throws Exception
     */
    @Test
    public void findById() throws Exception {
        // TODO: Implement.
    }

    /**
     * POST (create)
     *
     * @throws Exception
     */
    @Test
    public void createAndReturnEntityFromService() throws Exception {
        // TODO: Implement.
    }

    /**
     * PUT (update)
     *
     * @throws Exception
     */
    @Test
    public void updateAndReturnEntityFromService() throws Exception {
        // TODO: Implement.
    }

    /**
     * DELETE (delete)
     *
     * @throws Exception
     */
    @Test
    public void deleteShouldCallDelete() throws Exception {
        // TODO: Implement.
    }

}
