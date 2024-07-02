package com.urilvv.listengo.models.httpModels.request;

public class PlaylistReq {

    private String playlistName;
    private String imageUrl;
    private boolean isPrivate;

    public PlaylistReq(String playlistName, String imageUrl, boolean isPrivate) {
        this.playlistName = playlistName;
        this.imageUrl = imageUrl;
        this.isPrivate = isPrivate;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
