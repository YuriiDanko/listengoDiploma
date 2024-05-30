package com.urilvv.listengo.models.jsonModels;

public class AlbumJsonModel {

    private String albumId;
    private String albumName;
    private String imageUrl;
    private String artistName;

    public AlbumJsonModel(String albumId, String albumName, String imageUrl, String artistName) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.imageUrl = imageUrl;
        this.artistName = artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "AlbumJsonModel{" +
                "albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                '}';
    }

}