package org.tubesoopif2212.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class GuiController {

    @FXML
    private ImageView titleImageView;
    @FXML
    private GridPane chessBoard;

    private boolean displayTitleImage = true;

    public void initialize() {
        loadImage();
        fillChessBoard();
    }

    private void loadImage() {
        if (titleImageView == null || !displayTitleImage) {
            System.err.println("titleImageView is null. Check FXML injection.");
            return;
        }
        String imagePath = "/images/title.png";
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            System.err.println("Image file not found at path: " + imagePath);
            System.err.println("Absolute path tried: " + getClass().getResource(imagePath));
        } else {
            Image titleImage = new Image(is);
            titleImageView.setImage(titleImage);
            titleImageView.setPreserveRatio(true);
            titleImageView.setFitWidth(500);
            VBox.setMargin(titleImageView, new Insets(-200, -20, -150, 0));
            System.out.println("Image loaded successfully.");
        }
    }

    private void fillChessBoard() {
        if (chessBoard == null) {
            System.err.println("Initialization failed or FXML file is not loaded properly.");
            return;
        }
        int size = 15;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Rectangle square = new Rectangle(50, 50); // Assuming each square is 50x50 pixels
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.LIGHTGREEN);
                } else {
                    square.setFill(Color.DARKGREEN);
                }
                chessBoard.add(square, col, row);
            }
        }
    }

    @FXML
    private void handleNewGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ingame/ChooseDeck.fxml"));
            Stage stage = (Stage) titleImageView.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load ChooseDeck.fxml");
        }
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