package com.rice.client.thread;

import com.rice.client.Client;
import com.rice.client.util.Print;
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

        try (
                Socket socket = new Socket(hostname, port);
                DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
                DataInputStream fromServer = new DataInputStream(socket.getInputStream())
        ) {
            Print.debug("Socket connection success! Connected to server at " + hostname + ":" + port + "!");
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            run = true;
            String inputLine, outputLine;

            // Initiate Client protocol
//            ClientProtocol protocol = new ClientProtocol(credentials);
//            outputLine = protocol.processInput(null);
//            toServer.writeUTF(outputLine);

            while (run) {
//                inputLine = fromServer.readUTF();
//                System.out.println("Server -> " + inputLine);
//                outputLine = protocol.processInput(inputLine);

                outputLine = client.getCurrentFile().getTextAreaContent();
                Print.line("<< Server << \n" + outputLine);
                toServer.writeUTF(outputLine);
                Thread.sleep(3000);
            }
            socket.close();
            Print.debug("Server Stopped!");
        } catch (IOException | InterruptedException e) {
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
