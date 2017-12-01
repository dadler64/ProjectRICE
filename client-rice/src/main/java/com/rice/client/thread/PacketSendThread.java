package com.rice.client.thread;

import com.rice.client.Client;
import com.rice.lib.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PacketSendThread extends Thread {

    private boolean shouldRun = true;

    private ObjectOutputStream outputStream;

    public PacketSendThread(final ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }


    @Override
    public void run() {
        while (shouldRun) {
            Packet packet = null ;
            while((packet = Client.packetQueue.poll()) != null) {
                try {
                    outputStream.writeObject(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setShouldRun(final boolean shouldRun) {
        this.shouldRun = shouldRun;
    }
}
