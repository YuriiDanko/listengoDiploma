package com.urilvv.listengo.services;

import com.urilvv.listengo.models.Playlist;

public interface PlaylistService {

    Playlist createPlaylist(Playlist playlist) throws NullPointerException;

}
