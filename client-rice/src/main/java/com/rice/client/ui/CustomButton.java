package com.rice.client.ui;

import com.rice.client.util.Print;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomButton extends Button {

    private boolean isDark;
    private String iconType;
    private String iconName;
    private double width;
    private double height;
    private ImageView icon = new ImageView();

    public CustomButton(String iconType, String iconName, boolean isDark) {
        this.iconType = iconType;
        this.iconName = iconName;
        this.isDark = isDark;
        setUpButton();
    }

    public CustomButton(String iconType, String iconName, boolean isDark, double sideLength) {
        this.iconType = iconType;
        this.iconName = iconName;
        this.isDark = isDark;
        this.width = sideLength;
        this.height = sideLength;
        setUpButton();
    }

    private void setUpButton() {
        this.minWidth(this.height);
        this.minHeight(this.width);
        this.prefWidth(this.height);
        this.prefHeight(this.width);
        this.maxWidth(this.height);
        this.maxHeight(this.width);
        this.icon.setImage(setUpIcon());
        this.icon.setFitWidth(20);
        this.icon.setFitHeight(20);
        this.setGraphic(icon);
    }

    private Image setUpIcon() {
        String path = "";
        Image image;
        if (this.iconType.equals("PNG") || this.iconType.equals("JPG") || this.iconType.equals("JPEG")) {
            if (!isDark) {
                image = new Image(getClass().getResourceAsStream("/icons/dark/" + this.iconName.toLowerCase() + "." + this.iconType.toLowerCase()));
            } else if (isDark) {
                image = new Image(getClass().getResourceAsStream("/icons/light/" + this.iconName.toLowerCase() + "." + this.iconType.toLowerCase()));
            } else {
                image = new Image(getClass().getResourceAsStream("/icons/dark/" + this.iconName.toLowerCase() + "." + this.iconType.toLowerCase()));
            }
//            Image image = new Image(getClass().getResourceAsStream(path));
//            return image;
        } else {
            Print.error("Error: Unsupported image format of image '" + this.iconType + "' at ('" + path + "')");
            image = null;
        }
        return image;
    }
}
