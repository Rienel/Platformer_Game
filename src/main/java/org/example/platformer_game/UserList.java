package org.example.platformer_game;

import SQL.MySqlConnection;
import SQL.RetrieveData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserList {

    @FXML
    private TableView<User> userTable1;
    @FXML
    private TableView<User> userTable2;
    @FXML
    private TableView<User> userTable3;
    @FXML
    private TableColumn<User, String> uname1Column;
    @FXML
    private TableColumn<User, String> uname2Column;
    @FXML
    private TableColumn<User, String> uname3Column;
    @FXML
    private TableColumn<User, Integer> lvl1Column;
    @FXML
    private TableColumn<User, Integer> lvl2Column;
    @FXML
    private TableColumn<User, Integer> lvl3Column;
    @FXML
    private TextField tfUser;
    @FXML
    private Button ldrDeleteBtn;
    @FXML
    private Button ldrRenameBtn;

    @FXML
    private Button goBackbtn;

    private RetrieveData dataAccess = new RetrieveData();

    public void initialize() {
        uname1Column.setCellValueFactory(cellData -> cellData.getValue().unameProperty());
        lvl1Column.setCellValueFactory(cellData -> cellData.getValue().score().asObject());
        uname2Column.setCellValueFactory(cellData -> cellData.getValue().unameProperty());
        lvl2Column.setCellValueFactory(cellData -> cellData.getValue().score().asObject());
        uname3Column.setCellValueFactory(cellData -> cellData.getValue().unameProperty());
        lvl3Column.setCellValueFactory(cellData -> cellData.getValue().score().asObject());

        populateTable();
    }

    private void populateTable() {
        List<User> userList1 = dataAccess.getAllUsers1();
        List<User> userList2 = dataAccess.getAllUsers2();
        List<User> userList3 = dataAccess.getAllUsers3();
        ObservableList<User> observableList1 = FXCollections.observableArrayList(userList1);
        ObservableList<User> observableList2 = FXCollections.observableArrayList(userList2);
        ObservableList<User> observableList3 = FXCollections.observableArrayList(userList3);
        userTable1.setItems(observableList1);
        userTable2.setItems(observableList2);
        userTable3.setItems(observableList3);
    }

    @FXML
    private void goBackMenu(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        Stage dash = (Stage) scene.getWindow();
        dash.close();

        Stage stage = (Stage) goBackbtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.show();
    }

    @FXML
    private void goDelete(ActionEvent event) throws IOException {
        String txtuser = tfUser.getText();

        if (txtuser == null || txtuser.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText(null);
            alert.setContentText("Please input a username first!");

            alert.showAndWait();
            return;
        }

        int userid = GameController.loggedUserId;

        try(Connection c = MySqlConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE tblusers SET status = 1 WHERE id = ? AND username = ? AND status = 0")) {
            statement.setInt(1, userid);
            statement.setString(2, txtuser);

            int rowsDeleted = statement.executeUpdate();

            if(rowsDeleted > 0){
                showAlert("Username Deleted", txtuser + " has been deleted successfully.");
            }else{
                showAlert("FAILED", "YOU CAN ONLY DELETE YOUR OWN USERNAME!");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        Stage stage = (Stage) ldrDeleteBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goRename(ActionEvent event) throws IOException {
        String txtuser = tfUser.getText();

        if (txtuser == null || txtuser.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText(null);
            alert.setContentText("Please input a username first!");

            alert.showAndWait();
            return;
        }

        int userid = GameController.loggedUserId;

        try(Connection c = MySqlConnection.getConnection();
            PreparedStatement statement = c.prepareStatement(
                    "UPDATE tblusers SET username = ? WHERE id = ? AND status = 0")) {
            statement.setString(1, txtuser);
            statement.setInt(2, userid);

            int rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0){
                showAlert("Username Updated", "Your Username has been Renamed Successfully.");
            }else{
                showAlert("FAILED", "Renamed Unsuccessful");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        Stage stage = (Stage) ldrDeleteBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Leaderboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
