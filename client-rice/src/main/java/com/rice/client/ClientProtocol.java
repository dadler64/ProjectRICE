package com.rice.client;

import javafx.util.Pair;

import static com.rice.client.ClientProtocol.State.*;

public class ClientProtocol {

    private State state;
    private String username;
    private String password;

    ClientProtocol(Pair<String, String> credentials) {
        this.username = credentials.getKey();
        this.password = credentials.getValue();
        this.state = INIT;
    }

    public String processInput(String fromServer) throws InterruptedException {
        String toServer = "0";


        if (state == INIT) {
            if (fromServer.equals("Connected to server!")) {
                toServer = username;
                state = USERNAME_SENT;
            }
        }

        /*  Check that the user exists and also check their state
                - If user exists it will return 200
                - If user is logged in it will return 250
                - If user is blocked it will return 251
        */
        else if (state == USERNAME_SENT) {
            if (fromServer.equals("100")) {
                System.out.println("Username is valid!");
                // Send password
                toServer = password;
                state = PASSWORD_SENT;
            } else if (fromServer.equals("150")) {
                System.err.println("The username provided does not exist.");
            } else if (fromServer.equals("151")) {
                System.err.println("That user is already logged in.");
            } else {
                System.err.println("Unknown error with username!");
            }
        }

        // Check that the user exists and also check their state
        // if okay it will return 200
        // If user is logged in it will return 250
        // is user is blocked it will return 251
        else if (state == PASSWORD_SENT) {
            if (fromServer.equals("200")) {
                System.out.println("Password accepted!");
                state = LOGIN;
            } else if (fromServer.equals("250")) {
                System.err.println("Error: Password is incorrect!");
            } else if (fromServer.equals("251")) {
                System.err.println("Error: User is blocked!");
            } else {
                System.err.println("Unknown error with password!");
            }
        } else if (state == LOGIN) {
            System.out.println("You are now Logged in!");
            Thread.sleep(3000);
        } else if (state == LOGOFF) {
            System.out.println("Networking thread closed!");
        }
        return toServer;
    }

    enum State {
        INIT,
        USERNAME_SENT,
        PASSWORD_SENT,
        LOGIN,
        LOGOFF
    }
}