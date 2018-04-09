package com.jahnelgroup.auctionapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jahnelgroup.auctionapp.domain.user.UserRepository;
import com.jahnelgroup.auctionapp.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Abstract class for tests @WebMvcTest tests.
 *
 * The actual MockMvc reference will be provided by the @WebMvcTest annotation on the respective
 * test class but this class is used to help the remaining configuration needed for Spring Security.
 *
 * Import(WebSecurityConfig.class)
 *
 *      Will import our app's custom Spring Security configuration. No need to
 *      bring in the other security configurations because we'll ultimately mock
 *      out the user and their role anyway.
 *
 * WithMockUser(roles = "USER")
 *
 *      This annotation can be placed on a class or method and will mock out the user, auth
 *      and or roles. The default username is "user" and here we are defining the default role as USER.
 *
  */
@Import(WebSecurityConfig.class)
@WithMockUser(roles = "USER")
public abstract class AbstractWebMvcTest {

    /**
     * Add @WebMvcTest to the concrete test class.
     */
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * This Bean is only mocked to satisfy the wiring dependency for WebSecurityConfig, it
     * is never actually used with the Security flow unless actually invoked in your code somewhere.
     */
    @MockBean
    UserRepository mockUserRepository;

}
