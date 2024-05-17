package com.urilvv.listengo.models;

public class AlbumJsonModel {

    private String albumId;
    private String albumName;

    public AlbumJsonModel(String albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
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

    @Override
    public String toString() {
        return "AlbumJsonModel{" +
                "albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                '}';
    }

}