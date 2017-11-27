package com.rice.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class User {
    private String username;
    private String password;
    private UserStatus status;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;


    public User(String username, String password, UserStatus status, final ObjectInputStream inputStream, final ObjectOutputStream outputStream) {
        this.username = username; // Shouldn't be more than 16 characters
        this.password = password; // Shouldn't be more than 16 characters
        this.status = status;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public String toString() {
        return String.format("User [username=%s password=%s status= %s]", username, password , status);
    }
}
