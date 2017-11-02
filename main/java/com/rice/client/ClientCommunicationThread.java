package com.rice.client;

import com.rice.server.Server;
import com.rice.universal.User;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import static com.rice.client.ClientCommunicationThread.State.*;

public class ClientCommunicationThread implements Runnable {

    private Client client;
    private Socket socket;
    private boolean run = false;
    private String host = "localhost";
    private int port = 25000;
    private State state;

    public enum State {
        INIT, USERNAME_SENT, PASSWORD_SENT, LOGIN
    };

    public ClientCommunicationThread(Client client, Pair<String, String> credentials) {
        this.client = client;
    }

    public ClientCommunicationThread(Client client, String host, int port) {
        this.client = client;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, port);
            System.out.println("Socket connection success! Running comms thread!");
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            state = INIT;

            while (true) {
                if (state == INIT) {

                    state = USERNAME_SENT;
                    // Send username
                }

                /*  Check that the user exists and also check their state
                        - If user exists it will return 200
                        - If user is logged in it will return 250
                        - If user is blocked it will return 251
                */
                else if (state == USERNAME_SENT) {
                    String returnCode = "";


                    if (returnCode.equals("200")) {
                        state = PASSWORD_SENT;
                        // Send password
                    } else if (returnCode.equals("250")) {
                        System.err.println("That user is alread logged in.");
                    }else if (returnCode.equals("251")) {
                        System.err.println("The username provded does not exist.");
                    } else {
                        System.err.println("The username provded does not exist.");
                    }
                }

                // Check that the user exists and also check their state
                // if okay it will return 200
                // If user is logged in it will return 250
                // is user is blocked it will return 251
                else if (state == PASSWORD_SENT) {

                    state = LOGIN;
                }
            }
//            socket.close();
        } catch (IOException e) {
            System.err.printf("Couldn't get I/O for the connection.%n");
            System.exit(1);
        }
    }

    void stop() throws IOException {
        run = false;
    }

    private boolean validateUserLogin(String username, String password) {
        List<User> users = Server.userList;
        for (User u: users) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }
}
