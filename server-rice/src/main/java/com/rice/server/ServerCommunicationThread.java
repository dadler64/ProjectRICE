package com.rice.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicationThread implements Runnable {

    private int port;

    public ServerCommunicationThread() {
        this(25000);
    }

    public ServerCommunicationThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.printf("Server Started and listening on port %d%n", port);

        // Set up network connection
        try (
                final ServerSocket serverSocket = new ServerSocket(port);
                final Socket clientSocket = serverSocket.accept();
                final DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                final DataInputStream input = new DataInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Server successfully connected to client.");
//            String inputLine, outputLine;
//
//            // Initiate protocol
//            ServerProtocol protocol = new ServerProtocol(Server.getUserList());
//            outputLine = protocol.processInput(null);
//            output.writeUTF(outputLine);
//
//            while (true) {
//                inputLine = input.readUTF();
//                System.out.println("Client -> " + inputLine);
//
//                outputLine = protocol.processInput(inputLine);
//            }

            while (true) {
                System.out.println("Connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
