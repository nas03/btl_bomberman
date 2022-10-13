module uet.oop.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires javafx.media;
    requires java.desktop;

    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;
}