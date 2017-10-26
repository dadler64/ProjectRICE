package com.rice.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    // Path to FXML file
    private final BorderPane root = new BorderPane();
    private final TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {

        root.setCenter(textArea);

        // Create a new scene using the root
        final Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Project RICE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
