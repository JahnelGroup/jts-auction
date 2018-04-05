package com.jahnelgroup.auctionapp.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    Optional<User> findByUsername(String username);

}
