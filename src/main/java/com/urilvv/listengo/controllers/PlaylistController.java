package com.urilvv.listengo.controllers;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.securityModels.response.ErrorRes;
import com.urilvv.listengo.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/create-playlist/{userId}")
    public ResponseEntity createPlaylist(@PathVariable("userId") String userId, @RequestBody Playlist playlist){
        Playlist pl;
        try{
            pl = playlistService.createPlaylist(playlist);
        } catch(NullPointerException e) {
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pl);
    }

    @PutMapping("/edit-playlist/{playListId}")
    public ResponseEntity editPlaylist(@PathVariable("playListId") String playListId, @RequestBody Playlist playlist){
        Playlist pl = playlistService.searchById(playListId).get();

        pl.setPlaylistName(playlist.getPlaylistName());
        pl.setImageUrl(playlist.getImageUrl());
        pl = playlistService.save(pl);

        return ResponseEntity.ok(pl);
    }


}
