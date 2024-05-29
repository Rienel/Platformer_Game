package org.example.platformer_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    private Sounds sounds = new Sounds();
    private static boolean isSoundOn = true;

    @FXML
    private void initialize() {
        sounds.setFile(6);
        sounds.loop();
    }

    @FXML
    protected void toggleSound(ActionEvent event) {
        if (isSoundOn) {
            sounds.stop();
        } else {
            sounds.setFile(6);
            sounds.loop();
        }
        isSoundOn = !isSoundOn;
    }

    @FXML
    private void goToLevelUI(ActionEvent event) {
        sounds.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("level-ui.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void onQuit(ActionEvent event) {
        sounds.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLeaderboard(ActionEvent event) {
        sounds.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Leaderboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
