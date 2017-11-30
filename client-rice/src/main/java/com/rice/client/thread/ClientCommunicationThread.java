package com.rice.client.thread;

import com.rice.client.Client;
import com.rice.client.util.Print;
import com.rice.lib.Packet;
import com.rice.lib.packets.HandshakePacket;
import com.rice.lib.packets.InitialFilePacket;
import com.rice.lib.packets.WelcomePacket;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;

public class ClientCommunicationThread implements Runnable {

    private Client client;
    private Socket socket;
    private boolean run = false;
    private String hostname;
    private int port;
    private Pair<String, String> credentials;
    private boolean canUpdate = false;

    public ClientCommunicationThread(Client client, Pair<String, String> credentials) {
        this(client, credentials, "127.0.0.1", 25000);
    }

    public ClientCommunicationThread(Client client, Pair<String, String> credentials, String hostname, int port) {
        this.client = client;
        this.credentials = credentials;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        Print.info("Starting client communication thread!");
        Print.debug("Client=" + client.toString());
        Print.debug("Hostname=" + hostname);
        Print.debug("Port=" + port);

        try {
            final Socket socket = new Socket(hostname, port);
            final ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            final ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            Print.debug("Socket connection success! Connected to server at " + hostname + ":" + port + "!");
            run = true;
            // Send handshake
            toServer.writeObject(new HandshakePacket(credentials.getKey()));
//            toServer.flush();
            System.out.println("Sent hand shake");

            while (run) {
                Packet packet = null;
                while((packet = (Packet)fromServer.readObject()) != null) {
                    if(packet instanceof WelcomePacket) {
                        toServer.writeObject(new InitialFilePacket(this.client.getCurrentFile().getText(), this.client.getCurrentFile().getFileName()));
                        canUpdate = true;
                    }
                }
            }
            socket.close();
            Print.debug("Server Stopped!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        if (run) {
            Print.debug("Stopping server...");
            run = false;
        } else {
            Print.warn("Server is not running...");
        }
    }
}
