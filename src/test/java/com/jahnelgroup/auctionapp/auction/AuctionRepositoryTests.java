package com.jahnelgroup.auctionapp.auction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuctionRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuctionRepository auctionRepository;

    @Test
    public void findAll() {
        entityManager.persist(getAuction("one"));
        entityManager.persist(getAuction("two"));
        assertThat(auctionRepository.findAll()).containsExactly(
                getAuction(1L, "one"),
                getAuction(2L, "two"));
    }

    @Test
    public void findById() {
        entityManager.persist(getAuction("one"));
        entityManager.persist(getAuction("two"));
        assertThat(auctionRepository.findById(2L)).isEqualTo(Optional.of(getAuction(2L, "two")));
    }

    @Test
    public void save() {
        assertThat(auctionRepository.save(getAuction("one")))
                .isEqualTo(getAuction(1L, "one"));

        assertThat(entityManager.find(Auction.class, 1L)).isEqualTo(getAuction(1L, "one"));
    }

    @Test
    public void delete() {
        entityManager.persist(getAuction("one"));

        assertThat(entityManager.find(Auction.class, 1L)).isEqualTo(getAuction(1L, "one"));
        auctionRepository.delete(getAuction(1L, "one"));
        assertThat(entityManager.find(Auction.class, 1L)).isNull();
    }

    @Test
    public void deleteById() {
        entityManager.persist(getAuction("one"));

        assertThat(entityManager.find(Auction.class, 1L)).isEqualTo(getAuction(1L, "one"));
        auctionRepository.deleteById(1L);
        assertThat(entityManager.find(Auction.class, 1L)).isNull();
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
