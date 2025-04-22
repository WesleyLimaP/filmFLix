package com.filmFlix.project_filmFlix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmFlix.project_filmFlix.dtos.genreDtos.GenreDto;
import com.filmFlix.project_filmFlix.services.GenreService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GenreControllerIT {
    @Autowired
    private GenreService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private String token;
    private String admToken;
    private String invalidToken;

    @BeforeEach
    void setUs(){
        token = EntitiesFactory.createToken().token();
        admToken = EntitiesFactory.createADMToken().token();
        invalidToken = EntitiesFactory.createInvalidToken().token();

    }
    //find all
    @Test
    public void findAllShouldReturn200WhenValidToken() throws Exception {
        var result = mockMvc.perform(get("/genre")
                        .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("4"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Anime"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("Comédia"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content[2].name").value("Drama"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.content[3].name").value("Terror"));
    }
    //find by id(sucesso)
    @Test
    public void findByIdShouldReturn200WhenValidToken() throws Exception {
        var result = mockMvc.perform(get("/genre/1")
                        .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Comédia"));

    }
    //find by id (falha de id nao encontrado)
    @Test
    public void findByIdShouldReturn404WhenInvalidToken() throws Exception {
        var result = mockMvc.perform(get("/genre/100")
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }
    //insert (sucesso)
    @Test
    public void insertShouldReturn201WhenValidCredentialsAndToken() throws Exception {
        var genre = EntitiesFactory.createGenreDto();
        var json = objectMapper.writeValueAsString(genre);
        var result = mockMvc.perform(post("/genre")
                        .header("Authorization", "Bearer " + admToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pop"));

   }
   //insert(falha: genero ja existente)
    @Test
    public void insertShouldReturn409WhenInvalidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(new GenreDto("Drama"));
        var result = mockMvc.perform(post("/genre")
                .header("Authorization", "Bearer " + admToken)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isConflict());

    }
    //insert(falha de perfil sem autoridade)

    @Test
    public void insertShouldReturn403WhenInvalidRole() throws Exception {
        var json = objectMapper.writeValueAsString(EntitiesFactory.createGenreDto());
        var result = mockMvc.perform(post("/genre")
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());

    }
    //insert(falha: token invalido)
    @Test
    public void insertShouldReturn401WhenInvalidToken() throws Exception {
        try {
            var genre = EntitiesFactory.createGenreDto();
            var json = objectMapper.writeValueAsString(genre);
            var result = mockMvc.perform(post("/genre")
                    .header("Authorization", "Bearer " + invalidToken)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }
    }

    //update(sucesso)
    @Test
    public void updateShouldReturn200WhenValidCredentialsAndToken() throws Exception {
        var genre = EntitiesFactory.createGenreDto();
        var json = objectMapper.writeValueAsString(genre);
        var result = mockMvc.perform(put("/genre/1")
                        .header("Authorization", "Bearer " + admToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pop"));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));

    }
    //update(falha: id nao encontrado)
    @Test
    public void updateShouldReturn404WhenInvalidId() throws Exception {
        var genre = EntitiesFactory.createGenreDto();
        var json = objectMapper.writeValueAsString(genre);
        var result = mockMvc.perform(put("/genre/6")
                        .header("Authorization", "Bearer " + admToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }
    //update(falha: falha de permissao)
    @Test
    public void updateShouldReturn403WhenInvalidRole() throws Exception {
        var genre = EntitiesFactory.createGenreDto();
        var json = objectMapper.writeValueAsString(genre);
        var result = mockMvc.perform(put("/genre/6")
                        .header("Authorization", "Bearer " + token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());

    }
    //update(falha de genero existente)
    @Test
    public void updateShouldReturn409WhenInvalidCredentials() throws Exception {
        var json = objectMapper.writeValueAsString(new GenreDto("Drama"));
        var result = mockMvc.perform(put("/genre/1")
                        .header("Authorization", "Bearer " + admToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isConflict());

    }
    //update(falha de token invalido)
    @Test
    public void updateShouldReturn401WhenInvalidToken() throws Exception {
        try {
            var genre = EntitiesFactory.createGenreDto();
            var json = objectMapper.writeValueAsString(genre);
            var result = mockMvc.perform(put("/genre/6")
                    .header("Authorization", "Bearer " + invalidToken)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isUnauthorized());
        } catch (Exception e) {
            return;
        }


    }


}
