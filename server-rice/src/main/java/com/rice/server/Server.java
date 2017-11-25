/*
 * Copyright [2017] [Dan Adler <adlerd@wit.edu>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rice.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rice.server.ui.Console;
import com.rice.server.util.ServerLogger;
import com.rice.server.util.ServerPrint;
import com.sun.media.jfxmedia.logging.Logger;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Stack;

public class Server extends Application {

    private static Stack<String> commands;
    private Thread thread;
    public static List<User> userList;
    public static boolean GUI = true;
    private ServerCommunicationThread serverCommunicationThread;

    private static void getUsersFromJson(InputStream inputStream) {
        final Reader reader = new InputStreamReader(inputStream);
        final Gson gson = new Gson();
        final Type user = new TypeToken<List<User>>() {
        }.getType();
        userList = gson.fromJson(reader, user);
        ServerPrint.debug(userList.size() + " users loaded.");
    }

    public static void main(String... args) {
        ServerLogger.start(Logger.ERROR, true);
        Application.launch();
    }

    public static List<User> getUserList() {
        return userList;
    }

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        HBox buttonBar = new HBox();
        Button btnQuit = new Button("Quit");
        Button btnStartServer = new Button("Start Server");
        Button btnStopServer = new Button("Stop Server");
        TextArea commandArea = new TextArea();

        // Show 'System.out' in the server console
        Console console = new Console(commandArea);
        PrintStream printStream = new PrintStream(console, true);
        System.setOut(printStream);
        System.setErr(printStream);

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
            commandArea.setScrollTop(Double.MAX_VALUE);// Use Double.MIN_VALUE to scroll to the top
        });
        commandArea.setEditable(false);
        commandArea.setWrapText(false);
        commandArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #ffffff; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff; ");

        ScrollPane scrollPane = new ScrollPane(commandArea);
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
        stage.setOnHiding(event -> System.exit(0));

        // Start server button
        btnStartServer.setAlignment(Pos.CENTER);
        btnStartServer.setOnAction(event -> {
            new Thread(new ServerCommunicationThread()).start();
        });

        // Stop server button
        btnStopServer.setAlignment(Pos.CENTER);
        btnStopServer.setOnAction(event -> {
            ServerPrint.info("Stopping RICE Server...");
            // Stop the networking thread
            serverCommunicationThread.stop();
            ServerPrint.info("RICE Server successfully stopped.");
        });

        // Quit program button
        btnQuit.setAlignment(Pos.CENTER);
        btnQuit.setCancelButton(true);
        btnQuit.setOnAction(e -> {
            ServerPrint.info("Shutting down RICE Server GUI...");
            System.exit(0);
        });
    }
}
