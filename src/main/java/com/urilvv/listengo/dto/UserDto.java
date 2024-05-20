package com.urilvv.listengo.dto;

import com.urilvv.listengo.models.Playlist;

import java.util.Set;

public class UserDto {

    private String userId;
    private String email;
    private String userName;
    private Set<Playlist> playlists;

    public UserDto(String userID, String email, String userName, Set<Playlist> playlists) {
        this.userId = userID;
        this.email = email;
        this.userName = userName;
        this.playlists = playlists;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        this.playlists = playlists;
    }

    public static class UserDtoBuilder {

        private String userId;
        private String email;
        private String userName;
        private Set<Playlist> playlists;

        public static UserDtoBuilder builder() {
            return new UserDtoBuilder();
        }

        public UserDto build() {
            return new UserDto(this.userId, this.email, this.userName, this.playlists);
        }

        public UserDtoBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserDtoBuilder playlists(Set<Playlist> playlists) {
            this.playlists = playlists;
            return this;
        }
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", playlists=" + playlists +
                '}';
    }
}
