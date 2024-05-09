package org.example.platformer_game;

import SQL.MySqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameController {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    int LogedUser = -1;

    @FXML
    protected void OnRegister(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblusers (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();

            Scene scene = ((Node) event.getSource()).getScene();
            Stage registerStage = (Stage) scene.getWindow();
            registerStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1280, 720));
//            stage.setFullScreen(true);
//            stage.setFullScreenExitHint("");
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onRegisterAcc(ActionEvent event) throws IOException {
        // E close ang login after pressing the register
        Scene scene = ((Node) event.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        // Opens login fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 1280, 720));
//        stage.setFullScreen(true);
//        stage.setFullScreenExitHint("");
        stage.show();
    }

    @FXML
    protected void onLogIn(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                LogedUser = userId;

                // e close ang login
                Scene scene = ((Node) event.getSource()).getScene();
                Stage Login = (Stage) scene.getWindow();
                Login.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1280, 720));
//                stage.setFullScreen(true);
//                stage.setFullScreenExitHint("");
                stage.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}