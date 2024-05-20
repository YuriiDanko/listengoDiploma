package com.urilvv.listengo.models.securityModels.request;

public class PlaylistReq {

    private String playlistName;
    private String imageUrl;

    public PlaylistReq(String playlistName, String imageUrl) {
        this.playlistName = playlistName;
        this.imageUrl = imageUrl;
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

}
