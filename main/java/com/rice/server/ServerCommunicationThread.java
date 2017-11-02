package com.rice.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicationThread implements Runnable {

    int port = 25000;

    @Override
    public void run() {
        System.out.printf("Server Started and listening on port %d%n", port);

        //Reading the message from the client
        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                InputStream is = clientSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
        ) {
            System.out.println("Server successfully connected to client.");
            while (true) {
                String fromClient;
                if ((fromClient = br.readLine()) != null) {
                    System.out.printf("Client: %s%n", fromClient);
                }
            }
        } catch (IOException e) {
            System.err.printf("Error: Could not find or create socket on port ('%d')%n", port);
            System.err.println(e.getMessage());
        }
    }
}
