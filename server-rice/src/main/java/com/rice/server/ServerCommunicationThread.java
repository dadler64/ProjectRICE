package com.rice.server;

import com.rice.lib.Packet;
import com.rice.lib.packets.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerCommunicationThread implements Runnable {

    private int port;
    private boolean run = true;

    public ServerCommunicationThread() {
        this(25000);
    }


    public ServerCommunicationThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Server Started and listening on port " + port);
        ClientAcceptThread clientAcceptThread = new ClientAcceptThread(port);
        clientAcceptThread.start();

        System.out.println("Running!");
        while (run) {
//            System.out.println(getUserList().size());
//            if (!server.getUserList().isEmpty()) {
            final List<User> tempList = new ArrayList<>();
            tempList.addAll(Server.getUserList());
            if(tempList.size() > 0) {
                System.out.println(tempList.size());
            }
            for (final User u : tempList) {
//                System.out.println(u.getStatus().name());
                try {
                    Packet packet = null;
                    while ((packet = (Packet)u.getInputStream().readObject()) != null) {
                        if (packet instanceof HandshakePacket) {
                            System.out.println("Handshake received");
                            final HandshakePacket handshakePacket = (HandshakePacket) packet;
                            u.setUsername(handshakePacket.getUsername());
                            u.setStatus(UserStatus.LOGGED_ON);
                            u.getOutputStream().writeObject(new WelcomePacket());
                            continue;
                        }
                        if (packet instanceof DisconnectPacket) {
                            final DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                            continue;
                        }
                        for (final User u2 : tempList) {
                            if (u2.equals(u)) {
                                continue;
                            }
                            u2.getOutputStream().writeObject(packet);
                        }
                        if (packet instanceof ModifyPacket) {
                            final ModifyPacket modifyPacket = (ModifyPacket) packet;

                        }
                        if (packet instanceof CursorPacket) {
                            final CursorPacket cursorPacket = (CursorPacket) packet;
                        }
                        if(packet instanceof InitialFilePacket) {
                            final InitialFilePacket filePacket = (InitialFilePacket)packet;
                            new Thread(new FileSaveRunnable(new File(String.format("%s.txt", filePacket.getName())), filePacket.getText()));
                        }
                    }
                } catch(EOFException e) {
                    e.printStackTrace();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
//        }
    }

    public void shouldRun(boolean shouldRun) {
        this.run = shouldRun;
    }
}
