package com.jahnelgroup.auctionapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO: Investigate a better way to accomplish this.
 *
 * .anonymous().disable() on HttpSecurity does nothing?
 */
@Component
public class DenyAnonymousUserFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication == null ){
            throw new AnonymousUserDeniedException();
        }else{
            filterChain.doFilter(request, response);
        }
    }

}

class AnonymousUserDeniedException extends UnauthorizedUserException{
    public AnonymousUserDeniedException() {
        super("Anonymous users are not allowed.");
    }
}
