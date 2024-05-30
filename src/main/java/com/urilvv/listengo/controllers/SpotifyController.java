package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.urilvv.listengo.json.jsonUtils.JsonInformator;
import com.urilvv.listengo.json.jsonUtils.Parser;
import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.services.PlaylistService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

@RestController
public class SpotifyController {

    private String accessToken;
    private final WebApplicationContext webApplicationContext;
    private final RestTemplate restTemplate;
    private final PlaylistService playlistService;
    @Value("${spotify.url}")
    private String startUrl;
    private final Logger logger;

    @Autowired
    public SpotifyController(String accessToken, WebApplicationContext applicationContext, RestTemplate restTemplate, PlaylistService playlistService, Logger logger) {
        this.accessToken = accessToken;
        this.webApplicationContext = applicationContext;
        this.restTemplate = restTemplate;
        this.playlistService = playlistService;
        this.logger = logger;
    }

    @GetMapping("/recommendations/{genre}")
    private String recommendations(@PathVariable("genre") String genre) throws JsonProcessingException {
        String requestUrl = startUrl + "/recommendations?limit=40&market=ES&seed_artists=4NHQUGzhtTLFvgF5SZesLK&" +
                "seed_genres=" + genre + "&seed_tracks=0c6xIDDpzE81m2q797ordA";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return recommendations(genre);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        logger.info("Recommendation returned");

        return Parser.getTracksJson(jsonNode.get("tracks")).toString(4);
    }

    @GetMapping("/search/{searchValue}/{userId}")
    private String search(@PathVariable("searchValue") String searchValue, @PathVariable("userId") String userId) throws JsonProcessingException {
        String requestUrl = startUrl + "/search?type=track,artist,album&q=track" + searchValue.replace(" ", "") + "&limit=14";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return search(searchValue, userId);
        }

        ArrayList<Playlist> playlists = (ArrayList<Playlist>) playlistService.findByPlaylistName(searchValue, userId);

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        JSONObject resultJson = new JSONObject();
        resultJson.put("tracks", Parser.getTracksJson(jsonNode.get("tracks").get("items")));
        resultJson.put("albums", Parser.getAlbumsJson(jsonNode.get("albums").get("items")));
        resultJson.put("artists", Parser.getArtistsJson(jsonNode.get("artists").get("items")));
        resultJson.put("playlists", new JSONArray(Parser.toJson(playlists)));

        logger.info("Search results returned");

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
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
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

        logger.info("Search result by " + searchType + " returned");

        return jsonObject.toString(4);
    }

    @GetMapping("/albums/{albumId}/tracks")
    private String getAlbumTracks(@PathVariable("albumId") String albumId) throws JsonProcessingException {
        String requestUrl = startUrl + "/albums/" + albumId + "/tracks";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode() != HttpStatusCode.valueOf(401)) {
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return getAlbumTracks(albumId);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        logger.info("Album tracks returned");

        return Parser.getAlbumTracksJson(jsonNode.get("items"), albumId);
    }

    @GetMapping("/artists/{artistId}/top-tracks")
    private String getTopTracks(@PathVariable("artistId") String artistId) throws JsonProcessingException {
        String requestUrl = startUrl + "/artists/" + artistId + "/top-tracks";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpClientErrorException errorException){
            if(errorException.getStatusCode() != HttpStatus.valueOf(401)){
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return getTopTracks(artistId);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        logger.info("Artist top-tracks returned");

        return Parser.getTracksJson(jsonNode.get("tracks")).toString(4);
    }

    @GetMapping("/artist/{artistId}/albums")
    public String getArtistAlbums(@PathVariable("artistId") String artistId) throws JsonProcessingException {
        String requestUrl = startUrl + "/artists/" + artistId + "/albums?limit=10";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(requestUrl, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpClientErrorException errorException){
            if(errorException.getStatusCode() != HttpStatus.valueOf(401)){
                logger.error("Error occurred: " + errorException.getStatusCode() + " " + errorException.getMessage());
                return errorException.getResponseBodyAsString();
            }
            accessToken = webApplicationContext.getBean(String.class);
            return getTopTracks(artistId);
        }

        JsonNode jsonNode = Parser.parseJson(response.getBody());

        return Parser.getAlbumsJson(jsonNode.get("items")).toString(4);
    }

}
