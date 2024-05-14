package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.urilvv.listengo.models.SongModel;
import com.urilvv.listengo.models.SongModel.SongBuilder;
import com.urilvv.listengo.services.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class SpotifyController {

    private String accessToken;
    private final WebApplicationContext webApplicationContext;
    private final RestTemplate restTemplate;
    @Value("${spotify.url}")
    private String startUrl;

    public SpotifyController(String accessToken, WebApplicationContext applicationContext, RestTemplate restTemplate) {
        this.accessToken = accessToken;
        this.webApplicationContext = applicationContext;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/recommendations")
    private String recommendations() throws JsonProcessingException {
        String requestUrl = startUrl + "/recommendations?limit=10&market=ES&seed_artists=4NHQUGzhtTLFvgF5SZesLK&seed_genres=classical,country&seed_tracks=0c6xIDDpzE81m2q797ordA";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            accessToken = webApplicationContext.getBean(String.class);
            return recommendations();
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());
        ArrayList<SongModel> songs = new ArrayList<>();

        for (JsonNode node : jsonNode.get("tracks")) {
            SongModel song = new SongBuilder().builder()
                    .trackId(node.get("id").toString())
                    .albumId(node.get("album").get("id").toString())
                    .artistId(node.get("album").get("artists").findValue("id").toString())
                    .build();

            songs.add(song);
        }

        for (SongModel songModel : songs) {
            System.out.println(songModel);
        }


        return response.getBody();
    }

    @GetMapping("/search/{searchValue}")
    private Map search(@PathVariable("searchValue") String searchValue) {
        String requestUrl = startUrl + "/search?type=track,artist,album&q=track" + searchValue + "&limit=5";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Map> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, Map.class);
        } catch (HttpClientErrorException errorException) {
            accessToken = webApplicationContext.getBean(String.class);
            return search(searchValue);
        }

        return response.getBody();
    }

    @GetMapping("/{searchType}/{id}")
    private Map searchSongById(@PathVariable("searchType") String searchType, @PathVariable("id") String id) {
        String requestUrl = startUrl + "/" + searchType + "/" + id;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Map> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, Map.class);
        } catch (HttpClientErrorException errorException) {
            accessToken = webApplicationContext.getBean(String.class);
            return searchSongById(searchType, id);
        }

        return response.getBody();
    }

}
