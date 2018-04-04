package com.jahnelgroup.auctionapp.auditing.context;

import com.jahnelgroup.auctionapp.data.user.User;
import com.jahnelgroup.auctionapp.data.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@AllArgsConstructor
public class SpringSecurityUserContextService implements UserContextService {

    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public Long getCurrentUserId() {
        return null;
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
