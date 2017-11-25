package com.rice.server.ui;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {

    private TextArea textArea = new TextArea();

    public Console(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int i) throws IOException {
        textArea.appendText(String.valueOf((char) i));
    }
}