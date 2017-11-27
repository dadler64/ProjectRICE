package com.rice.lib.packets;

import com.rice.lib.Packet;

public class HandshakePacket extends Packet {

    private String username;

    public HandshakePacket(final String username) {
        super(1);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
