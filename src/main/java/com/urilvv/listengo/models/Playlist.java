package com.urilvv.listengo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToMany(mappedBy = "playlists")
    private Set<User> users;

    public Playlist() {
    }

    public Playlist(String playlistId, String playlistName, String imageUrl, String creator, Set<User> users) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.imageUrl = imageUrl;
        this.creator = creator;
        this.users = users;
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

    public static class PlaylistBuilder{

        private String playlistName;
        private String imageUrl;
        private String creator;
        private Set<User> users;

        public static PlaylistBuilder builder(){
            return new PlaylistBuilder();
        }

        public Playlist build(){
            return new Playlist("", this.playlistName, this.imageUrl, this.creator, this.users);
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

    }
}
