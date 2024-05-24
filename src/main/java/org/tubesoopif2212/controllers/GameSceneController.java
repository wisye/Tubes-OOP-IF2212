package org.tubesoopif2212.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
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

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.tubesoopif2212.Deck;
import org.tubesoopif2212.PlantFactory;
import javafx.geometry.Side;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import java.io.InputStream;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;

public class GameSceneController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private VBox rootVBox;
    @FXML
    private Pane tilesPane;
    @FXML
    private HBox plantDeckBox;

    private Map<Rectangle, ImageView> tileToPlantMap = new HashMap<>();
    private Map<ImageView, Plants> imageViewToPlantMap = new HashMap<>();

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

        for (Node node : plantDeckBox.getChildren()) {
            if (node instanceof ImageView) {
                ImageView plantImageView = (ImageView) node;
                Plants plant = imageViewToPlantMap.get(plantImageView);
                if (plant != null) {
                    setupDragAndDropForPlant(plantImageView, plant);
                }
            }
        }


        for (Node node : tilesPane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle tile = (Rectangle) node;
                setupDragAndDropForTile(tile);
            }
        }


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

                tile.setLayoutX(col * (tileSize + 8));
                tile.setLayoutY(row * (tileSize + 6));
                tilesPane.getChildren().add(tile);
            }
        }

        for (int row = 0; row < rows; row++) {
            // Tiles di bagian kiri
            Rectangle leftTile = new Rectangle(tileSize, tileSize, Color.rgb(255, 0, 0, 0.5));
            leftTile.setLayoutX(-1 * (tileSize + 8));
            leftTile.setLayoutY(row * (tileSize + 6));
            leftTile.setOnDragOver(event -> event.consume()); // Mencegah drag and drop
            tilesPane.getChildren().add(leftTile);


            Rectangle rightTile = new Rectangle(tileSize, tileSize, Color.rgb(255, 0, 0, 0.5));
            rightTile.setLayoutX(cols * (tileSize + 8));
            rightTile.setLayoutY(row * (tileSize + 6));
            rightTile.setOnDragOver(event -> event.consume());
            tilesPane.getChildren().add(rightTile);
        }

        for (Node node : tilesPane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle tile = (Rectangle) node;
                setupDragAndDropForTile(tile);
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
        HBox topBar = new HBox(30);
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.setStyle("-fx-background-color: #8B4513;");

        InputStream sunImageStream = getClass().getResourceAsStream("/images/Sun.png");
        if (sunImageStream == null) {
            System.err.println("Cannot load sun image, input stream is null");
            return;
        }
        Image sunImage = new Image(sunImageStream);
        ImageView sunImageView = new ImageView(sunImage);
        sunImageView.setFitHeight(50);
        sunImageView.setFitWidth(50);

        VBox sunBox = new VBox(5, sunImageView, sunCountLabel);
        sunBox.setAlignment(Pos.CENTER);
        sunCountLabel.setStyle("-fx-text-fill: white;");

        HBox plantDeckBox = new HBox(5);

        Deck<PlantFactory<? extends Plants>> selectedDeck = Deck.getLastSelectedDeck();
        System.out.println("Selected deck: " + selectedDeck);

        for (PlantFactory<? extends Plants> plantFactory : selectedDeck.getPlants()) {
            String plantName = plantFactory.getName();
            String imagePath = "/images/plants/" + plantName + ".gif";
            InputStream plantImageStream = getClass().getResourceAsStream(imagePath);
            if (plantImageStream == null) {
                System.err.println("Cannot load plant image for " + plantName + ", input stream is null");
                continue;
            }
            Image plantImage = new Image(plantImageStream);
            ImageView plantImageView = new ImageView(plantImage);
            plantImageView.setFitHeight(50);
            plantImageView.setFitWidth(50);
            plantDeckBox.getChildren().add(plantImageView);

            try {
                Plants plant = plantFactory.create(0);
                imageViewToPlantMap.put(plantImageView, plant);
            } catch (Exception e) {
                System.err.println("Cannot create plant for " + plantName + ": " + e.getMessage());
            }
        }

        topBar.getChildren().addAll(sunBox, plantDeckBox);
        rootVBox.getChildren().add(0, topBar);

        for (Node node : plantDeckBox.getChildren()) {
            if (node instanceof ImageView) {
                ImageView plantImageView = (ImageView) node;
                Plants plant = imageViewToPlantMap.get(plantImageView);
                if (plant != null) {
                    setupDragAndDropForPlant(plantImageView, plant);
                }
            }
        }
    }

    private void setupDragAndDropForPlant(ImageView plantImageView, Plants plant) {
        plantImageView.setFitHeight(50);
        plantImageView.setFitWidth(50);

        String plantName = plant.getName();
        plantImageView.setId(plantName);
        plantImageView.setOnDragDetected(event -> {
            Dragboard db = plantImageView.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();
            content.putImage(plantImageView.getImage());
            System.out.println("Plant name (id): " + plantName);
            content.putString(plantName);

            db.setContent(content);

            System.out.println("Drag detected, image: " + db.hasImage() + ", string: " + db.hasString());

            event.consume();
        });
    }

    private void setupDragAndDropForTile(Rectangle tile) {
        if (tile.getFill().equals(Color.rgb(255, 0, 0, 0.5))) {
            return;
        }
        tile.setOnDragOver(event -> {
            System.out.println("Drag over event triggered");
            if (event.getGestureSource() != tile &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        tile.setOnDragDropped(event -> {
            System.out.println("Drag dropped event triggered");
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() && db.hasString()) {
                System.out.println("Dragboard has image and string");
                String plantName = db.getString();
                InputStream plantImageStream = getClass().getResourceAsStream("/images/plants/" + plantName + ".gif");
                if (plantImageStream == null) {
                    System.out.println("Cannot load plant image for " + plantName + ", input stream is null>>>");
                    return;
                }
                Image plantImage = new Image(plantImageStream);
                ImageView plantImageView = new ImageView(plantImage);
                plantImageView.setFitHeight(50);
                plantImageView.setFitWidth(50);

                // Calculate the position of the tile
                double posX = tile.getLayoutX();
                double posY = tile.getLayoutY();

                // Set the position of the ImageView
                plantImageView.setLayoutX(posX);
                plantImageView.setLayoutY(posY);

                // Add the ImageView to the tile's parent, which should be a StackPane
                tilesPane.getChildren().add(plantImageView);
                System.out.println("Number of children in tilesPane: " + tilesPane.getChildren().size());

                // Map the tile to the plant
                tileToPlantMap.put(tile, plantImageView);

                success = true;
            } else {
                System.out.println("Dragboard does not have image and string");
            }
            event.setDropCompleted(success);

            event.consume();
        });
    }

    public void addPlantToGrid(int row, int col, String plantName) {
        File file = new File("src/main/resources/images/plants/" + plantName + ".gif");
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return;
        }
        Image plantImage = new Image(file.toURI().toString());
        ImageView plantImageView = new ImageView(plantImage);
        plantImageView.setFitHeight(50);
        plantImageView.setFitWidth(50);

        gameGrid.add(plantImageView, col, row);
    }

    public void removePlantFromGrid(int row, int col) {
        for (Node node : gameGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                gameGrid.getChildren().remove(node);
                break;
            }
        }
    }

    public void addZombieToGrid(int row, int col, String zombieName) {
        String imagePath = "/images/zombies/" + zombieName + ".gif";
        InputStream zombieImageStream = getClass().getResourceAsStream(imagePath);
        if (zombieImageStream == null) {
            System.err.println("Cannot load zombie image for " + zombieName + ", input stream is null");
            return;
        }
        Image zombieImage = new Image(zombieImageStream);
        ImageView zombieImageView = new ImageView(zombieImage);
        zombieImageView.setFitHeight(50);
        zombieImageView.setFitWidth(50);

        gameGrid.add(zombieImageView, col, row);
    }

    public void removeZombieFromGrid(int row, int col) {
        for (Node node : gameGrid.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                gameGrid.getChildren().remove(node);
                break;
            }
        }
    }
}
