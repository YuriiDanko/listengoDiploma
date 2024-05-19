package com.urilvv.listengo.controllers;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.services.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create-playlist")
    public ResponseEntity createPlaylist(@RequestBody Playlist playlist){

    }

}
