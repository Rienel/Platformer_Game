package org.example.platformer_game;

import SQL.MySqlConnection;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TextField rtfUsername;
    @FXML
    private TextField rtfPassword;
    @FXML
    private Button levelUIButton;

    int LogedUser = -1;
    public static int loggedUserId;


    //Leaderboard
    @FXML
    private TableView<UserScore> leaderboardTable;
    @FXML
    private TableColumn<UserScore, String> usernameColumn;
    @FXML
    private TableColumn<UserScore, Integer> scoreColumn;

    private ObservableList<UserScore> data;

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        loadLeaderboardData();
    }

    private void loadLeaderboardData() {
        String query = "SELECT username, score FROM tblusers ORDER BY score DESC";

        try (Connection c = MySqlConnection.getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                data.add(new UserScore(username, score));
            }

            leaderboardTable.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class UserScore {
        private final SimpleStringProperty username;
        private final SimpleIntegerProperty score;

        public UserScore(String username, int score) {
            this.username = new SimpleStringProperty(username);
            this.score = new SimpleIntegerProperty(score);
        }

        public String getUsername() {
            return username.get();
        }

        public int getScore() {
            return score.get();
        }
    }

    @FXML
    protected void OnRegister(ActionEvent event) {
        String username = rtfUsername.getText();
        String password = rtfPassword.getText();

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
            stage.setResizable(false);
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
