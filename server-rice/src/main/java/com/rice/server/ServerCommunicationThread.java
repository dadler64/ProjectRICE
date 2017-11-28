//package com.rice.server;
//
//import com.rice.lib.Packet;
//import com.rice.lib.packets.*;
//
//import java.io.*;
//
//public class ServerCommunicationThread implements Runnable {
//
//
//    private int port;
//    private boolean run;
//    private Server server;
//
//    public ServerCommunicationThread(Server server) {
//        this(server, 25000);
//    }
//
//    public ServerCommunicationThread(Server Server, int port) {
//        this.server = server;
//        this.port = port;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Server Started and listening on port " + port);
//        ClientAcceptThread clientAcceptThread = new ClientAcceptThread(server, port);
//        clientAcceptThread.start();
//
//        run = true;
//        System.out.println("Running!");
//        while (run) {
////            System.out.println(getUserList().size());
////            if (!server.getUserList().isEmpty()) {
//                for (final User u : server.getUserList()) {
//                    System.out.println(u.getStatus().name());
//                    Packet packet = null;
//                    try {
//                        while ((packet = (Packet) u.getInputStream().readObject()) != null) {
//                            if (packet instanceof HandshakePacket) {
//                                System.out.println("Handshake received");
//                                final HandshakePacket handshakePacket = (HandshakePacket) packet;
//                                u.setUsername(handshakePacket.getUsername());
//                                u.setStatus(UserStatus.LOGGED_ON);
////                                u.getOutputStream().writeObject(new WelcomePacket());
//                                continue;
//                            }
//                            if (packet instanceof DisconnectPacket) {
//                                final DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
//                                continue;
//                            }
//                            for (final User u2 : server.getUserList()) {
//                                if (u2.equals(u)) {
//                                    continue;
//                                }
//                                u2.getOutputStream().writeObject(packet);
//                            }
//                            if (packet instanceof ModifyPacket) {
//                                final ModifyPacket modifyPacket = (ModifyPacket) packet;
//
//                            }
//                            if (packet instanceof CursorPacket) {
//                                final CursorPacket cursorPacket = (CursorPacket) packet;
//                            }
//                        }
//                    } catch (IOException | ClassNotFoundException e) {
////                    System.out.println(e.getMessage());
//                        e.printStackTrace();
//                    }
//                }
//            }
////        }
//    }
//
//    public void shouldRun(boolean shouldRun) {
//        this.run = shouldRun;
//    }
//}
