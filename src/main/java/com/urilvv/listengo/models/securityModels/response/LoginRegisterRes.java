package com.urilvv.listengo.models.securityModels.response;

public class LoginRegisterRes {

    private String username;
    private String token;
    private String userId;

    public LoginRegisterRes(String username, String token, String userId) {
        this.username = username;
        this.token = token;
        this.userId = userId;
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
}