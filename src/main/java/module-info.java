module org.example.platformer_game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens org.example.platformer_game to javafx.fxml;
    exports org.example.platformer_game;
}