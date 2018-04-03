package com.jahnelgroup.auctionapp.auction;

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
 * Auction JSON serialization tests.
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@JsonTest
public class AuctionJsonTests {

    @Autowired
    private JacksonTester<Auction> json;

    @Value("${classpath:/json/auction.json}")
    Resource auctionJson;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(getAuction()))
                .isEqualToJson("/json/auction.json");
    }

    @Test
    public void testDeserialize() throws Exception {
        assertThat(this.json.parse(Files.readAllBytes(Paths.get(auctionJson.getURI()))))
                .isEqualTo(getAuction());
    }

    private Auction getAuction() {
        Auction auction = new Auction();

        // TODO: Implement.

        return auction;
    }
}
