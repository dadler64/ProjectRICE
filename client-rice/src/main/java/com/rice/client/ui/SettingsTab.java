package com.rice.client.ui;

import com.rice.client.util.Print;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class SettingsTab extends Tab {

    // Allow only one settings pane to be open
    private static int numOpen = 0;

    public SettingsTab() {
        setUp();
        numOpen++;
        Print.debug("Settings tabs open=" + numOpen);
        this.setOnClosed(e -> {
            numOpen = 0;
            Print.debug("Settings tabs open=" + numOpen);
        });
    }

    public static int getNumOpen() {
        return numOpen;
    }

    private void setUp() {
        BorderPane contentPane = new BorderPane();
        Button test = new Button("Test");

        contentPane.setLeft(test);
        this.setText("Settings");
        this.setContent(contentPane);
    }
}
