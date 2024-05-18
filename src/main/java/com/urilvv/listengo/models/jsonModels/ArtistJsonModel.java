package com.urilvv.listengo.models.jsonModels;

public class ArtistJsonModel {

    private String artistId;
    private String artistName;
    private String imageUrl;

    public ArtistJsonModel(String artistId, String artistName, String imageUrl) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ArtistJsonModel{" +
                "artistId='" + artistId + '\'' +
                ", artistName='" + artistName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}