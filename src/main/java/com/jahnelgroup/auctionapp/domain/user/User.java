package com.jahnelgroup.auctionapp.domain.user;

import com.jahnelgroup.auctionapp.domain.AbstractEntity;
import com.jahnelgroup.auctionapp.domain.user.Role.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User extends AbstractEntity {

    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
