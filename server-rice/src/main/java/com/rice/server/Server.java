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

import com.rice.lib.Packet;
import com.rice.lib.packets.CursorPacket;
import com.rice.lib.packets.DisconnectPacket;
import com.rice.lib.packets.HandshakePacket;
import com.rice.lib.packets.ModifyPacket;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Application {

    private static List<User> userList = new ArrayList<>();
    private ServerCommunicationThread serverCommunicationThread;
    private TextArea commandArea;

//    private static void getUsersFromJson(InputStream inputStream) {
//        final Reader reader = new InputStreamReader(inputStream);
//        final Gson gson = new Gson();
//        final Type user = new TypeToken<List<User>>() {
//        }.getType();
//        userList = gson.fromJson(reader, user);
//        ServerPrint.debug(userList.size() + " users loaded.");
//    }

    public static void main(String... args) {
        Application.launch();
    }

    public static List<User> getUserList() {
        return userList;
    }

    public static void addUser(User user) {
        userList.add(user);
    }

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

class ServerCommunicationThread implements Runnable {


    private int port;
    private boolean run;

    public ServerCommunicationThread() {
        this(25000);
    }


    public ServerCommunicationThread(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Server Started and listening on port " + port);
        ClientAcceptThread clientAcceptThread = new ClientAcceptThread(port);
        clientAcceptThread.start();

        run = true;
        System.out.println("Running!");
        while (run) {
//            System.out.println(getUserList().size());
//            if (!server.getUserList().isEmpty()) {
            for (final User u : Server.getUserList()) {
//                System.out.println(u.getStatus().name());
                Packet packet = null;
                try {
                    while ((packet = (Packet) u.getInputStream().readObject()) != null) {
                        if (packet instanceof HandshakePacket) {
                            System.out.println("Handshake received");
                            final HandshakePacket handshakePacket = (HandshakePacket) packet;
                            u.setUsername(handshakePacket.getUsername());
                            u.setStatus(UserStatus.LOGGED_ON);
//                                u.getOutputStream().writeObject(new WelcomePacket());
                            continue;
                        }
                        if (packet instanceof DisconnectPacket) {
                            final DisconnectPacket disconnectPacket = (DisconnectPacket) packet;
                            continue;
                        }
                        for (final User u2 : Server.getUserList()) {
                            if (u2.equals(u)) {
                                continue;
                            }
                            u2.getOutputStream().writeObject(packet);
                        }
                        if (packet instanceof ModifyPacket) {
                            final ModifyPacket modifyPacket = (ModifyPacket) packet;

                        }
                        if (packet instanceof CursorPacket) {
                            final CursorPacket cursorPacket = (CursorPacket) packet;
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
//        }
    }

    public void shouldRun(boolean shouldRun) {
        this.run = shouldRun;
    }
}

