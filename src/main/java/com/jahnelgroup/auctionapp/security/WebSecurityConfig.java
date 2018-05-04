package com.jahnelgroup.auctionapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.security-strength}")
    private Integer securityStrength;

    @Bean
    protected RestAuthenticationEntryPoint authenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint())
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityStrength);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // http://www.baeldung.com/spring-security-granted-authority-vs-role

        auth.inMemoryAuthentication()
            .withUser("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")

            .and()
            .withUser("admin")
                .password(passwordEncoder().encode("password"))
                .authorities("WRITE_PRIVILEGES", "READ_PRIVILEGES")
                .roles("ADMIN");
    }

}
