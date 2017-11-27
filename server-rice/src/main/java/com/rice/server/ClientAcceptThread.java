package com.rice.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientAcceptThread extends Thread {

    private boolean shouldRun = true;

    private int port = -1;

    private Server server;

    public ClientAcceptThread(final int port, final Server server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        while (shouldRun) {
            try (final ServerSocket serverSocket = new ServerSocket(port);
                    final Socket clientSocket = serverSocket.accept();
                    final ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
                    final ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {
                this.server.addUser(new User("UNKNOWN", "", UserStatus.UNKNOWN, input, output));
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
