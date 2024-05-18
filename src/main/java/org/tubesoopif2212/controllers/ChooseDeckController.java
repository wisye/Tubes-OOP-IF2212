package org.tubesoopif2212.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import org.tubesoopif2212.Inventory;
import org.tubesoopif2212.Deck;
import java.io.InputStream;

public class ChooseDeckController {

    @FXML
    private TilePane inventoryTilePane;
    @FXML
    private ListView<String> deckListView;
    private Inventory inventory; // Assume inventory is initialized elsewhere
    private Deck selectedDeck;

    @FXML
    private void initialize() {
        loadInventoryImages();
    }

    private void loadInventoryImages() {
        String[] plantNames = {"Peashooter", "Sunflower", "SnowPea", "Squash", "Wallnut"}; // Example plant names
        for (String plantName : plantNames) {
            InputStream is = getClass().getResourceAsStream("/images/plants/" + plantName + ".gif");
            if (is == null) {
                System.err.println("Could not find image: /images/plants/" + plantName + ".gif");
                continue; // Skip this iteration if the image file is not found
            }
            ImageView imageView = new ImageView(new Image(is));
            imageView.setFitHeight(50); // Adjust size as necessary
            imageView.setFitWidth(50);
            inventoryTilePane.getChildren().add(imageView);

            // Enable drag functionality for imageView
            imageView.setOnDragDetected(event -> {
                // Implement drag logic here
            });
        }
    }

    @FXML
    private void handleSelectDeck() throws IOException {
        // Implementation remains the same
    }
}