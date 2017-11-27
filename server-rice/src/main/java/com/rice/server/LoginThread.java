package com.rice.server;

import javafx.util.Pair;

import java.util.List;

public class LoginThread implements Runnable{
    Pair<String, String> credentials;
    List<User> userList;

    public LoginThread(List<User> userList, Pair<String, String> credentials) {
        this.credentials = credentials;
        this.userList = userList;
    }

    private boolean validateCredentials(List<User> userList, Pair<String, String> credentials) {
        for (User user : userList) {
            if (user.getUsername().equals(credentials.getKey())) {
                if (user.getPassword().equals(credentials.getValue())) {
//                    if (user.getStatus() == UserStatus.LOGGED_OFF) {
                        System.out.println("Correct username and password.");
                        return true;
//                    }
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
//        System.out.println("-----------------------------------------------------");
//        System.out.println("Username=" + credentials.getKey() + ", Password=" + credentials.getValue());
//        System.out.println("Logging user in...");
//        System.out.printf("Validating user credentials for '%s'...%n", credentials.getKey());
//        if (validateCredentials(Server.userList, credentials)) {
////                loginUser();
//            System.out.printf("User '%s' is now logged in.%n", credentials.getKey());
//        } else {
//            System.out.printf("Error: Incorrect login credentials for '%s'.%n", credentials.getKey());
//        }
    }
}
