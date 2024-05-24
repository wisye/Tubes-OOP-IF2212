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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.input.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.tubesoopif2212.Plants.Plants;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.Parent;

public class ChooseDeckController {

    @FXML
    private TilePane inventoryTilePane;
    @FXML
    private ListView<String> deckListView;
    @FXML
    private HBox selectDeckHBox;
    @FXML
    private Button removeButton;


    private Inventory inventory; // Assume inventory is initialized elsewhere
    private Deck selectedDeck;

    public ChooseDeckController() {
        this.inventory = new Inventory();
        this.selectedDeck = new Deck();
    }


    @FXML
    public void initialize() {
        loadInventoryImages();
        setupDeckListView();
        setupRemoveButton();
        selectDeckHBox.setPadding(new Insets(10, 0, 10, 0));
    }

    private void loadInventoryImages() {
        String[] plantNames = {"Peashooter", "Sunflower", "Snowpea", "Squash", "Wallnut", "Nahida", "Lilypad", "CeresFauna", "Cannabis", "Planterra"};
        for (String plantName : plantNames) {
            InputStream is = getClass().getResourceAsStream("/images/plants/" + plantName.replace(" ", "") + ".gif");
            if (is == null) {
                System.err.println("Could not find image: /images/plants/" + plantName.replace(" ", "") + ".gif");
                continue;
            }
            ImageView imageView = new ImageView(new Image(is));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);

            Label label = new Label(plantName);

            VBox vbox = new VBox(imageView, label);
            vbox.setSpacing(5);

            enableDrag(imageView, plantName);

            inventoryTilePane.getChildren().add(vbox);
        }
    }

    private void enableDrag(ImageView imageView, String plantName) {
        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(plantName); // Use plant name as data
            db.setContent(content);
            event.consume();
        });
    }

    private void setupDeckListView() {
        deckListView.setOnDragOver(event -> {
            if (event.getGestureSource() != deckListView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        deckListView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String plantName = db.getString();
                try {
                    Plants plant = inventory.getPlantByName(plantName);
                    // This now only updates the status in inventory and adds the plant to the deck
                    inventory.choosePlant(inventory.getPlantIndex(plant), selectedDeck);
                    deckListView.getItems().add(plantName); // Reflect addition in ListView
                    success = true;
                } catch (IllegalStateException e) {
                    showAlert("Error", "Deck is full. Cannot add more plants.");
                } catch (Exception e) {
                    showAlert("Error", e.getMessage());
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleSelectDeck() throws IOException {
        // Simpan selectedDeck ke lastSelectedDeck sebelum beralih scene
        Deck.setLastSelectedDeck(selectedDeck);

        // Load the new FXML file for the game scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ingame/GameScene.fxml"));
        Parent gameSceneRoot = loader.load();

        // Create a new scene with the loaded root
        Scene gameScene = new Scene(gameSceneRoot);

        // Get the current stage (window) using any control (e.g., a button) from the current scene
        Stage stage = (Stage) selectDeckHBox.getScene().getWindow();

        // Set the new scene on the current stage
        stage.setScene(gameScene);

        // Optionally, set the title of the stage
        stage.setTitle("Plant vs Zombie Game");

        // Show the stage
        stage.show();
    }

    // Di dalam class ChooseDeckController

    private void setupRemoveButton() {
        removeButton.setOnAction(event -> {
            String selectedItem = deckListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    selectedDeck.removeByName(selectedItem);
                    deckListView.getItems().remove(selectedItem);
                    inventory.updatePlantStatusToAvailable(selectedItem); // Metode ini perlu Anda implementasikan di kelas Inventory
                } catch (Exception e) {
                    showAlert("Error", "Failed to remove plant from deck: " + e.getMessage());
                }
            }
        });
    }


}