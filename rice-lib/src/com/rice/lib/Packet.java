package com.rice.lib;

import java.io.Serializable;

public class Packet implements Serializable{

    private int id;

    public Packet(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
