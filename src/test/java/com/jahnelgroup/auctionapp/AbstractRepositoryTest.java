package com.jahnelgroup.auctionapp;

import com.jahnelgroup.auctionapp.auditing.AuditConfig;
import com.jahnelgroup.auctionapp.auditing.context.UserContextService;
import com.jahnelgroup.auctionapp.domain.user.role.Role;
import com.jahnelgroup.auctionapp.domain.user.User;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Import(AuditConfig.class)
public abstract class AbstractRepositoryTest {

    /**
     * This Bean is only mocked to satisfy the wiring dependency for WebSecurityConfig, it
     * is never actually used with the Security flow unless actually invoked in your code somewhere.
     */
    @MockBean
    UserContextService mockUserContextService;

    private User user;

    protected void login(Long id, String username, String ... roleNames) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);

        Long roleId = 0L;
        List<Role> roles = new ArrayList<>();
        for(String r : roleNames){
            Role role = new Role();
            role.setId(roleId++);
            role.setRoleName(r);
        }
        if( !roles.isEmpty() ){
            user.setRoles(roles);
        }

        login(user);
    }

    protected void login(User user) {
        this.user = user;
        when(mockUserContextService.getCurrentUserId()).thenReturn(user.getId());
        when(mockUserContextService.getCurrentUsername()).thenReturn(user.getUsername());
        when(mockUserContextService.getCurrentUser()).thenReturn(user);
    }

}
