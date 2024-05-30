package com.urilvv.listengo.services;

import com.urilvv.listengo.models.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    Playlist createPlaylist(Playlist playlist) throws NullPointerException;
    Optional<Playlist> searchById(String id);
    Playlist save(Playlist playlist);
    Playlist delete(String playlistId);
    List<Playlist> findByPlaylistName(String name, String creator);

}
