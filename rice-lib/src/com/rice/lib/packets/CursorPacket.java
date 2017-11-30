package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class CursorPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 5L;

    private int line, pos;

    public CursorPacket(final int lien, final int pos) {
        super(5);
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
