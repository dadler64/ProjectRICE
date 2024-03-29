package com.rice.lib.packets;

import com.rice.lib.Packet;

import java.io.Serializable;

public class ModifyPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 4L;

    private String data;

    private ModifyType modifyType;

    private int startLine, endLine, startPos, endPos;

    public ModifyPacket(final String data, final ModifyType modifyType, final int startLine, final int endLine, final int startPos, final int endPos) {
        super(4);
        this.data = data;
        this.modifyType = modifyType;
        this.startLine = startLine;
        this.endLine = endLine;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public ModifyPacket(final String data, final ModifyType modifyType, final int line, final int pos) {
        this(data, modifyType, line, line, pos, pos);

    }

    public String getData() {
        return data;
    }

    public ModifyType getModifyType() {
        return modifyType;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getStartPos() {
        return startPos;
    }

    public int getEndPos() {
        return endPos;
    }
}