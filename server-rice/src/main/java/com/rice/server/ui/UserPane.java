package com.rice.server.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;

//public class UserPane extends BorderPane {
public class UserPane extends TitledPane {

    private static final TextArea USERNAME_AREA = new TextArea();

    // TODO: add a table column for storing the users
    // TODO: add more columns for login status, ip, etc...
    public UserPane() {
        USERNAME_AREA.setEditable(false);
        USERNAME_AREA.setWrapText(false);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(USERNAME_AREA);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        this.setText("User List");
        this.setCollapsible(false);
        this.setContent(scrollPane);
    }

    public static void addUser(String username) {
        USERNAME_AREA.appendText(username + "\n");
    }
}
