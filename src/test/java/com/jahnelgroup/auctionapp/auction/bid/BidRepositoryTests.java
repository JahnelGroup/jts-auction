package com.jahnelgroup.auctionapp.auction.bid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bid repository tests.
 *
 * @DataJpaTest will rollback the transaction but the auto-generated ID's (i.e., sequence will
 * continue to increment.)
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BidRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BidRepository bidRepository;

    @Test
    public void findAll() throws Exception {
        Bid one = entityManager.persist(getBid("100.00"));
        Bid two = entityManager.persist(getBid("200.00"));
        assertThat(bidRepository.findAll()).containsExactly(
                getBid(one.getId(), "100.00"),
                getBid(two.getId(), "200.00"));
    }

    @Test
    public void findById() throws Exception {
        Bid one = entityManager.persist(getBid("100.00"));
        Bid two = entityManager.persist(getBid("200.00"));
        assertThat(bidRepository.findById(two.getId())).isEqualTo(Optional.of(getBid(two.getId(), "200.00")));
    }

    @Test
    public void save() throws Exception {
        Bid one = entityManager.persist(getBid("100.00"));
        assertThat(one).isEqualTo(getBid(one.getId(), "100.00"));
        assertThat(entityManager.find(Bid.class, one.getId())).isEqualTo(getBid(one.getId(), "100.00"));
    }

    @Test
    public void update() throws Exception {
        Bid one = bidRepository.save(getBid("100.00"));
        assertThat(one).isEqualTo(getBid(one.getId(), "100.00"));
        assertThat(entityManager.find(Bid.class, one.getId())).isEqualTo(getBid(one.getId(), "100.00"));

        Bid oneUpdated = bidRepository.save(getBid(one.getId(), "200.00"));
        assertThat(oneUpdated).isEqualTo(getBid(one.getId(), "200.00"));
        assertThat(entityManager.find(Bid.class, oneUpdated.getId())).isEqualTo(getBid(oneUpdated.getId(), "200.00"));
    }

    @Test
    public void delete() throws Exception {
        Bid one = entityManager.persist(getBid("100.00"));
        assertThat(entityManager.find(Bid.class, one.getId())).isEqualTo(getBid(one.getId(), "100.00"));
        bidRepository.delete(getBid(one.getId(), "100.00"));
        assertThat(entityManager.find(Bid.class, one.getId())).isNull();
    }

    @Test
    public void deleteById() throws Exception {
        Bid one = entityManager.persist(getBid("100.00"));
        assertThat(entityManager.find(Bid.class, one.getId())).isEqualTo(getBid(one.getId(),"100.00"));
        bidRepository.deleteById(one.getId());
        assertThat(entityManager.find(Bid.class, one.getId())).isNull();
    }

    private Bid getBid(String amount) {
        return getBid(null, amount);
    }
    private Bid getBid(Long id, String amount) {
        Bid bid = new Bid();
        if( id != null ) bid.setId(id);
        bid.setAmount(new BigDecimal(amount));
        return bid;
    }



}
