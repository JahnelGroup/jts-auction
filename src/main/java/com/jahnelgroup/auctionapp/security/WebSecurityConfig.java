package com.jahnelgroup.auctionapp.security;

import com.jahnelgroup.auctionapp.domain.user.UserRepository;
import com.jahnelgroup.auctionapp.security.authentication.AuthUserDetailsService;
import com.jahnelgroup.auctionapp.security.authentication.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Web Security Configuration
 *
 * https://medium.com/@nydiarra/secure-a-spring-boot-rest-api-with-json-web-token-reference-to-angular-integration-e57a25806c50
 * https://github.com/nydiarra/springboot-jwt
 *
 * http://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.security-realm}")
    private String securityRealm;

    @Value("${security.security-strength}")
    private Integer securityStrength;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    UserDetailsService authUserDetailsService;

    @Bean
    protected UserDetailsService authUserDetailsService(UserRepository userRepository){
        return new AuthUserDetailsService(userRepository);
    }

    @Bean
    protected RestAuthenticationEntryPoint authenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityStrength);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

}
