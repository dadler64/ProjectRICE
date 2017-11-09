//package com.rice.client;
//
//import com.rice.server.Server;
//import com.rice.universal.User;
//
//import javafx.util.Pair;
//
//import java.util.List;
//
//@SuppressWarnings("Duplicates")
//public class CredentialManager implements Runnable {
//    private List<User> users;
//    private String username;
//    private String password; // TODO: use password hashes instead of actual passwords
//    private Pair<String, String> credentials;
//
//    public CredentialManager(Pair<String, String> credentials, List<User> users) {
//        this.credentials = credentials;
//        this.users = users;
//    }
//
//    @Override
//    public void run() {
//        for (User user: users) {
//            if (user.getUsername().equals(this.credentials.getKey())) {
//                if (user.getPassword().equals(this.credentials.getValue())) {
//
//                    return;
//                } else {
//                    System.err.printf("The password for '%s' is incorrect.", this.credentials.getKey());
//                }
//            } else {
//                System.err.printf("The user '%s' does not exist.", this.credentials.getKey());
//            }
//        }
//        System.out.println("CredentialManager done!");
//    }
//}
