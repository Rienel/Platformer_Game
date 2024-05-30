package org.example.platformer_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


public class ExitDialog extends Stage {

    private Text textDialog = new Text();
    private Text hintBox = new Text();
    private Text answerBox = new Text();

    private Sounds sounds = new Sounds();

    @FXML
    Button btnmenu;
    public ExitDialog() {
        btnmenu = new Button("Go Back to Menu");

        VBox vBox = new VBox(30, textDialog, btnmenu);
        vBox.setPadding(new Insets(15));
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }

    public void open() {
        textDialog.setText("Well Done\nGo Back to Main Menu");
        show();
    }

    public void openQuit() {
        textDialog.setText("Give Up?\nGo Back to Main Menu");
        show();
    }

}