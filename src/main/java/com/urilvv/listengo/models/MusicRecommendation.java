package com.urilvv.listengo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MusicRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String songName;
    private boolean isLiked;
    private String userId;

    public MusicRecommendation(String songName, boolean isLiked, String userId) {
        this.songName = songName;
        this.isLiked = isLiked;
        this.userId = userId;
    }

    public MusicRecommendation() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

}
