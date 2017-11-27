package com.rice.lib.packets;

import com.rice.lib.Packet;

public class DisconnectPacket extends Packet {

    private DisconnectReason disconnectReason;

    public DisconnectPacket(final DisconnectReason disconnectReason) {
        super(2);
        this.disconnectReason = disconnectReason;
    }

    public DisconnectReason getDisconnectReason() {
        return disconnectReason;
    }
}

enum DisconnectReason {
    USER_QUIT, DOCUMENT_CLOSE;
}
