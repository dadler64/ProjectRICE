package com.rice.server;

import java.util.List;

import static com.rice.server.ServerProtocol.ConnectionStatus.*;

public class ServerProtocol {

    private List<User> users;
    private User currentUser;
    private ConnectionStatus state;

    ServerProtocol(List<User> users) {
        this.users = users;
        this.state = INIT;
    }

    private boolean validateUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public String processInput(String fromClient) {
        String toClient = null;

        switch (state) {
            // Initialization of connection
            case INIT: {
                toClient = "Connected to server!";
                state = SENT_USERNAME;
            }
            // Username was sent by Client
            case SENT_USERNAME: {
                if (validateUsername(fromClient)) {
                    toClient = "100";
                    state = SENT_PASSWORD;
                } else {
                    toClient = "150";
                }
            }
            // Password was sent by Client
            case SENT_PASSWORD: {
                if (currentUser.getPassword().equals(fromClient)) {
                    toClient = "200";
                    state = LOGIN;
                } else {
                    toClient = "250";
                }
            }
            // Finalize login
            case LOGIN: {
                toClient = "You are successfully logged in!";
            }
        }
        return toClient;
    }

    enum ConnectionStatus {
        INIT,
        SENT_USERNAME,
        SENT_PASSWORD,
        LOGIN,
        UNKNOWN,
        ERROR
    }
}