package com.urilvv.listengo.models.httpModels.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveAnswerReq {

    @JsonProperty("songName")
    private String songName;
    @JsonProperty("isLiked")
    private boolean isLiked;

    public SaveAnswerReq(String songName, boolean isLiked) {
        this.songName = songName;
        this.isLiked = isLiked;
    }

    public SaveAnswerReq(){}

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public String toString() {
        return "SaveAnswerReq{" +
                "songName='" + songName + '\'' +
                ", isLiked=" + isLiked +
                '}';
    }

}
