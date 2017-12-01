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
//        while (run) {
////            System.out.println(getUserList().size());
////            if (!server.getUserList().isEmpty()) {
//            final List<User> tempList = new ArrayList<>();
//            tempList.addAll(Server.getUserList());
//            if(tempList.size() > 0) {
//                System.out.println(tempList.size());
//            }
//            for (final User u : tempList) {
////                System.out.println(u.getStatus().name());
//
//            }
//        }
//        }
    }

    public void shouldRun(boolean shouldRun) {
        this.run = shouldRun;
    }
}
