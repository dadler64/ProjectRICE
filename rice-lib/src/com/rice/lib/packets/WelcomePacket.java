package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class WelcomePacket extends Packet implements Serializable{

    private static final long serialVersionUID = 3L;

    public WelcomePacket() {
        super(3);
    }


}
