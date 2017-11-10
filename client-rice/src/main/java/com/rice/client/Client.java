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

package com.rice.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

public class Client extends Application {
    private boolean loggedIn = false;
    private final String ip = "172.0.0.1";
    private final String host = "localhost";
    private final int port = 25000;
    private CodeArea codeArea;
    private ClientCommunicationThread serverCommThread;

    private final BorderPane root = new BorderPane();
    private final ButtonBar buttonBar = new ButtonBar();
    private final Button loginBtn = new Button("Login");
    private final Button logoutBtn = new Button("Logout");
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final ScrollPane scrollPane = new ScrollPane();


    @Override
    public void start(Stage primaryStage) {
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");

        // Preset text fields for quicker testing
        usernameField.setText("dadler");
        passwordField.setText("password");

        scrollPane.setContent(codeArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        buttonBar.setMinHeight(50);
        buttonBar.setPrefHeight(50);
        buttonBar.setPadding(new Insets(10));
        buttonBar.getButtons().addAll(usernameField, passwordField, loginBtn, logoutBtn);

        root.setTop(buttonBar);
        root.setCenter(scrollPane);

        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setOnAction(actionEvent -> {
            System.out.println("Username=" + usernameField.getText());
            System.out.println("Password=" + passwordField.getText());
            if (!usernameField.getText().equals("") && !passwordField.getText().equals("")) {
                loginUser(new Pair<>(usernameField.getText(), passwordField.getText()));
            } else {
                System.err.println("Error: You must enter a valid username and password!");
            }
        });

        logoutBtn.setAlignment(Pos.CENTER);
        logoutBtn.setOnAction(actionEvent -> {
            serverCommThread.stopThread();
        });

        // Create a new scene using the root
        final Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Project RICE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public String getCodeAreaText() {
        return this.codeArea.getText();
    }

    private void loginUser(Pair<String, String> credentials) {
//        if (!loggedIn) {
//            loggedIn = true;
        new Thread(serverCommThread = new ClientCommunicationThread(this, credentials, ip, port)).start();
//        } else {
//            System.err.println("Warning: The server comms thread is already running!");
//        }
    }
}

