package com.urilvv.listengo.models;

public class SongJsonModel {

    private String trackId;
    private String trackName;
    private String durationMs;
    private String imageUrl;
    private ArtistJsonModel artist;
    private AlbumJsonModel album;

    public SongJsonModel(String trackId, String trackName, String durationMs, String imageUrl, ArtistJsonModel artist, AlbumJsonModel album) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.durationMs = durationMs;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.album = album;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(String durationMs) {
        this.durationMs = durationMs;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArtistJsonModel getArtist() {
        return artist;
    }

    public void setArtist(ArtistJsonModel artist) {
        this.artist = artist;
    }

    public AlbumJsonModel getAlbum() {
        return album;
    }

    public void setAlbum(AlbumJsonModel album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "SongJsonModel{" +
                "trackId='" + trackId + '\'' +
                ", trackName='" + trackName + '\'' +
                ", durationMs='" + durationMs + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", artist=" + artist +
                ", album=" + album +
                '}';
    }

    public static class SongJsonBuilder{

        private String trackId;
        private String trackName;
        private String durationMs;
        private String imageUrl;
        private ArtistJsonModel artist;
        private AlbumJsonModel album;

        public static SongJsonBuilder builder(){
            return new SongJsonBuilder();
        }

        public SongJsonModel build(){
            return new SongJsonModel(trackId, trackName, durationMs, imageUrl, artist, album);
        }

        public SongJsonBuilder trackId(String trackId){
            this.trackId = trackId;
            return this;
        }

        public SongJsonBuilder trackName(String trackName){
            this.trackName = trackName;
            return this;
        }

        public SongJsonBuilder durationMs(String durationMs){
            this.durationMs = durationMs;
            return this;
        }

        public SongJsonBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public SongJsonBuilder artist(ArtistJsonModel artist){
            this.artist = artist;
            return this;
        }

        public SongJsonBuilder album(AlbumJsonModel album){
            this.album = album;
            return this;
        }

    }

}