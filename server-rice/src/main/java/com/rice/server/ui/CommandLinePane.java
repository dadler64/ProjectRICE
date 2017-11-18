package com.rice.server.ui;

import com.rice.server.util.CommandLine;
import com.rice.server.util.CustomException;
import com.rice.server.util.Print;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class CommandLinePane extends TitledPane {

    private final TextField textField = new TextField();
    private final TextArea textArea = new TextArea();
    private CommandLine commandLine;

    public CommandLinePane(CommandLine commandLine) {
        this.commandLine = commandLine;
        this.textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!textField.getText().isEmpty()) {
                    try {
                        commandLine.addToHistory(textField.getText());
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
        // Auto-Scroll to the bottom
        textArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            textArea.setScrollTop(Double.MAX_VALUE);
            // Use Double.MIN_VALUE to scroll to the top
        });
        textArea.setEditable(false);
        textArea.setWrapText(false);
        textArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #ffffff; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; ");

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
//        textArea.setText(input);
//        textArea.appendText("\n");
        Print.line(input);
    }

    public TextField getTextField() {
        return textField;
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
