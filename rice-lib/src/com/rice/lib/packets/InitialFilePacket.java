package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class InitialFilePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 6L;

    private String text;

    private String name;

    public InitialFilePacket(final String text, final String name) {
        super(6);
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
