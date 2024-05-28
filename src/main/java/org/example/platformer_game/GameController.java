package org.example.platformer_game;

import SQL.MySqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private PasswordField tfPassword;
    @FXML
    private TextField rtfUsername;
    @FXML
    private PasswordField rtfPassword;

    int LogedUser = -1;
    public static int loggedUserId;


    @FXML
    protected void OnRegister(ActionEvent event) {
        String username = rtfUsername.getText();
        String password = rtfPassword.getText();

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "INSERT INTO tblusers (username, password, status) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onLogIn(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        loggedUserId = getLogUserId(username);
        System.out.println("LOGGED USER ID " + loggedUserId);

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement statement = c.prepareStatement(
                     "SELECT * FROM tblusers WHERE username = ? AND password = ? AND status = 0")) {
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

                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                Parent root = loader.load();
                Scene newScene = new Scene(root, 1280, 720);
                Stage stage = new Stage();
                stage.setScene(newScene);
                stage.setResizable(false);
                stage.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLogUserId(String username){
        int id = 0;
        String query = "SELECT id FROM tblusers WHERE username = ?";
        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


}
