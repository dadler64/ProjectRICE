package com.rice.server;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientAcceptThread extends Thread {

    private boolean shouldRun = true;
    private int port = -1;
//    private Server server;

//    public ClientAcceptThread(final Server server, final int port) {
//        this.server = server;
//        this.port = port;
//    }

    public ClientAcceptThread(final int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (shouldRun) {
            try {
                final Socket clientSocket = serverSocket.accept();
                final ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                final ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                Server.addUser(new User("UNKNOWN", "", UserStatus.UNKNOWN, input, output));
                System.out.println("ADDED USER");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }
}
