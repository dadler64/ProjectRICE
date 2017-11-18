package com.rice.client;

import com.rice.client.util.Print;

public class TestCommThread implements Runnable {

    private boolean run;
    private Client client;

    public TestCommThread(Client client) {
        this.client = client;
        this.run = true;
    }

    @Override
    public void run() {
        Print.debug("Started comms thread!");


//        threadLoop();


        Print.debug("Stopped comms Thread!");
    }

    private void threadLoop() {
        Print.debug("Currently open file ('" + client.getCurrentFile().getFileName() + "')'s content: ");

        while (run) {
            System.out.printf("*---------------------------------------------*%n%s%n", client.getCurrentFile().getTextAreaContent());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void stopThread() {
        run = false;
    }
}
