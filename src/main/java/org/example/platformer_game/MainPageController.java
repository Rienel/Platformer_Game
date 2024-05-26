package org.example.platformer_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private ImageView soundImageView;

    private Sounds sounds = new Sounds();
    private static boolean isSoundOn = true;


    @FXML
    private void initialize() {
        // Play the main page music when this controller is initialized
        sounds.setFile(6);  // Assuming index 6 is for MainPage.wav
        sounds.loop();
    }

    @FXML
    protected void toggleSound(ActionEvent event) {
        if (isSoundOn) {
            sounds.stop();
            soundImageView.setImage(new Image(getClass().getResource("src/main/resources/soundoff.png").toString()));
        } else {
            sounds.setFile(6);
            sounds.loop();
            soundImageView.setImage(new Image(getClass().getResource("soundon.png").toString()));
        }
        isSoundOn = !isSoundOn;
    }


    @FXML
    private void goToLevelUI(ActionEvent event) {
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
