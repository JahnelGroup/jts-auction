package com.jahnelgroup.auctionapp.security;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthenticatedUser extends User {

    @Getter
    private com.jahnelgroup.auctionapp.data.user.User user;

    public AuthenticatedUser(
            com.jahnelgroup.auctionapp.data.user.User user,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user = user;
    }

}
