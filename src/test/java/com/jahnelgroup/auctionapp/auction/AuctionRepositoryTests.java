package com.jahnelgroup.auctionapp.auction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Auction repository tests.
 *
 * @DataJpaTest will rollback the transaction but the auto-generated ID's (i.e., sequence will
 * continue to increment.)
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuctionRepositoryTests {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AuctionRepository auctionRepository;

    @Test
    public void findAll() {
        Auction one = entityManager.persist(getAuction("one"));
        Auction two = entityManager.persist(getAuction("two"));
        assertThat(auctionRepository.findAll()).containsExactly(
                getAuction(one.getId(), "one"),
                getAuction(two.getId(), "two"));
    }

    @Test
    public void findById() {
        Auction one = entityManager.persist(getAuction("one"));
        Auction two = entityManager.persist(getAuction("two"));
        assertThat(auctionRepository.findById(two.getId())).isEqualTo(Optional.of(getAuction(two.getId(), "two")));
    }

    @Test
    public void save() {
        Auction one = auctionRepository.save(getAuction("one"));
        assertThat(one).isEqualTo(getAuction(one.getId(), "one"));
        assertThat(entityManager.find(Auction.class, one.getId())).isEqualTo(getAuction(one.getId(), "one"));
    }

    @Test
    public void update(){
        Auction one = auctionRepository.save(getAuction("one"));
        assertThat(one).isEqualTo(getAuction(one.getId(), "one"));
        assertThat(entityManager.find(Auction.class, one.getId())).isEqualTo(getAuction(one.getId(), "one"));

        Auction oneUpdated = auctionRepository.save(getAuction(one.getId(), "one-updated"));
        assertThat(oneUpdated).isEqualTo(getAuction(one.getId(), "one-updated"));
        assertThat(entityManager.find(Auction.class, oneUpdated.getId())).isEqualTo(getAuction(oneUpdated.getId(), "one-updated"));
    }

    @Test
    public void delete() {
        Auction one = entityManager.persist(getAuction("one"));
        assertThat(entityManager.find(Auction.class, one.getId())).isEqualTo(getAuction(one.getId(), "one"));
        auctionRepository.delete(getAuction(one.getId(), "one"));
        assertThat(entityManager.find(Auction.class, one.getId())).isNull();
    }

    @Test
    public void deleteById() {
        Auction one = entityManager.persist(getAuction("one"));
        assertThat(entityManager.find(Auction.class, one.getId())).isEqualTo(getAuction(one.getId(), "one"));
        auctionRepository.deleteById(one.getId());
        assertThat(entityManager.find(Auction.class, one.getId())).isNull();
    }


    private Auction getAuction(String name) {
        return getAuction(null, name);
    }
    private Auction getAuction(Long id, String name) {
        Auction auction = new Auction();
        if( id != null ) auction.setId(id);
        auction.setName("Auction " + name);
        return auction;
    }
}
