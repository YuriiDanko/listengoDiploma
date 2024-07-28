package com.urilvv.listengo.services;

import com.urilvv.listengo.json.jsonModels.SongIdModel;
import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.Playlist.PlaylistBuilder;
import com.urilvv.listengo.repositories.PlaylistRepository;
import com.urilvv.listengo.services.serviceImpl.PlaylistServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTests {

    @Mock
    private PlaylistRepository playlistRepository;
    @InjectMocks
    private PlaylistServiceImpl playlistService;

    private Playlist playlist;

    @BeforeEach
    public void setup(){
        playlistService = new PlaylistServiceImpl(playlistRepository);
        playlist = PlaylistBuilder.builder()
                .creator("urilvv")
                .playlistName("Dark Side Of The Moon")
                .isPrivate(true)
                .imageUrl("testImage")
                .build();
    }

    @Test
    public void createPlaylistTest(){
        when(playlistService.createPlaylist(any(Playlist.class))).thenReturn(playlist);

        Playlist createdPlaylist = playlistService.createPlaylist(playlist);

        assertNotNull(createdPlaylist);
        assertEquals(createdPlaylist.getCreator(), playlist.getCreator());
    }

    @Test
    public void searchPlaylistByIdTest(){
        when(playlistService.searchById(any(String.class))).thenReturn(Optional.ofNullable(playlist));

        Playlist searchedPlaylist = playlistService.searchById("Random UUID").get();

        assertNotNull(searchedPlaylist);
        assertEquals(searchedPlaylist.getPlaylistName(), playlist.getPlaylistName());
    }

    @Test
    public void savePlaylistTest(){
        Set<SongIdModel> songs = new HashSet<>();
        songs.add(new SongIdModel());
        songs.add(new SongIdModel());
        songs.add(new SongIdModel());

        playlist.setSongs(songs);

        when(playlistService.save(any(Playlist.class))).thenReturn(playlist);

        Playlist savedPlaylist = playlistService.save(playlist);

        assertNotNull(savedPlaylist);
        assertEquals(savedPlaylist.getSongs().size(), playlist.getSongs().size());
    }

    @Test
    public void deletePlaylistTest(){
        when(playlistRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(playlist));

        assertAll(() -> playlistService.delete("Random UUID"));
    }

    @Test
    public void findPlaylistByPlaylistNameTest(){
        when(playlistService.findByPlaylistName(any(String.class), any(String.class))).thenReturn(List.of(playlist));

        List<Playlist> playlistsFound = playlistService.findByPlaylistName("Random Name", "Random Creator");

        assertNotNull(playlistsFound);
        assertEquals(playlistsFound.get(0), playlist);
    }

}
