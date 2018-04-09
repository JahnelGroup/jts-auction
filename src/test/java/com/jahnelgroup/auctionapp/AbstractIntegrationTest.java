package com.jahnelgroup.auctionapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * http://www.baeldung.com/oauth-api-testing-with-spring-mvc
 */
@AutoConfigureMockMvc
@DirtiesContext
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.raw-client-secret}")
    private String rawClientSecret;

    @Value("${security.jwt.grant-type}")
    private String grantType;

    private String accessToken = null;
    private String username = null;
    private String password = null;

    protected void login(String username, String password) throws Exception {
        this.username = username;
        this.password = password;
        this.accessToken = obtainAccessToken(username, password);
    }

    protected MockHttpServletRequestBuilder _get(String urlTemplate, Object... uriVars){
        return get(urlTemplate, uriVars).header("Authorization", accessToken);
    }

    protected MockHttpServletRequestBuilder _put(String urlTemplate, Object... uriVars){
        return put(urlTemplate, uriVars).header("Authorization", accessToken);
    }

    protected MockHttpServletRequestBuilder _post(String urlTemplate, Object... uriVars){
        return post(urlTemplate, uriVars).header("Authorization", accessToken);
    }

    protected MockHttpServletRequestBuilder _delete(String urlTemplate, Object... uriVars){
        return delete(urlTemplate, uriVars).header("Authorization", accessToken);
    }

    protected String obtainAccessToken(String username, String password) throws Exception {
        if( accessToken != null ) return accessToken;

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, rawClientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return "Bearer " + jsonParser.parseMap(resultString).get("access_token").toString();
    }

}
