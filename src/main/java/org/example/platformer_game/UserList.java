package org.example.platformer_game;

import SQL.RetrieveData;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UserList {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> unameColumn;
    @FXML
    private TableColumn<User, Integer> lvl1Column;
    @FXML
    private TableColumn<User, Integer> lvl2Column;
    @FXML
    private TableColumn<User, Integer> lvl3Column;

    @FXML
    private Button goBackbtn;

    private RetrieveData dataAccess = new RetrieveData();

    public void initialize() {
        unameColumn.setCellValueFactory(cellData -> cellData.getValue().unameProperty());
        lvl1Column.setCellValueFactory(cellData -> cellData.getValue().lvl1Property().asObject());
        lvl2Column.setCellValueFactory(cellData -> cellData.getValue().lvl2Property().asObject());
        lvl3Column.setCellValueFactory(cellData -> cellData.getValue().lvl3Property().asObject());

        populateTable();
    }

    private void populateTable() {
        List<User> userList = dataAccess.getAllUsers();
        ObservableList<User> observableList = FXCollections.observableArrayList(userList);
        userTable.setItems(observableList);
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
}
