package com.urilvv.listengo.controllers;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.Playlist.PlaylistBuilder;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.mappers.UserMapper;
import com.urilvv.listengo.models.securityModels.request.PlaylistReq;
import com.urilvv.listengo.models.securityModels.response.ErrorRes;
import com.urilvv.listengo.services.PlaylistService;
import com.urilvv.listengo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @PostMapping("/create-playlist/{userId}")
    public ResponseEntity createPlaylist(@PathVariable("userId") String userId, @RequestBody PlaylistReq playlistReq) {
        User user = userService.searchById(userId).get();
        Set<Playlist> playlists = user.getPlaylists() == null ? new HashSet<>() : user.getPlaylists();
        Playlist pl = PlaylistBuilder.builder()
                .playlistName(playlistReq.getPlaylistName())
                .creator(userId)
                .imageUrl(playlistReq.getImageUrl())
                .songs(new HashSet<>())
                .isPrivate(playlistReq.isPrivate())
                .build();
        try {
            pl = playlistService.createPlaylist(pl);
        } catch (NullPointerException e) {
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRes);
        }

        playlists.add(pl);
        user.setPlaylists(playlists);
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(pl);
    }

    @PutMapping("/edit-playlist/{playlistId}")
    public ResponseEntity editPlaylist(@PathVariable("playlistId") String playlistId, @RequestBody PlaylistReq playlistReq) {
        Playlist pl = playlistService.searchById(playlistId).get();

        pl.setPlaylistName(playlistReq.getPlaylistName());
        pl.setImageUrl(playlistReq.getImageUrl());
        pl = playlistService.save(pl);

        return ResponseEntity.ok(pl);
    }

    @DeleteMapping(path = "/delete-playlist/{playlistId}")
    public ResponseEntity deletePlaylist(@PathVariable("playlistId") String playlistId, @RequestParam("userId") String userId) {
        User user = userService.searchById(userId).get();
        Set<Playlist> playlists = user.getPlaylists();

        if(playlists.isEmpty()){
            return ResponseEntity.badRequest().body(new ErrorRes(HttpStatus.BAD_REQUEST, "User has no playlists added"));
        }

        for (Playlist pl : playlists) {
            if (pl.getPlaylistId().equals(playlistId)) {
                Set<User> users = pl.getUsers();
                playlists.remove(pl);
                break;
            }
        }

        userService.saveUser(user);
        return ResponseEntity.ok(playlistService.delete(playlistId));
    }

    @PutMapping("/add-playlist/{playlistId}")
    public ResponseEntity addPlaylist(@PathVariable("playlistId") String playlistId, @RequestParam("userId") String userId){
        User user = userService.searchById(userId).get();
        user.getPlaylists().add(playlistService.searchById(playlistId).get());
        userService.saveUser(user);
        return ResponseEntity.accepted().body(UserMapper.mapToDto(user));
    }

    @PutMapping("/remove-playlist/{playlistId}")
    public ResponseEntity removePlaylist(@PathVariable("playlistId") String playlistId, @RequestParam("userId") String userId){
        User user = userService.searchById(userId).get();
        Playlist pl = playlistService.searchById(playlistId).get();
        user.getPlaylists().remove(pl);

        if(checkDependencies(pl)){
            playlistService.delete(pl.getPlaylistId());
        }

        userService.saveUser(user);
        return ResponseEntity.accepted().body(UserMapper.mapToDto(user));
    }

    @GetMapping("/get-playlists/{userId}")
    public ResponseEntity getPlaylist(@PathVariable("userId") String userId){
        User user = null;

        try{
            user = userService.searchById(userId).get();
        } catch (Exception e){
            ErrorRes errorRes = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.badRequest().body(errorRes);
        }

        Set<Playlist> playlists = user.getPlaylists().stream().sorted(new Comparator<Playlist>() {
            @Override
            public int compare(Playlist o1, Playlist o2) {
                return o1.getPlaylistName().toLowerCase().compareTo(o2.getPlaylistName().toLowerCase());
            }
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        return ResponseEntity.ok(playlists);
    }

    private boolean checkDependencies(Playlist playlist){
        return playlist.getUsers().isEmpty();
    }

}