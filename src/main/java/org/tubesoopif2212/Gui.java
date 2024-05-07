package org.tubesoopif2212;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    private static final int DISPLAY_WIDTH = 1280;
    private static final int DISPLAY_HEIGHT = 720;
    private static final int TILE_WIDTH_AMOUNT = 11;
    private static final int TILE_HEIGHT_AMOUNT = 7;
    private static final int TILE_WIDTH = DISPLAY_WIDTH / TILE_WIDTH_AMOUNT;
    private static final int TILE_HEIGHT = DISPLAY_HEIGHT / TILE_HEIGHT_AMOUNT;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), DISPLAY_WIDTH, DISPLAY_HEIGHT);
        stage.setTitle("Guardians of Flora Versus the Legions of Necrosis!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void mainMenu(){
        
    }
}