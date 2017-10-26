package com.rice.server;git add a

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    public String username;
    public String password;
    public boolean isLoggedIn;
    private static List<User> users = new ArrayList<>();

    public User(String username, String password, boolean isLoggedIn) {
        this.username = username; // Shouldn't be more than 16 characters
        this.password = password; // Shouldn't be more than 16 characters
        this.isLoggedIn = isLoggedIn;
        users.add(this);
    }

    public static String getUsername(User user) {
        return user.username;
    }

    public static String getUsernames() {
        StringBuilder sb = new StringBuilder();
        sb.append("       Users:       |     Passwords:     | Login Status:\n");
        sb.append("--------------------|--------------------|---------------\n");
        for (User user: users) {
            sb.append(String.format(" - %s%s |  %s%s  |     %b%n", user.username, getSpaces(user.username), user.password, getSpaces(user.password), user.isLoggedIn));
        }
        sb.append("\n");
        return sb.toString();
    }

    public static String getPassword(User user) {
        return user.username;
    }

    public static int getNumUsers() {
        return users.size();
    }

    public static Map<String, String> getUser(User user) {
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

    private static String getSpaces(String str) {
        StringBuilder sb = new StringBuilder();
        if (str.length() < 16 && str.length() > 0) {
            for (int i = 0; i < (16 - str.length()); i++) {
                sb.append(" ");
            }
        } else {
            sb.append("");
        }
        return sb.toString();
    }
}
