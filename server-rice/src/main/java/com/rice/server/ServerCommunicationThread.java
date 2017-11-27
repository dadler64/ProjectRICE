package com.rice.server;

import com.rice.lib.Packet;
import com.rice.lib.packets.*;
import com.rice.server.util.ServerPrint;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommunicationThread implements Runnable {

    private int port;
    private boolean run;
    private Server server;
    private ClientAcceptThread clientAcceptThread;

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
        clientAcceptThread = new ClientAcceptThread(port, server);
        clientAcceptThread.start();
            run = true;
            while (run) {
                System.out.println(server.getUserList().size());
                for(final User u : server.getUserList()) {
                    System.out.println(u.getStatus().name());
                    Packet packet = null;
                    try {
                        while ((packet = (Packet)u.getInputStream().readObject()) != null) {
                            if(packet instanceof HandshakePacket) {
                                System.out.println("Handshake received");
                                final HandshakePacket handshakePacket = (HandshakePacket)packet;
                                u.setUsername(handshakePacket.getUsername());
                                u.setStatus(UserStatus.LOGGED_ON);
                                u.getOutputStream().writeObject(new WelcomePacket());
                                continue;
                            }
                            if(packet instanceof DisconnectPacket) {
                                final DisconnectPacket disconnectPacket = (DisconnectPacket)packet;
                                continue;
                            }
                            for(final User u2 : server.getUserList()) {
                                if(u2.equals(u)) {
                                    continue;
                                }
                                u2.getOutputStream().writeObject(packet);
                            }
                            if(packet instanceof ModifyPacket) {
                                final ModifyPacket modifyPacket = (ModifyPacket)packet;

                            }
                            if(packet instanceof CursorPacket) {
                                final CursorPacket cursorPacket = (CursorPacket)packet;
                            }
                        }
                    } catch (IOException e) {
                        ServerPrint.error(e.getMessage());
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        ServerPrint.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

    }

    public void stop() {
        if (run) {
            run = false;
            this.clientAcceptThread.setShouldRun(false);
            ServerPrint.info("Server stopped!");
        } else {
            ServerPrint.warn("Server is not running...");
        }
    }
}
