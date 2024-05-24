package org.tubesoopif2212.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.geometry.Pos;
import org.tubesoopif2212.Inventory;
import org.tubesoopif2212.Plants.Plants;
import java.util.Map;
import org.tubesoopif2212.Deck;
import org.tubesoopif2212.PlantFactory;
import javafx.geometry.Side;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class GameSceneController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private VBox rootVBox;
    @FXML
    private Pane tilesPane;

    private Label sunCountLabel = new Label("0");

    private final int cols = 9;
    private final int rows = 6;
    private final int tileSize = 51;


    private void adjustBackgroundToFitGridPane() {
        gameGrid.widthProperty().addListener((obs, oldVal, newVal) -> {
            updateBackgroundSizeAndPosition();
        });

        gameGrid.heightProperty().addListener((obs, oldVal, newVal) -> {
            updateBackgroundSizeAndPosition();
        });
    }

    private void updateBackgroundSizeAndPosition() {
        double gridWidth = gameGrid.getWidth();
        double gridHeight = gameGrid.getHeight();

        BackgroundSize bgSize = new BackgroundSize(gridWidth, gridHeight, false, false, false, false);
        BackgroundPosition bgPosition = new BackgroundPosition(Side.LEFT, 0, false, Side.TOP, 0, false);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/Backyardpool.jpg"));
        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, bgPosition, bgSize);

        gameGrid.setBackground(new Background(bgImage));
    }

    public void initialize() {
        tilesPane = new Pane();
        setupBackground();
        setupGameGrid();
        bindGridPaneSizeToParent();
        setupLayout();
        setupTopBar();
        adjustBackgroundToFitGridPane();
        addTilesPane();
    }

    private void setupBackground() {
        final double totalWidth = (cols * tileSize + (cols - 1) * 5) * 1.5;
        final double totalHeight = (rows * tileSize + (rows - 1) * 5) * 1.5;

        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/Backyardpool.jpg"));

        BackgroundSize bgSize = new BackgroundSize(totalWidth, totalHeight, false, false, false, false);
        BackgroundPosition bgPosition = new BackgroundPosition(Side.LEFT, -55, false, Side.TOP, -60, false);

        BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, bgPosition, bgSize);

        gameGrid.setBackground(new Background(bgImage));
    }

    private void addTilesPane() {
        gameGrid.add(tilesPane, 0, 0, cols, rows); 

        tilesPane.prefWidthProperty().bind(gameGrid.widthProperty());
        tilesPane.prefHeightProperty().bind(gameGrid.heightProperty());

        tilesPane.setTranslateX(15);
        tilesPane.setTranslateY(35);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle tile = new Rectangle(tileSize, tileSize);
                if (isWaterTile(row, col)) {
                    tile.setFill(Color.rgb(0, 0, 255, 0.5));
                } else {
                    tile.setFill(Color.rgb(255, 255, 255, 0.5));
                }

                tile.setLayoutX(col * (tileSize + 8)); // Set position within tilesPane
                tile.setLayoutY(row * (tileSize + 6));
                tilesPane.getChildren().add(tile); // Menambahkan Rectangle ke dalam tilesPane
            }
        }
    }

    private void setupGameGrid() {
        gameGrid.setVgap(5);
        gameGrid.setHgap(5);
        gameGrid.setPadding(new Insets(100, 0, 0, 200));
    }

    private void bindGridPaneSizeToParent() {
        gameGrid.prefWidthProperty().bind(rootVBox.widthProperty());
        gameGrid.prefHeightProperty().bind(rootVBox.heightProperty());
    }

    private boolean isWaterTile(int row, int col) {
        return row == 2 || row == 3;
    }

    private void setupLayout() {
        Region spacerTop = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS);

        Region spacerBottom = new Region();
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        rootVBox.getChildren().add(0, spacerTop);
        rootVBox.getChildren().add(spacerBottom);
    }

    private void setupTopBar() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-background-color: #8B4513;");

        Image sunImage = new Image(getClass().getResourceAsStream("/images/Sun.png"));
        ImageView sunImageView = new ImageView(sunImage);
        sunImageView.setFitHeight(50);
        sunImageView.setFitWidth(50);

        VBox sunBox = new VBox(5, sunImageView, sunCountLabel);
        sunBox.setAlignment(Pos.CENTER);
        sunCountLabel.setStyle("-fx-text-fill: white;");

        HBox plantDeckBox = new HBox(5);

        Deck<PlantFactory<? extends Plants>> selectedDeck = Deck.getLastSelectedDeck();

        for (PlantFactory<? extends Plants> plantFactory : selectedDeck.getPlants()) {
            String plantName = plantFactory.getName();
            String imagePath = "/images/plants/" + plantName + ".gif";
            Image plantImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView plantImageView = new ImageView(plantImage);
            plantImageView.setFitHeight(50);
            plantImageView.setFitWidth(50);
            plantDeckBox.getChildren().add(plantImageView);
        }

        topBar.getChildren().addAll(sunBox, plantDeckBox);
        rootVBox.getChildren().add(0, topBar);
    }
}
