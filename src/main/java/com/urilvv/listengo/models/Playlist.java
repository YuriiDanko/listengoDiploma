package com.urilvv.listengo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urilvv.listengo.json.jsonModels.SongIdModel;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String playlistId;
    private String playlistName;
    private String imageUrl;
    private String creator;
    private boolean isPrivate;
    @JsonIgnore
    @ManyToMany(mappedBy = "playlists", cascade = CascadeType.ALL)
    private Set<User> users;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<SongIdModel> songs;


    public Playlist() {
    }

    public Playlist(String playlistId, String playlistName, String imageUrl, String creator, Set<User> users, boolean isPrivate, Set<SongIdModel> songs) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.imageUrl = imageUrl;
        this.creator = creator;
        this.users = users;
        this.isPrivate = isPrivate;
        this.songs = songs;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Set<SongIdModel> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongIdModel> songs) {
        this.songs = songs;
    }

    public static class PlaylistBuilder{

        private String playlistName;
        private String imageUrl;
        private String creator;
        private Set<User> users;
        private Set<SongIdModel> songs;
        private boolean isPrivate;

        public static PlaylistBuilder builder(){
            return new PlaylistBuilder();
        }

        public Playlist build(){
            return new Playlist("", this.playlistName, this.imageUrl, this.creator, this.users, this.isPrivate, this.songs);
        }

        public PlaylistBuilder playlistName(String playlistName){
            this.playlistName = playlistName;
            return this;
        }

        public PlaylistBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public PlaylistBuilder creator(String creator){
            this.creator = creator;
            return this;
        }

        public PlaylistBuilder users(Set<User> users){
            this.users = users;
            return this;
        }

        public PlaylistBuilder isPrivate(boolean isPrivate){
            this.isPrivate = isPrivate;
            return this;
        }

        public PlaylistBuilder songs(Set<SongIdModel> songs){
            this.songs = songs;
            return this;
        }

    }
}
