package com.rice.client.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Separator;

public class CustomSeparator extends Separator {

    private boolean visible = true;

    public CustomSeparator() {
        setUp();
    }


    private void setUp() {
        this.setOrientation(Orientation.VERTICAL);
        this.setHalignment(HPos.CENTER);
        this.setValignment(VPos.CENTER);
        this.setPrefHeight(30);
        this.setMinHeight(30);
        this.setMaxHeight(30);
        this.setPadding(new Insets(0, 5, 0, 5));
    }
}
