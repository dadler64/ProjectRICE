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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Optional;

public class Client extends Application {

    // Path to FXML file
    private final BorderPane root = new BorderPane();
    private final TextArea textArea = new TextArea();
    private final ButtonBar buttonBar = new ButtonBar();
    private final Button loginBtn = new Button("LoginRecord");
    private final Button logoutBtn = new Button("Logout");
    private final Label loggedIn = new Label("Logged In");
    private final Label loggedOut = new Label("Logged Out");

    @Override
    public void start(Stage primaryStage) {

        buttonBar.getButtons().addAll(loginBtn, logoutBtn);
        buttonBar.setButtonOrder("");
        root.setTop(buttonBar);
        root.setCenter(textArea);

        this.loginBtn.setOnAction(actionEvent -> {
            loginDialog();
        });
        this.logoutBtn.setOnAction(actionEvent -> {
            logoffDialog();
        });


        // Create a new scene using the root
        final Scene scene = new Scene(root, 500, 400);

        primaryStage.setTitle("Project RICE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loginDialog() {
        String imagePath = "/main/resources/login.png";
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("RICE Server LoginRecord");
        dialog.setHeaderText("Please log in with your username and password.");

// Set the icon (must be included in the project).
        try {
            dialog.setGraphic(new ImageView(this.getClass().getResourceAsStream(imagePath).toString()));
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
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }

    private void logoffDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logoff.");
        alert.setHeaderText("Are you sure you want to logoff?");
//        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.out.println("Logging you off...");
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
}

