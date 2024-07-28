package com.urilvv.listengo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urilvv.listengo.jwt.JwtAuthorizationFilter;
import com.urilvv.listengo.jwt.jwtUtils.JwtBuilderClass;
import com.urilvv.listengo.models.Playlist;
import com.urilvv.listengo.models.User;
import com.urilvv.listengo.models.httpModels.request.PlaylistReq;
import com.urilvv.listengo.services.PlaylistService;
import com.urilvv.listengo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlaylistController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PlaylistControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaylistService playlistService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtBuilderClass jwtBuilderClass;
    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    private ObjectMapper objectMapper;

    private Playlist playlist;
    private Playlist editedPlaylist;
    private PlaylistReq playlistReq;
    private User user;

    @BeforeEach
    public void setup(){
        user = new User();
        user.setUserName("Urilvv");
        user.setEmail("urilvv1@gmail.com");
        user.setPlaylists(new HashSet<>());
        playlist = Playlist.PlaylistBuilder.builder()
                .creator("urilvv")
                .playlistName("Dark Side Of The Moon")
                .isPrivate(true)
                .imageUrl("testImage")
                .users(Set.of(user))
                .build();
        editedPlaylist = Playlist.PlaylistBuilder.builder()
                .creator("Edited")
                .playlistName("Dark Side Of The Moon")
                .isPrivate(true)
                .imageUrl("testImage")
                .build();
        playlistReq = new PlaylistReq("Playlist", "imagePlaylist", false);
    }

    @Test
    public void createPlaylistTest() throws Exception {
        when(userService.searchById(any(String.class))).thenReturn(Optional.ofNullable(user));
        when(playlistService.createPlaylist(any(Playlist.class))).thenReturn(playlist);

        mockMvc.perform(post("/create-playlist/" + user.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playlistReq)))
                .andExpect(status().isCreated());
    }

    @Test
    public void editPlaylistTest() throws Exception {
        String playlistId = "randomUUID";

        when(playlistService.searchById(any(String.class))).thenReturn(Optional.ofNullable(playlist));
        when(playlistService.save(any(Playlist.class))).thenReturn(editedPlaylist);

        mockMvc.perform(put("/edit-playlist/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playlistReq)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePlaylistTest() throws Exception {
        String playlistId = "randomUUID";
        when(userService.searchById(any(String.class))).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(delete("/delete-playlist/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "userId"))
                .andExpect(status().isBadRequest());

        Set<Playlist> playlists = Set.of(playlist, editedPlaylist);
        user.setPlaylists(playlists);
        when(playlistService.delete(any(String.class))).thenReturn(playlist);

        mockMvc.perform(delete("/delete-playlist/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "userId"))
                .andExpect(status().isOk());
    }

    @Test
    public void addAndRemovePlaylistTest() throws Exception {
        String playlistId = "random UUID";
        when(userService.searchById(any(String.class))).thenReturn(Optional.ofNullable(user));
        when(playlistService.searchById(any(String.class))).thenReturn(Optional.ofNullable(playlist));

        mockMvc.perform(put("/add-playlist/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "userId"))
                .andExpect(status().isAccepted());

        mockMvc.perform(put("/remove-playlist/" + playlistId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "userId"))
                .andExpect(status().isAccepted());
    }

}
