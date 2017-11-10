package com.rice.server.ui;

import com.rice.server.User;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

//public class UserPane extends BorderPane {
public class UserPane extends TitledPane {

    private static final TextArea USERNAME_AREA = new TextArea();

    private ObservableList<User> users;
    private TableView<User> userTable;
    private TableColumn<User, String> userColumn;
    private TableColumn<User, String> statusColumn;

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
