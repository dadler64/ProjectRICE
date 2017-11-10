package com.rice.server.ui;

import com.rice.server.utils.CommandLine;
import com.rice.server.utils.CustomException;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class CommandLinePane extends TitledPane implements EventHandler<KeyEvent> {

    private final TextField textField = new TextField();
    private final TextArea textArea = new TextArea();
    private CommandLine commandLine;

    public CommandLinePane(CommandLine commandLine) {
        this.commandLine = commandLine;
        this.textArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            // This will scroll to the bottom
            textArea.setScrollTop(Double.MAX_VALUE);
            // Use Double.MIN_VALUE to scroll to the top
        });
        this.textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!textField.getText().isEmpty()) {
                    try {
                        commandLine.addToHistory(textField.getText());
//                        writeToConsole(textField.getText());
                        writeToConsole(commandLine.runCommand(textField.getText()));
                        textField.clear();
                    } catch (CustomException e) {
                        e.printStackTrace();
                    }
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
        this.textArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; ");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
//        borderPane.setBottom(textField);
        borderPane.setTop(textField);
        this.setText("Command Line");
        this.setCollapsible(false);
        this.setContent(borderPane);
        initCommandLinePane();
    }

    private void initCommandLinePane() {
    }


    private void writeToConsole(String input) {
        textArea.setText(input);
        textArea.appendText("\n");
    }

    public TextField getTextField() {
        return textField;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!textArea.getText().isEmpty()) {
                    this.commandLine.addToHistory(textArea.getText().split("\n")[0]);
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
