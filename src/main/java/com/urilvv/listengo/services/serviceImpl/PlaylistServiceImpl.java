package com.urilvv.listengo.services.serviceImpl;

import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.repositories.PlaylistRepository;
import com.urilvv.listengo.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) {
        if(playlist == null){
            throw new NullPointerException();
        }
        return playlistRepository.save(playlist);
    }

    @Override
    public Optional<Playlist> searchById(String id) {
        return playlistRepository.findById(id);
    }

    @Override
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

}
