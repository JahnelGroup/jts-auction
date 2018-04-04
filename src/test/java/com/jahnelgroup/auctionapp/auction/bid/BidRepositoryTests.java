package com.jahnelgroup.auctionapp.auction.bid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BidRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BidRepository bidRepository;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void deleteById() throws Exception {

    }

}
