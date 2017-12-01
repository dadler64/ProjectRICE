package com.rice.client.thread;

import com.rice.client.Client;
import com.rice.client.util.Print;
import com.rice.lib.Packet;
import com.rice.lib.packets.HandshakePacket;
import com.rice.lib.packets.InitialFilePacket;
import com.rice.lib.packets.ModifyPacket;
import com.rice.lib.packets.WelcomePacket;
import javafx.application.Platform;
import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            new PacketSendThread(toServer).start();
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
                        System.out.println("Wrote Initial File Packet!");
                        toServer.writeObject(new InitialFilePacket(this.client.getCurrentFile().getText(), this.client.getCurrentFile().getFileName()));
                        canUpdate = true;
                    }
                    if (packet instanceof ModifyPacket) {
                        ModifyPacket modifyPacket = (ModifyPacket) packet;
                        final String content = client.getCurrentFile().getTextAreaContent();
                        String modifiedContent = null;
                        switch (modifyPacket.getModifyType()) {
                            case INSERT:
                                if(content.length() <= 0) {
                                    modifiedContent = modifyPacket.getData();
                                } else {
                                    final String data = modifyPacket.getData();
                                    final String start = content.substring(0, modifyPacket.getStartPos());
                                    final String end = content.substring(modifyPacket.getEndPos());
//                                    System.out.println(String.format("StartPos->%d EndPos->%d startstr->%s endstr->%s", modifyPacket.getStartPos(), modifyPacket.getEndPos(), start, end));
                                    modifiedContent = String.format("%s%s%s", start , data, end);
                                }
                                final String finalModifiedContent = modifiedContent;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
//                                        System.out.println(finalModifiedContent);
                                        Client.textAreaModified.add(true);
                                        client.getCurrentFile().setTextArea(finalModifiedContent);
                                    }
                                });
                                break;
                            case DELETE:
                                final String start = content.substring(0, modifyPacket.getStartPos());
                                if(modifyPacket.getEndPos() + 1 > content.length()) {
                                    System.out.println(String.format("StartPos->%d EndPos->%d startstr->%s", modifyPacket.getStartPos(), modifyPacket.getEndPos(), start));
                                    modifiedContent = start;
                                } else {
                                    final String end = content.substring(modifyPacket.getEndPos() + 1);
                                    System.out.println(String.format("StartPos->%d EndPos->%d startstr->%s endstr->%s", modifyPacket.getStartPos(), modifyPacket.getEndPos(), start, end));
                                    modifiedContent = String.format("%s%s", start, end);
                                }
                                final String finalModifiedContent2 = modifiedContent;
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Client.textAreaModified.add(true);
                                        client.getCurrentFile().setTextArea(finalModifiedContent2);
                                    }
                                });
                                break;
                        }
                    }
                }
            }
            socket.close();
            Print.debug("Server Stopped!");
        } catch (IOException | ClassNotFoundException e) {
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
