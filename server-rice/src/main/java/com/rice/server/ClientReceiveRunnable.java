package com.rice.server;

import com.rice.lib.Packet;
import com.rice.lib.packets.*;

import java.io.*;

public class ClientReceiveRunnable implements Runnable{

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private User user;

    public ClientReceiveRunnable(final ObjectOutputStream outputStream, final ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            Packet packet = null;
            while ((packet = (Packet)inputStream.readObject()) != null) {
                if (packet instanceof HandshakePacket) {
                    System.out.println("Handshake received");
                    final HandshakePacket handshakePacket = (HandshakePacket) packet;
                    Server.addUser(user = new User(handshakePacket.getUsername(), "", UserStatus.LOGGED_ON, inputStream, outputStream));
                    outputStream.writeObject(new WelcomePacket());
                    continue;
                }
                if (packet instanceof DisconnectPacket) {
                    final DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                    outputStream.close();
                    inputStream.close();
                    continue;
                }
                if(packet instanceof InitialFilePacket) {
                    final InitialFilePacket filePacket = (InitialFilePacket)packet;
                    new Thread(new FileSaveRunnable(new File(String.format("%s.txt", filePacket.getName())), filePacket.getText())).start();
                    continue;
                }
                for (final User u2 : Server.getUserList()) {
                    if (u2.equals(user)) {
                        continue;
                    }
                    System.out.println(String.format("Sending packet %d to others", packet.getId()));
                    u2.getOutputStream().writeObject(packet);
                }
                if (packet instanceof ModifyPacket) {
                    final ModifyPacket modifyPacket = (ModifyPacket) packet;
                }
                if (packet instanceof CursorPacket) {
                    final CursorPacket cursorPacket = (CursorPacket) packet;
                }
            }
        } catch(EOFException e) {
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
