package com.rice.universal;

import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class CommandLinePane extends BorderPane implements EventHandler<KeyEvent> {

    private final CommandLine commandLine = new CommandLine();
    private final TextField textField = new TextField();
    private final TextArea textArea = new TextArea();

    public CommandLinePane() {
        this.textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!textField.getText().isEmpty()) {
                    commandLine.addToHistory(textField.getText());
                    writeToConsole(textField.getText());
                    textField.clear();
                }
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                textField.clear();
                textField.appendText(commandLine.getPreviousHistory());
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                textField.clear();
                textField.appendText(commandLine.getNextHistory());
            }
        });
        this.textArea.setEditable(false);
        this.textArea.setWrapText(false);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.setTop(textArea);
        this.setBottom(scrollPane);
    }

    private void writeToConsole(String input) {
        textArea.appendText(input + "\n");
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!textArea.getText().isEmpty()) {
                    commandLine.addToHistory(textArea.getText().split("\n")[0]);
                    writeToConsole(textArea.getText());
                    textArea.clear();
                }
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                textArea.clear();
                textArea.appendText(commandLine.getPreviousHistory());
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                textArea.clear();
                textArea.appendText(commandLine.getNextHistory());
            }
    }
}
