module org.example.platformer_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.platformer_game to javafx.fxml;
    exports org.example.platformer_game;
}