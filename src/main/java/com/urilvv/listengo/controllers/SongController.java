package com.urilvv.listengo.controllers;

import com.urilvv.listengo.services.PlaylistService;
import com.urilvv.listengo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    private final UserService userService;
    private final PlaylistService playlistService;

    @Autowired
    public SongController(UserService userService, PlaylistService playlistService) {
        this.userService = userService;
        this.playlistService = playlistService;
    }

    /*@PutMapping("/add-song/{trackId}")
    public ResponseEntity addSong(@PathVariable("trackId") String trackId, @RequestParam("playlistId") String playlistId){

    }*/
}
