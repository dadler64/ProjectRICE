package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class DisconnectPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 2L;

    private DisconnectReason disconnectReason;

    public DisconnectPacket(final DisconnectReason disconnectReason) {
        super(2);
        this.disconnectReason = disconnectReason;
    }

    public DisconnectReason getDisconnectReason() {
        return disconnectReason;
    }
}
enum DisconnectReason implements Serializable {
    USER_QUIT, DOCUMENT_CLOSE;
}
