package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.urilvv.listengo.json.jsonModels.SongIdModel;
import com.urilvv.listengo.json.jsonModels.SongIdModel.SongBuilder;
import com.urilvv.listengo.json.jsonUtils.Parser;
import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.services.PlaylistService;
import com.urilvv.listengo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.Set;

@RestController
public class SongController {

    private final UserService userService;
    private final PlaylistService playlistService;
    private final RestTemplate restTemplate;

    @Autowired
    public SongController(UserService userService, PlaylistService playlistService, RestTemplate restTemplate) {
        this.userService = userService;
        this.playlistService = playlistService;
        this.restTemplate = restTemplate;
    }

    @PutMapping({"/add-song/{trackId}"})
    public ResponseEntity addSong(@PathVariable("trackId") String trackId,
                                  @RequestParam("playlistId") String playlistId,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) throws JsonProcessingException {
        String requestUrl = "http://localhost:8080/tracks/" + trackId;
        Playlist pl = playlistService.searchById(playlistId).get();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(jwtToken.split(" ")[1]);
        HttpEntity<?> request = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = this.restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        JsonNode jsonNode = Parser.parseJson(response.getBody());

        SongIdModel song = new SongBuilder().builder()
                    .trackId(jsonNode.get("tracks").get(0).get("trackId").toString().replace("\"", ""))
                    .albumId(jsonNode.get("tracks").get(0).get("album").get("albumId").toString().replace("\"", ""))
                    .artistId(jsonNode.get("tracks").get(0).get("artist").get("artistId").toString().replace("\"", ""))
                    .build();

        pl.getSongs().add(song);
        playlistService.save(pl);

        return ResponseEntity.ok(playlistService.save(pl));
    }

    @PutMapping("/remove-song/{trackId}")
    public ResponseEntity removeSong(@PathVariable("trackId") String trackId,
                                     @RequestParam("playlistId") String playlistId){
        Playlist pl = playlistService.searchById(playlistId).get();

        Set<SongIdModel> songs = pl.getSongs();

        Iterator<SongIdModel> iterator = songs.iterator();

        while(iterator.hasNext()){
            SongIdModel temp = iterator.next();
            if(temp.getTrackId().equals(trackId)){
                iterator.remove();
                break;
            }
        }

        return ResponseEntity.ok(playlistService.save(pl));
    }

}