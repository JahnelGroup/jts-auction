package com.jahnelgroup.auctionapp.security;

import com.jahnelgroup.auctionapp.data.user.User;
import com.jahnelgroup.auctionapp.data.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if( !optionalUser.isPresent() ){
            throw new UsernameNotFoundException(username);
        }

        AuthenticatedUser au = optionalUser.map(user -> new AuthenticatedUser(
                user,
                username,
                passwordEncoder.encode(user.getPassword()),
                new ArrayList<>())).get();

        return au;
    }
}
