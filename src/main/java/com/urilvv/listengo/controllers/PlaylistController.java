package com.urilvv.listengo.controllers;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.Playlist.PlaylistBuilder;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.securityModels.request.PlaylistReq;
import com.urilvv.listengo.models.securityModels.response.ErrorRes;
import com.urilvv.listengo.services.PlaylistService;
import com.urilvv.listengo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @PostMapping("/create-playlist/{userId}")
    public ResponseEntity createPlaylist(@PathVariable("userId") String userId, @RequestBody PlaylistReq playlistReq){
        User user = userService.searchById(userId).get();
        Set<Playlist> playlists = new HashSet<>();
        Playlist pl = PlaylistBuilder.builder()
                .playlistName(playlistReq.getPlaylistName())
                .creator(userId)
                .imageUrl(playlistReq.getImageUrl())
                .build();
        try{
            pl = playlistService.createPlaylist(pl);
        } catch(NullPointerException e) {
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }

        playlists.add(pl);
        user.setPlaylists(playlists);
        userService.saveUser(user);

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
