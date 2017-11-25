package com.rice.server;

import com.rice.server.util.ServerPrint;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunicationThread implements Runnable {

    private int port;
    private boolean run;

    public ServerCommunicationThread() {
        this(25000);
    }

    public ServerCommunicationThread(int port) {
        this.port = port;
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

//            output.writeUTF("Test");

            run = true;
            while (run) {
                ServerPrint.line("Connected!");
                Thread.sleep(30000);
            }
        } catch (IOException | InterruptedException e) {
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
