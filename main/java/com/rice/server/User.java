package com.rice.server;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private UserStatus status;

    private static List<User> users = new ArrayList<>();

    public User(String username, String password, UserStatus status) {
        this.username = username; // Shouldn't be more than 16 characters
        this.password = password; // Shouldn't be more than 16 characters
        this.status = status;
        users.add(this);
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

    public static List<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    @Override
    public String toString() {
        return String.format("User [username=%s password=%s status= %s]", username, password , status);
    }

    public static String getUsernames() {
        StringBuilder sb = new StringBuilder();
        sb.append("       Users:       |     Passwords:     | LoginRecord Status:\n");
        sb.append("--------------------|--------------------|---------------\n");
        for (User user: users) {
            sb.append(String.format("  %s%s |  %s%s  |     %b%n", user.username, getSpaces(user.username), user.password, getSpaces(user.password), user.status.toString()));
        }
        sb.append("\n");
        return sb.toString();
    }

/*
    public static int getNumUsers() {
        return users.size();
    }

    public static Map<String, String> getAllUsers(User user) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put(user.username, user.password);
        return userInfo;
    }

    public static Map<String, String> getAllUserInfo() {
        Map<String, String> userInfo = new HashMap<>();
        for (User user: users) {
            userInfo.put(user.username, user.password);
        }
        return userInfo;
    }
*/

    private static String getSpaces(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            sb.append("             ");
            return sb.toString();
        }

        if (str.length() < 16 && str.length() > 0) {
            for (int i = 0; i < (16 - str.length()); i++) {
                sb.append(" ");
            }
        } else {
            for (int i = 0; i < 16; i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
