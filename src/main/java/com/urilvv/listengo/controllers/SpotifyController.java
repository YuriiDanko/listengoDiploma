package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.urilvv.listengo.json.jsonUtils.JsonInformator;
import com.urilvv.listengo.json.jsonUtils.Parser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

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
        String requestUrl = startUrl + "/recommendations?limit=10&market=ES&seed_artists=4NHQUGzhtTLFvgF5SZesLK&" +
                "seed_genres=classical,country&seed_tracks=0c6xIDDpzE81m2q797ordA";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        System.out.println(accessToken);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return recommendations();
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        System.out.println("Recommendations returned.");

        return Parser.getTracksJson(jsonNode.get("tracks")).toString(4);
    }

    @GetMapping("/search/{searchValue}")
    private String search(@PathVariable("searchValue") String searchValue) throws JsonProcessingException {
        String requestUrl = startUrl + "/search?type=track,artist,album&q=track" + searchValue.replace(" ", "") + "&limit=5";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return search(searchValue);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        JSONObject resultJson = new JSONObject();
        resultJson.put("tracks", Parser.getTracksJson(jsonNode.get("tracks").get("items")));
        resultJson.put("albums", Parser.getAlbumsJson(jsonNode.get("albums").get("items")));
        resultJson.put("artists", Parser.getArtistsJson(jsonNode.get("artists").get("items")));

        System.out.println("Search result returned.");

        return resultJson.toString(4);
    }

    @GetMapping("/{searchType}/{ids}")
    private String searchSongById(@PathVariable("searchType") String searchType,
                                  @PathVariable("ids") String[] ids) throws JsonProcessingException {
        StringBuilder urlBuilder;
        if (ids.length > 1) {
            urlBuilder = new StringBuilder(startUrl + "/" + searchType + "?ids=");
        } else {
            urlBuilder = new StringBuilder(startUrl + "/" + searchType + "/");
        }

        for (String id : ids) {
            urlBuilder.append(id).append(",");
        }
        urlBuilder.replace(urlBuilder.length() - 1, urlBuilder.length(), "");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return searchSongById(searchType, ids);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());
        JSONArray jsonArray = new JSONArray();

        switch (searchType) {
            case "tracks":
                jsonArray = JsonInformator.getTracksInfo(jsonNode);
                break;
            case "artists":
                jsonArray = JsonInformator.getArtistsInfo(jsonNode);
                break;
            case "albums":
                jsonArray = JsonInformator.getAlbumsInfo(jsonNode);
                break;
            default:
                break;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(searchType, jsonArray);

        return jsonObject.toString(4);
    }

}
