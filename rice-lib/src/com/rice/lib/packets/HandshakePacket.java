package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class HandshakePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    public HandshakePacket(final String username) {
        super(1);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
