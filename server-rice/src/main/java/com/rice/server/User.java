package com.rice.server;

public class User {
    private String username;
    private String password;
    private UserStatus status;


    public User(String username, String password, UserStatus status) {
        this.username = username; // Shouldn't be more than 16 characters
        this.password = password; // Shouldn't be more than 16 characters
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("User [username=%s password=%s status= %s]", username, password , status);
    }
}
