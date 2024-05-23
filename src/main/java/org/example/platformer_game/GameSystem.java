package org.example.platformer_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GameSystem extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameSystem.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Register");
        stage.setScene(scene);

        //para di mu resize ang window
        stage.setResizable(false);
        stage.show();
    } 

    public static void main(String[] args) {
        launch();
    }
}