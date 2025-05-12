package com.filmFlix.project_filmFlix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmFlix.project_filmFlix.dtos.authDtos.TokenDto;
import com.filmFlix.project_filmFlix.entities.User;
import com.filmFlix.project_filmFlix.services.JwtService;
import com.filmFlix.project_filmFlix.services.MovieService;
import com.filmFlix.project_filmFlix.util.EntitiesFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovieControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService service;

    @Autowired
    private JwtService jwtService;

    private User user;
    private TokenDto token;
    private Long totalMovies;
    private Long validID;
    private Long invalidID;

    @BeforeEach
    void setUp() {
        validID = 1L;
        invalidID = 100L;
        totalMovies = 10L;
        user = (User) EntitiesFactory.createUser();
    }

    // findAll (sem filtro)
    @Test
    public void findAllShouldReturnAPageWhenNoRouteParameterIsProvided() throws Exception {
        token = EntitiesFactory.createToken();

        var result = mockMvc.perform(get("/movies")
                .header("Authorization", "Bearer " + token.token())
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content").isNotEmpty());
        result.andExpect(jsonPath("$.totalElements").value(totalMovies));
    }

    // findAll (com filtro por gênero)
    @Test
    public void findAllShouldReturnAPageWithOnlyTheSpecificGenrePassedAsARouteParameter() throws Exception {
        token = EntitiesFactory.createToken();

        var result = mockMvc.perform(get("/movies?genre=1")
                .header("Authorization", "Bearer " + token.token())
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].title").value("Bob Esponja"));
        result.andExpect(jsonPath("$.content[1].title").value("Kingsman"));
        result.andExpect(jsonPath("$.totalElements").value(2));
    }

    // findAll (token inválido)
    @Test
    public void findAllShouldReturnStatus401WhenInvalidToken() {
        try {
            token = EntitiesFactory.createInvalidToken();

            var result = mockMvc.perform(get("/movies?genre=1")
                    .header("Authorization", "Bearer " + token.token())
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    // findById (id válido)
    @Test
    public void findByIdShouldReturn200DtoWhenValidId() throws Exception {
        token = EntitiesFactory.createToken();

        var result = mockMvc.perform(get("/movies/1")
                .header("Authorization", "Bearer " + token.token())
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.title").value("Bob Esponja"));
    }

    // findById (id inválido)
    @Test
    public void findByIdShouldThrow404WhenInvalidId() throws Exception {
        token = EntitiesFactory.createToken();

        var result = mockMvc.perform(get("/movies/100")
                .header("Authorization", "Bearer " + token.token())
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    // findById (token inválido)
    @Test
    public void findByIdShouldReturnStatus401WhenInvalidToken() {
        try {
            token = EntitiesFactory.createInvalidToken();

            var result = mockMvc.perform(get("/movies?genre=1")
                    .header("Authorization", "Bearer " + token.token())
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    // insert (com sucesso)
    @Test
    public void insertShouldReturn201WhenValidCredentials() throws Exception {
        token = EntitiesFactory.createADMToken();
        var request = EntitiesFactory.createMovieInsertDto();
        var json = new ObjectMapper().writeValueAsString(request);

        var result = mockMvc.perform(post("/movies")
                .header("Authorization", "Bearer " + token.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.subTitle").value(request.getSubTitle()));
    }

    // insert (role inválida)
    @Test
    public void insertShouldReturn403WhenInvalidRole() throws Exception {
        token = EntitiesFactory.createToken();
        var request = EntitiesFactory.createInvalidToken();
        var json = new ObjectMapper().writeValueAsString(request);

        var result = mockMvc.perform(post("/movies")
                .header("Authorization", "Bearer " + token.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    // insert (token inválido)
    @Test
    public void insertShouldReturnStatus401WhenInvalidToken() {
        try {
            token = EntitiesFactory.createInvalidToken();
            var request = EntitiesFactory.createInvalidToken();
            var json = new ObjectMapper().writeValueAsString(request);

            var result = mockMvc.perform(get("/movies")
                    .header("Authorization", "Bearer " + token.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    // delete (sucesso)
    @Test
    public void deleteShouldReturn204WhenValidIdAndValidRole() throws Exception {
        token = EntitiesFactory.createADMToken();

        var result = mockMvc.perform(delete("/movies/1")
                .header("Authorization", "Bearer " + token.token()));

        result.andExpect(status().isNoContent());
    }

    // delete (id inválido)
    @Test
    public void deleteShouldReturn404WhenInvalidIdAndValidRole() throws Exception {
        token = EntitiesFactory.createADMToken();

        var result = mockMvc.perform(delete("/movies/100")
                .header("Authorization", "Bearer " + token.token()));

        result.andExpect(status().isNotFound());
    }

    // delete (role inválida)
    @Test
    public void deleteShouldReturn403WhenInvalidRole() throws Exception {
        token = EntitiesFactory.createToken();

        var result = mockMvc.perform(delete("/movies/100")
                .header("Authorization", "Bearer " + token.token()));

        result.andExpect(status().isForbidden());
    }

    // delete (token inválido)
    @Test
    public void deleteShouldReturn401WhenInvalidToken() {
        try {
            token = EntitiesFactory.createInvalidToken();

            var result = mockMvc.perform(delete("/movies/100")
                    .header("Authorization", "Bearer " + token.token()));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    // update (com sucesso)
    @Test
    public void updateShouldReturn200WhenValidCredentialsAndValidRole() throws Exception {
        token = EntitiesFactory.createADMToken();
        var request = EntitiesFactory.createMovieInsertDto();
        var json = new ObjectMapper().writeValueAsString(request);

        var result = mockMvc.perform(put("/movies/1")
                .header("Authorization", "Bearer " + token.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.subTitle").value(request.getSubTitle()));
    }

    // update (role inválida)
    @Test
    public void updateShouldReturn403WhenInvalidRole() throws Exception {
        token = EntitiesFactory.createToken();
        var request = EntitiesFactory.createInvalidToken();
        var json = new ObjectMapper().writeValueAsString(request);

        var result = mockMvc.perform(put("/movies/1")
                .header("Authorization", "Bearer " + token.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    // update (id inválido)
    @Test
    public void updateShouldReturn404WhenInvalidId() throws Exception {
        token = EntitiesFactory.createADMToken();
        var request = EntitiesFactory.createInvalidToken();
        var json = new ObjectMapper().writeValueAsString(request);

        var result = mockMvc.perform(put("/movies/100")
                .header("Authorization", "Bearer " + token.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    // update (token inválido)
    @Test
    public void updateShouldReturn401WhenInvalidToken() {
        try {
            token = EntitiesFactory.createInvalidToken();
            var request = EntitiesFactory.createInvalidToken();
            var json = new ObjectMapper().writeValueAsString(request);

            var result = mockMvc.perform(put("/movies/100")
                    .header("Authorization", "Bearer " + token.token())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    }

