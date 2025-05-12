package com.filmFlix.project_filmFlix.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TmdbApiService {
    @Value("${tmdb.api.key}")
    private String tmdbApiKey;



    public String getTrailerYt(String movieId) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/"+movieId+"/videos?language=pt-BR"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + tmdbApiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var json = objectMapper.readTree(response.body());
        if(json.get(0) != null){
           return  "https://www.youtube.com/watch?v=" + json.get("results").get(0).path("key").asText();
        }
        return null;

    }

    public JsonNode getMovie(String title, String subTitle) throws IOException, InterruptedException {
        String query = (title + " " + subTitle).replace(" ", "%20");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query=" + query + "&include_adult=true&language=pt-BR&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + tmdbApiKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.body()).get("results").get(0);
    }
}
