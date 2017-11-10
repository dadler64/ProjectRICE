package com.rice.server.ui;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

public class Console extends OutputStream {

    private TextArea output = new TextArea();

    public Console(CommandLinePane commandLinePane) {
        this.output = commandLinePane.getTextArea();
    }

    @Override
    public void write(int i) throws IOException {
        output.appendText(String.valueOf((char) i));
    }
}