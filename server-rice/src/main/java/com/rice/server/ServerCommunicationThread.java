package com.rice.server;

import com.rice.server.util.ServerPrint;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerCommunicationThread implements Runnable {

    private int port;
    private boolean run;
    private Server server;

    public ServerCommunicationThread(Server server) {
        this(server, 25000);
    }

    public ServerCommunicationThread(Server server, int port) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        ServerPrint.info("Server Started and listening on port " + port);

        // Set up network connection
        try (
                final ServerSocket serverSocket = new ServerSocket(port);
                final Socket clientSocket = serverSocket.accept();
                final DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                final DataInputStream input = new DataInputStream(clientSocket.getInputStream())
        ) {
            ServerPrint.debug("Server successfully connected to client.");
            String inputLine, outputLine, currentText;
            List<User> loggedIn = server.getUserList();

            run = true;
            while (run) {
                inputLine = input.readUTF();
                if (inputLine != null) {
                    ServerPrint.line(">> Client >> \n" + inputLine);
                }
//                Thread.sleep(3000);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (run) {
            run = false;
            ServerPrint.info("Server stopped!");
        } else {
            ServerPrint.warn("Server is not running...");
        }
    }
}
