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
    protected void OnRegister() {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblusers (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogInNow(ActionEvent event) throws IOException {
        // e close ang register ig human sa log in, gwapo si rienel
        Scene scene = ((Node) event.getSource()).getScene();
        Stage registerStage = (Stage) scene.getWindow();
        registerStage.close();

        // Opens login fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
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

                // same thing, gwapo si rienel, i mean e close ang login fxml ig human login
                Scene scene = ((Node) event.getSource()).getScene();
                Stage registerStage = (Stage) scene.getWindow();
                registerStage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Level.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}