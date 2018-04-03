package com.jahnelgroup.auctionapp.auction.bid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    /**
     * GET (read)
     *
     * @throws Exception
     */
    @Test
    public void findAllShouldReturnTwoBids() throws Exception {
        // TODO: Implement.
    }

    /**
     * GET (read by Id)
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
