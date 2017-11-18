package com.rice.server.ui;

import com.rice.server.ServerCommunicationThread;
import com.rice.server.util.CommandLine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintStream;

public class ServerGUI extends Application {

    // Path to FXML file
//    private final BorderPane root = new BorderPane();
    private VBox root = new VBox();
    private final ButtonBar buttonBar = new ButtonBar();
    private final Button btnQuit = new Button("Quit");
    private final Button btnStartServer = new Button("Start Server");
    private final Button btnStopServer = new Button("Stop Server");
    private CommandLine commandLine = new CommandLine();
    private final CommandLinePane commandLinePane = new CommandLinePane(commandLine);

    @Override
    public void start(Stage stage) {

        // Show 'System.out' in the server console
        Console console = new Console(commandLinePane);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
//        System.setErr(ps);

        // Disable the textField for the command line temporarily
//        commandLinePane.getTextField().setDisable(true);

        buttonBar.setMinHeight(50);
        buttonBar.setPrefHeight(50);
        buttonBar.getButtons().addAll(btnStartServer, btnStopServer, btnQuit);
        buttonBar.setPadding(new Insets(10));
        buttonBar.minWidthProperty().bind(root.minWidthProperty());
        buttonBar.prefWidthProperty().bind(root.prefWidthProperty());

//        commandLinePane.setMinWidth(500);
        commandLinePane.setMinHeight(550);
//        commandLinePane.setPrefHeight(550);
        commandLinePane.minHeightProperty().bind(root.minHeightProperty());
        commandLinePane.prefHeightProperty().bind(root.prefHeightProperty());
        commandLinePane.minWidthProperty().bind(root.minWidthProperty());
        commandLinePane.prefWidthProperty().bind(root.prefWidthProperty());

//        root.setTop(buttonBar);
//        root.setCenter(commandLinePane);
        root.getChildren().addAll(buttonBar, commandLinePane);

        // Create a new scene using the root
        final Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Project RICE - Server");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
        // Shut everything down if you close the window
        stage.setOnHiding(event -> System.exit(0));

        btnStartServer.setAlignment(Pos.CENTER);
        // Interferes with the ENTER EventListener in CommandLinePane
//        btnStartServer.setDefaultButton(true);
        btnStartServer.setOnAction(event -> {
            System.out.println("Starting RICE Server...");
            // Start the networking thread to connect to potential clients
            new Thread(new ServerCommunicationThread()).start();
//            System.out.println("RICE Server successfully started.");
        });

        btnStopServer.setAlignment(Pos.CENTER);
        btnStopServer.setOnAction(event -> {
            System.out.println("Stopping RICE Server...");
            // Stop the networking thread
//            new Thread(new GUIServerThread(this.commandLine, this.commandLinePane)).start();
            System.out.println("RICE Server successfully stopped.");
        });

        // Simple event handler to quit the program
        btnQuit.setAlignment(Pos.CENTER);
        btnQuit.setCancelButton(true);
        btnQuit.setOnAction(e -> {
            System.out.print("Shutting down RICE Server GUI...");
            System.exit(0);
        });
    }
}
