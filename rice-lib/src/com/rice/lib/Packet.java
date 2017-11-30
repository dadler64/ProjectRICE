package com.rice.lib;

import java.io.Serializable;

public class Packet implements Serializable{

    private static final long serialVersionUID = 123L;

    private int id;

    public Packet(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
