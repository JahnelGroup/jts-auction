package com.jahnelgroup.auctionapp.auction.bid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bid JSON serialization tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@JsonTest
public class BidJsonTests {

    @Autowired
    private JacksonTester<Bid> json;

    @Value("${classpath:/json/bid.json}")
    Resource bidJson;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(getBid()))
                .isEqualToJson("/json/bid.json");
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.parse(Files.readAllBytes(Paths.get(bidJson.getURI()))))
                .isEqualTo(getBid());
    }

    private Bid getBid() {
        Bid bid = new Bid();

        // TODO: Implement.

        return bid;
    }
}
