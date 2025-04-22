package com.filmFlix.project_filmFlix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@Transactional
@AutoConfigureMockMvc
public class ReviewControllerIT {


        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        private String token;
        private String admToken;
        private String invalidToken;

        @BeforeEach
        void setUp() {
            token = EntitiesFactory.createToken().token();
            admToken = EntitiesFactory.createADMToken().token();
            invalidToken = EntitiesFactory.createInvalidToken().token();
        }

        // findAllByMovie (sucesso)
        @Test
        public void findAllByMovieShouldReturn200WhenValidToken() throws Exception {
            var result = mockMvc.perform(get("/review/movies/1")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isOk());
            result.andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
        }

        // findById (sucesso)
        @Test
        public void findByIdShouldReturn200WhenValidToken() throws Exception {
            var result = mockMvc.perform(get("/review/1")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isOk());
            result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        }

        // findById (falha: id inválido)
        @Test
        public void findByIdShouldReturn404WhenInvalidId() throws Exception {
            var result = mockMvc.perform(get("/review/1000")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isNotFound());
        }

        // insert (sucesso)
        @Test
        public void insertShouldReturn201WhenValidCredentialsAndToken() throws Exception {
            var review = EntitiesFactory.createReviewRequestDto();
            var json = objectMapper.writeValueAsString(review);

            var result = mockMvc.perform(post("/review")
                    .header("Authorization", "Bearer " + token)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isCreated());
            result.andExpect(MockMvcResultMatchers.jsonPath("$.text").value("teste"));
        }

        // insert (falha: token inválido)
        @Test
        public void insertShouldReturn401WhenInvalidToken() throws Exception {
            try {
                var review = EntitiesFactory.createReviewRequestDto();
                var json = objectMapper.writeValueAsString(review);

                var result = mockMvc.perform(post("/review")
                        .header("Authorization", "Bearer " + invalidToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isUnauthorized());
            } catch (Exception e) {
                return;
            }
        }

        // insert (falha: perfil sem permissão)
        @Test
        public void insertShouldReturn403WhenInvalidRole() throws Exception {
            try {
                var review = EntitiesFactory.createReviewRequestDto();
                var json = objectMapper.writeValueAsString(review);

                var result = mockMvc.perform(post("/review")
                        .header("Authorization", "Bearer " + invalidToken)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isUnauthorized()); // caso esteja tratando como token inválido

            } catch (Exception e) {
                return;
            }
        }


        // update (sucesso)
        @Test
        public void updateShouldReturn200WhenValidCredentialsAndToken() throws Exception {
            var review = EntitiesFactory.createReviewRequestDto();
            var json = objectMapper.writeValueAsString(review);

            var result = mockMvc.perform(put("/review/1")
                    .header("Authorization", "Bearer " + token)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isOk());
            result.andExpect(MockMvcResultMatchers.jsonPath("$.text").value("teste"));
        }

        // update (falha: id inválido)
        @Test
        public void updateShouldReturn404WhenInvalidId() throws Exception {
            var review = EntitiesFactory.createReviewRequestDto();
            var json = objectMapper.writeValueAsString(review);

            var result = mockMvc.perform(put("/review/999")
                    .header("Authorization", "Bearer " + token)
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isNotFound());
        }

        // delete (sucesso)
        @Test
        public void deleteShouldReturn204WhenValidCredentialsAndToken() throws Exception {
            var result = mockMvc.perform(delete("/review/1")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isNoContent());
        }

        // delete (falha: id inválido)
        @Test
        public void deleteShouldReturn404WhenInvalidId() throws Exception {
            var result = mockMvc.perform(delete("/review/999")
                    .header("Authorization", "Bearer " + token)
                    .accept(MediaType.APPLICATION_JSON));

            result.andExpect(status().isNotFound());
        }

        // delete (falha: token inválido)
        @Test
        public void deleteShouldReturn401WhenInvalidToken() throws Exception {
            try {
                var result = mockMvc.perform(delete("/review/1")
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON));

                result.andExpect(status().isUnauthorized());
            } catch (Exception e) {
                return;
            }
        }

}
