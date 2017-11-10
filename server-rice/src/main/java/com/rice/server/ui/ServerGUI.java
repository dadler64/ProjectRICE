package com.rice.server.ui;

import com.rice.server.ServerCommunicationThread;
import com.rice.server.utils.CommandLine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class ServerGUI extends Application {

    // Path to FXML file
    private final BorderPane root = new BorderPane();
    private final VBox leftVBoxPanel = new VBox();
    private final ButtonBar buttonBar = new ButtonBar();
    private final Button btnQuit = new Button("Quit");
    private final Button btnStartServer = new Button("Start Server");
    private final Button btnStopServer = new Button("Stop Server");
    private CommandLine commandLine = new CommandLine();
    private final CommandLinePane commandLinePane = new CommandLinePane(commandLine);
    private final UserPane userPane = new UserPane();
    private final StatsPane statsPane = new StatsPane();

    private final OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

    @Override
    public void start(Stage primaryStage) {

        // Show 'System.out' in the server console
        Console console = new Console(commandLinePane);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
//        System.setErr(ps);

        // Disable the textField for the command line temporarily
        commandLinePane.getTextField().setDisable(true);

        buttonBar.setMinHeight(50.0);
        buttonBar.setPrefHeight(50.0);
        buttonBar.getButtons().addAll(btnStartServer, btnStopServer, btnQuit);
        buttonBar.setPadding(new Insets(10));


        userPane.setMinWidth(300.0);
        userPane.setPrefWidth(300.0);
        userPane.setMinHeight(300.0);
        userPane.setPrefHeight(300.0);

        statsPane.setMinWidth(300.0);
        statsPane.setPrefWidth(300.0);
        statsPane.setMinHeight(300.0);
        statsPane.setPrefHeight(300.0);

        leftVBoxPanel.getChildren().addAll(userPane, statsPane);
        commandLinePane.setMinWidth(500.0);
        commandLinePane.setPrefWidth(500.0);
        commandLinePane.setMinHeight(550.0);
        commandLinePane.setPrefHeight(550.0);

        root.setTop(buttonBar);
        root.setLeft(leftVBoxPanel);
        root.setRight(commandLinePane);

        // Create a new scene using the root
        final Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("RICE Server GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
        // Shut everything down if you close the window
        primaryStage.setOnHiding(event -> System.exit(0));

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
