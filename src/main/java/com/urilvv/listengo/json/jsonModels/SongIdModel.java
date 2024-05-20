package com.urilvv.listengo.json.jsonModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urilvv.listengo.models.Playlist;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class SongIdModel {

    @Id
    private String trackId;
    private String artistId;
    private String albumId;
    @JsonIgnore
    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists;

    public SongIdModel() {}

    public SongIdModel(String songId, String artistName, String albumName, Set<Playlist> playlists) {
        this.trackId = songId;
        this.artistId = artistName;
        this.albumId = albumName;
        this.playlists = playlists;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    @Override
    public String toString() {
        return "SongModel{" +
                "trackId=" + trackId +
                ", artistId='" + artistId + '\'' +
                ", albumId='" + albumId + '\'' +
                '}';
    }

    public static class SongBuilder{

        private String trackId;
        private String artistId;
        private String albumId;
        private Set<Playlist> playlists;

        public SongBuilder builder(){
            return new SongBuilder();
        }

        public SongIdModel build(){
            return new SongIdModel(this.trackId, this.artistId, this.albumId, this.playlists);
        }

        public SongBuilder trackId(String id){
            this.trackId = id;
            return this;
        }

        public SongBuilder albumId(String albumId){
            this.albumId = albumId;
            return this;
        }

        public SongBuilder artistId(String artistId){
            this.artistId = artistId;
            return this;
        }

        public SongBuilder playlists(Set<Playlist> playlists){
            this.playlists = playlists;
            return this;
        }

    }

}


