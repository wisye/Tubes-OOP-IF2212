module org.tubesoopif2212 {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    requires com.almasb.fxgl.all;

    opens org.tubesoopif2212 to javafx.fxml;
    exports org.tubesoopif2212;
    exports org.tubesoopif2212.Zombies;
    exports org.tubesoopif2212.Tile;
    exports org.tubesoopif2212.Plants;
    exports org.tubesoopif2212.controllers;
    opens org.tubesoopif2212.controllers to javafx.fxml;
}