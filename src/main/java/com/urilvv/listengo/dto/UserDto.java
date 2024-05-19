package com.urilvv.listengo.dto;

public class UserDto {

    private String userId;
    private String email;
    private String userName;

    public UserDto(String userID, String email, String userName) {
        this.userId = userID;
        this.email = email;
        this.userName = userName;
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

    public static class UserDtoBuilder {

        private String userId;
        private String email;
        private String userName;

        public static UserDtoBuilder builder() {
            return new UserDtoBuilder();
        }

        public UserDto build() {
            return new UserDto(this.userId, this.email, this.userName);
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
    }

}
