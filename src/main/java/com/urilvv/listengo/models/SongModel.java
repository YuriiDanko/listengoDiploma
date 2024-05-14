package com.urilvv.listengo.models;

public class SongModel {

    private String trackId;
    private String artistId;
    private String albumId;

    public SongModel(String songId, String artistName, String albumName) {
        this.trackId = songId;
        this.artistId = artistName;
        this.albumId = albumName;
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

        public SongBuilder builder(){
            return new SongBuilder();
        }

        public SongModel build(){
            return new SongModel(this.trackId, this.artistId, this.albumId);
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

    }

}


