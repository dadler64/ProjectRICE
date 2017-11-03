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

//import com.rice.server.LoginThread;
//import com.rice.server.Server;
//import com.rice.universal.User;
//import com.rice.universal.UserStatus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.IOException;
import java.util.Optional;

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
    private final ScrollPane scrollPane = new ScrollPane();


    @Override
    public void start(Stage primaryStage) {
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        scrollPane.setContent(codeArea);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        buttonBar.setMinHeight(50);
        buttonBar.setPrefHeight(50);
        buttonBar.setPadding(new Insets(10));
        buttonBar.getButtons().addAll(loginBtn, logoutBtn);

        root.setTop(buttonBar);
        root.setCenter(scrollPane);

//        loginBtn.setOnAction(actionEvent -> loginUser());
        this.loginBtn.setAlignment(Pos.CENTER);
        this.loginBtn.setOnAction(actionEvent -> loginDialog());
        this.logoutBtn.setAlignment(Pos.CENTER);
        this.logoutBtn.setOnAction(actionEvent -> logoffDialog());

        // Create a new scene using the root
        final Scene scene = new Scene(root, 500, 400);

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

    private void loginDialog() {
        String imagePath = "/login.png";
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("RICE Server Login");
        dialog.setHeaderText("Please log in with your username and password.");

        // Set the icon (must be included in the project).
        try {
            dialog.setGraphic(new ImageView(this.getClass().getResource(imagePath).toString()));
        } catch (NullPointerException e) {
            System.err.printf("Error: could not find \'login.png\' at ('%s')%n", imagePath);
        }

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("LoginRecord", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable loginBtn button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the loginBtn button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
//                loginUser();
            } else {
//                return null;
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            // TODO: Add login code
        });


    }

    private void logoffDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logoff.");
        alert.setHeaderText("Are you sure you want to logoff?");
//        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            try {
                System.out.println("Logging you off...");
                serverCommThread.stop();
                loggedIn = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("You are now logged off. [login=%b]%n", loggedIn);
        }
        else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private void loginUser() {
        if (!loggedIn) {
            new Thread(serverCommThread = new ClientCommunicationThread(this, host, port)).start();
            loggedIn = true;
        } else {
            System.err.println("Warning: The server comms thread is already running!");
        }

    }
}

