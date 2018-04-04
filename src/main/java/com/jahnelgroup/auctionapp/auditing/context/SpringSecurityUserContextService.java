package com.jahnelgroup.auctionapp.auditing.context;

import com.jahnelgroup.auctionapp.data.user.User;
import com.jahnelgroup.auctionapp.data.user.UserRepository;
import com.jahnelgroup.auctionapp.security.AuthenticatedUser;
import com.jahnelgroup.auctionapp.security.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Data
@AllArgsConstructor
public class SpringSecurityUserContextService implements UserContextService {

    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Optional<User> u = userRepository.findById(getCurrentUserId());
        if( !u.isPresent() ){
            throw new UnauthorizedException();
        }else{
            return u.get();
        }
    }

    @Override
    public Long getCurrentUserId() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( !(p instanceof AuthenticatedUser) ){
            throw new UnauthorizedException();
        }else{
            return ((AuthenticatedUser)p).getUser().getId();
        }
    }

    @Override
    public String getCurrentUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if( username == null ){
            throw new UnauthorizedException();
        }else{
            return username;
        }
    }

}
