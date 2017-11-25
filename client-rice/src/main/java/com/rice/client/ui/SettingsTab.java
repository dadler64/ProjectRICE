package com.rice.client.ui;

import com.rice.client.util.Print;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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
        Label sliderValue = new Label("Slider Value: 0");
        Slider slider = new Slider();
        slider.valueProperty().addListener(l -> {
            sliderValue.setText(String.format("Slider Value:  %.0f", slider.getValue()));
        });

        contentPane.setLeft(new VBox(test, sliderValue, slider));
        this.setText("Settings");
        this.setContent(contentPane);
    }
}
