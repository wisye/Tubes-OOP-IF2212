package org.tubesoopif2212.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;



public class GuiController {

    @FXML
    private ImageView titleImageView;

    public void initialize() {
        String imagePath = "/images/title.png"; // Adjusted path
        InputStream is = getClass().getResourceAsStream(imagePath);
        VBox.setMargin(titleImageView, new Insets(-200, -20, -150, 0));
        if (is == null) {
            System.err.println("Image file not found at path: " + imagePath);
            System.err.println("Absolute path tried: " + getClass().getResource(imagePath));
        } else {
            Image titleImage = new Image(is);
            titleImageView.setImage(titleImage);
            titleImageView.setPreserveRatio(true);
            titleImageView.setFitWidth(500);
            System.out.println("Image loaded successfully.");
        }
    }

    @FXML
    private void handleNewGame() {
        System.out.println("New Game Clicked");
        // Implementasi logika untuk New Game
    }

    @FXML
    private void handleLoadGame() {
        System.out.println("Load Game Clicked");
        // Implementasi logika untuk Load Game
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Created by Kelompok 3 K-1 STI 22");
        alert.showAndWait();
    }
}