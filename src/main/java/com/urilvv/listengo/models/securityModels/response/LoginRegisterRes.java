package com.urilvv.listengo.models.securityModels.response;

public class LoginRegisterRes {

    private String username;
    private String token;
    private String userId;
    private String accessToken;

    public LoginRegisterRes(String username, String token, String userId, String accessToken) {
        this.username = username;
        this.token = token;
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}