package com.rice.server.ui;

import com.rice.server.ServerCommunicationThread;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServerGUI extends Application {
    private ServerCommunicationThread serverCommunicationThread;
    private TextArea commandArea;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        HBox buttonBar = new HBox();
        Button btnQuit = new Button("Quit");
        Button btnStartServer = new Button("Start Server");
        Button btnStopServer = new Button("Stop Server");
        commandArea = new TextArea();

        // Show 'System.out' in the server console
//        Console console = new Console(commandArea);
//        SplitOutputStream stream = new SplitOutputStream(console, System.out);
//        PrintStream printStream = new PrintStream(stream, true);
//        System.setOut(printStream);
//        System.setErr(printStream);

        // Set up button bar
        buttonBar.setPrefHeight(50);
        buttonBar.getChildren().addAll(btnStartServer, btnStopServer, btnQuit);
        buttonBar.setPadding(new Insets(10));
        buttonBar.minWidthProperty().bind(root.minWidthProperty());
        buttonBar.prefWidthProperty().bind(root.prefWidthProperty());

        // Set up command area
        commandArea.minHeightProperty().bind(root.minHeightProperty());
        commandArea.prefHeightProperty().bind(root.prefHeightProperty());
        commandArea.minWidthProperty().bind(root.minWidthProperty());
        commandArea.prefWidthProperty().bind(root.prefWidthProperty());

        // Auto-Scroll to the bottom
        commandArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            // Use Double.MIN_VALUE to scroll to the top
            commandArea.setScrollTop(Double.MAX_VALUE);
        });
        commandArea.setEditable(false);
        commandArea.setWrapText(false);
        commandArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #ffffff; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; ");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(commandArea);
        scrollPane.setVisible(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        root.setTop(buttonBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/flat_button_light.css").toExternalForm());

        stage.setTitle("Project RICE - Server");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
        // Shut everything down if you close the window
        stage.setOnHiding(event -> {
            System.out.println("Shutting down...");
//            serverCommunicationThread.shouldRun(false);
            System.exit(0);
        });

        // Start server button
        btnStartServer.setAlignment(Pos.CENTER);
        btnStartServer.setOnAction(event -> {
            System.out.println("Starting server!");
            serverCommunicationThread = new ServerCommunicationThread();
            new Thread(serverCommunicationThread).start();
        });

        // Stop server button
        btnStopServer.setAlignment(Pos.CENTER);
        btnStopServer.setOnAction(event -> {
            System.out.println("Stopping server...");
            serverCommunicationThread.shouldRun(false);
            System.out.println("Server stopped.");
        });

        // Quit program button
        btnQuit.setAlignment(Pos.CENTER);
        btnQuit.setCancelButton(true);
        btnQuit.setOnAction(e -> {
            System.out.println("Shutting down RICE Server GUI...");
            System.exit(0);
        });
    }

    public void writeToConsole(String str) {
        commandArea.appendText(str + "\n");
    }
}
