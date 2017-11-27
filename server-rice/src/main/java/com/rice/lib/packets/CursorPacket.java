package com.rice.lib.packets;

import com.rice.lib.Packet;

public class CursorPacket extends Packet {

    private int line, pos;

    public CursorPacket(final int lien, final int pos) {
        super(4);
        this.line = line;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public int getLine() {

        return line;
    }
}
