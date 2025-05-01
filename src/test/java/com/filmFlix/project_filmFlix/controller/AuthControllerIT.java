package com.filmFlix.project_filmFlix.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.services.AuthService;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AuthControllerIT {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService userservice;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private String token;
    private String admToken;
    private String invalidToken;


    @BeforeEach
    void setUp(){
        token = EntitiesFactory.createToken().token();
        admToken = EntitiesFactory.createADMToken().token();
        invalidToken = EntitiesFactory.createInvalidToken().token();

    }

    //login (sucesso)
    @Test
    public void loginShouldReturnATokenWhenValidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(EntitiesFactory.createLoginRequest());
        var result = mockMvc.perform(post("/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.token").isNotEmpty());
    }
    //login (falha: credenciais invalidas)
    @Test
    public void loginShouldReturn404WhenInvalidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(EntitiesFactory.createInvalidLoginRequest());
        var result = mockMvc.perform(post("/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isNotFound());

    }
    //SignUp (sucesso)
    @Test
    public void singUpShouldReturn201WhenValidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(EntitiesFactory.createSignUpRequest());
        var result = mockMvc.perform(post("/auth/sing-up")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.token").isNotEmpty());


    }
    //SignUp (falha quando ah dados ja existentes no banco dedos)
    @Test
    public void singUpShouldReturn409WhenInvalidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(EntitiesFactory.createInvalidSignUpRequest());
        var result = mockMvc.perform(post("/auth/sing-up")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(status().isConflict());

    }

}
