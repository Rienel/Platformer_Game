package org.example.platformer_game;

import SQL.InsertScore;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;


public class MainApp {
    @FXML
    private Button lvlOneButton;

    @FXML
    private Button lvlTwoButton;

    @FXML
    private Button lvlThreeButton;

    @FXML
    private Button logoutButton;

    private final HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private final Pane appRoot = new Pane();
    public static final Pane gameRoot = new Pane();
    private final Pane uiRoot = new Pane();

    private final GameDialog dialog = new GameDialog();

    private final ExitDialog exdialog = new ExitDialog();

    public static int hintPoints = 0;
    public static int score = 0;

    public static Label hintPointsTxt = new Label();
    public static Label scoreTxt = new Label();

    private final Player player = new Player(70, 430, 40, 40);
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean onGround = true;

    private MapGenerator mapGenerator = new MapGenerator();

    private int levelWidth;

    private boolean dialogEvent = false;
    private static boolean running = true;
    public static Tiles tile;

    private int counter = 0;

    private boolean exdialogbool = false;
    private Scene scene;

    public boolean isLevel1 = false;
    public boolean isLevel2 = false;
    public boolean isLevel3 = false;

    //Music
    private Sounds sounds = new Sounds();

    public MainApp() {
    }

    public Scene getScene() {
        return scene;
    }

    private void levelOneInitContent() {

        Rectangle board = new Rectangle(200,100,Color.WHITESMOKE);
        board.setLayoutY(25);
        board.setLayoutX(25);

        hintPointsTxt.setText(String.valueOf(hintPoints));

        hintPointsTxt.setPrefHeight(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setPrefWidth(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setTextFill(Color.BLACK);
        hintPointsTxt.setLayoutX(30);
        hintPointsTxt.setLayoutY(30);

        scoreTxt.setText(String.valueOf(score));
        scoreTxt.setPrefHeight(scoreTxt.getFont().getSize());
        scoreTxt.setPrefWidth(scoreTxt.getFont().getSize());
        scoreTxt.setTextFill(Color.BLACK);
        scoreTxt.setLayoutX(30);
        scoreTxt.setLayoutY(60);

        levelWidth = LevelData.LEVEL_ONE[0].length() * 60;
        mapGenerator.setLevel(1);
        mapGenerator.run();

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });
        playMusic(3);

        appRoot.getChildren().addAll(mapGenerator.lvl1setBg(),board,gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    private void levelTwoInitContent() {

        Rectangle board = new Rectangle(200,100,Color.WHITESMOKE);
        board.setLayoutY(25);
        board.setLayoutX(25);

        hintPointsTxt.setText(String.valueOf(hintPoints));

        hintPointsTxt.setPrefHeight(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setPrefWidth(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setTextFill(Color.BLACK);
        hintPointsTxt.setLayoutX(30);
        hintPointsTxt.setLayoutY(30);

        scoreTxt.setText(String.valueOf(score));
        scoreTxt.setPrefHeight(scoreTxt.getFont().getSize());
        scoreTxt.setPrefWidth(scoreTxt.getFont().getSize());
        scoreTxt.setTextFill(Color.BLACK);
        scoreTxt.setLayoutX(30);
        scoreTxt.setLayoutY(60);

        levelWidth = LevelData.LEVEL_TWO[0].length() * 60;
        mapGenerator.setLevel(2);
        mapGenerator.run();

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        playMusic(4);

        appRoot.getChildren().addAll(mapGenerator.lvl2setBg(),board,gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    private void levelThreeInitContent() {

        Rectangle board = new Rectangle(200,100,Color.WHITESMOKE);
        board.setLayoutY(25);
        board.setLayoutX(25);

        hintPointsTxt.setText(String.valueOf(hintPoints));

        hintPointsTxt.setPrefHeight(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setPrefWidth(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setTextFill(Color.BLACK);
        hintPointsTxt.setLayoutX(30);
        hintPointsTxt.setLayoutY(30);

        scoreTxt.setText(String.valueOf(score));
        scoreTxt.setPrefHeight(scoreTxt.getFont().getSize());
        scoreTxt.setPrefWidth(scoreTxt.getFont().getSize());
        scoreTxt.setTextFill(Color.BLACK);
        scoreTxt.setLayoutX(30);
        scoreTxt.setLayoutY(60);

        levelWidth = LevelData.LEVEL_THREE[0].length() * 60;
        mapGenerator.setLevel(3);
        mapGenerator.run();

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        playMusic(5);

        appRoot.getChildren().addAll(mapGenerator.lvl3setBg(),board,gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    //Music
    public void playMusic(int i) {
        sounds.setFile(i);
        sounds.play();
        sounds.loop();
    }

    public void stopMusic() {
        sounds.stop();
    }

    public void playSE(int i) {
        sounds.setFile(i);
        sounds.play();
    }

    /* Another desperate attempt to implement the animation */
    // The attempt works holy jesus
    private void update() {

        if (!isPressed(KeyCode.W) && !isPressed(KeyCode.A) && !isPressed(KeyCode.D) && onGround) {
            player.animateIdle();
        }

        if (isPressed(KeyCode.W) && player.getHitBox().getTranslateY() >= 5 && onGround) {
            jumpPlayer();
            playSE(0);
        } else if (!onGround && playerVelocity.getY() > 0) {
            player.animateFall();
        }

        if(!onGround) {
            player.animateJump();
        }

        // TODO find another implementation for reverse moving
        if (isPressed(KeyCode.A) && player.getHitBox().getTranslateX() >= 5) {
            player.animateRun();
            player.getImage().setScaleX(-1);
            movePlayerX(-5);
        }

        if (isPressed(KeyCode.D) && player.getHitBox().getTranslateX() + 40 <= levelWidth - 5) {
            player.animateRun();
            player.getImage().setScaleX(1);
            movePlayerX(5);
        }

        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        movePlayerY((int) playerVelocity.getY());
        handleInteractions();
        dialog.setCorrect(false);
    }

    private void jumpPlayer() {
        if (onGround) {
            playerVelocity = playerVelocity.add(0, -22);
            onGround = false;
        }
    }

    private void handleInteractions() {
        for (Node QBox : mapGenerator.getMysteryQ()) {
            if (player.getHitBox().getBoundsInParent().intersects(QBox.getBoundsInParent())) {
                if (isPressed(KeyCode.E)) {
                    if ((boolean) QBox.getProperties().get("alive")) {
                        dialogEvent = true;
                        running = false;
                    }
                }
                if (dialog.isCorrect()) {
                    gameRoot.getChildren().remove(QBox);
                    gameRoot.getChildren().remove(tile.getImageView());
                    QBox.getProperties().put("alive", false);
                    scoreTxt.setText(String.valueOf(score));
                }
            }
        }

        if(dialog.isCorrect()){
            if(counter == 10){
                System.out.println("Done");
                exdialog.open();
                running = false;
            }else{
                counter++;
                System.out.println("Counter Check: " + counter);
            }
        }

        for(Iterator<Node> it = mapGenerator.getMysteryQ().iterator(); it.hasNext();){
            Node coin = it.next();
            if(dialog.isCorrect()) {
                if (!(Boolean) coin.getProperties().get("alive")) {
                    it.remove();
                    gameRoot.getChildren().remove(coin);
                }
            }
        }

        for (Node hintBox : mapGenerator.getHints()) {
            if (player.getHitBox().getBoundsInParent().intersects(hintBox.getBoundsInParent())) {
                hintBox.getProperties().put("alive", false);
                hintPoints++;
                hintPointsTxt.setText(String.valueOf(hintPoints));
            }
        }

        for(Iterator<Node> it = mapGenerator.getHints().iterator(); it.hasNext();){
            Node hintBox = it.next();
            if(!(Boolean)hintBox.getProperties().get("alive")){
                it.remove();
                gameRoot.getChildren().remove(hintBox);
            }
        }
    }

    private void movePlayerX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : mapGenerator.getPlatforms()) {
                if (player.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getHitBox().getTranslateX() + 40 == platform.getTranslateX()) {
                            player.getHitBox().setTranslateX(player.getHitBox().getTranslateX() - 1);
                            player.getImage().setTranslateX(player.getImage().getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (player.getHitBox().getTranslateX() == platform.getTranslateX() + 60) {
                            player.getHitBox().setTranslateX(player.getHitBox().getTranslateX() + 1);
                            player.getImage().setTranslateX(player.getImage().getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            player.getHitBox().setTranslateX(player.getHitBox().getTranslateX() + (movingRight ? 1 : -1));
            player.getImage().setTranslateX(player.getImage().getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : mapGenerator.getPlatforms()) {
                if (player.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getHitBox().getTranslateY() + player.getHitBox().getHeight() == platform.getTranslateY()) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() - 1);
                            player.getImage().setTranslateY(player.getHitBox().getTranslateY() - player.getImage().getFitHeight() + player.getHitBox().getHeight());
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);
                            onGround = true;
                            return;
                        }
                    } else {
                        if (player.getHitBox().getTranslateY() == platform.getTranslateY() + platform.getBoundsInParent().getHeight()) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + 1);
                            player.getImage().setTranslateY(player.getHitBox().getTranslateY() - player.getImage().getFitHeight() + player.getHitBox().getHeight());
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);
                            return;
                        }
                    }
                }
            }
            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + (movingDown ? 1 : -1));
            player.getImage().setTranslateY(player.getHitBox().getTranslateY() - player.getImage().getFitHeight() + player.getHitBox().getHeight());
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @FXML
    private void levelOne(ActionEvent actionEvent) throws IOException {
        isLevel1 = true;
        levelOneInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlOneButton.getScene().getWindow();
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        Platform.runLater(this::runGameLoop);
    }

    @FXML
    private void levelTwo(ActionEvent actionEvent) throws IOException {
        isLevel2 = true;
        levelTwoInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlTwoButton.getScene().getWindow();
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        Platform.runLater(this::runGameLoop);
    }

    @FXML
    private void levelThree(ActionEvent actionEvent) throws IOException {
        isLevel3 = true;
        levelThreeInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlThreeButton.getScene().getWindow();
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        Platform.runLater(this::runGameLoop);
    }

    @FXML
    private void GameSystem(ActionEvent actionEvent) throws IOException {
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        FXMLLoader fxmlLoader = new FXMLLoader(GameSystem.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.setScene(scene);

        //para di mu resize ang window
        stage.setResizable(false);
        stage.show();
    }

    public static void setRunning(boolean running) {
        MainApp.running = running;
    }

    private void runGameLoop() {
        // makes the game responsive
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    update();
                }
                if(counter == 10){
                    System.out.println("done\n");
                    running = false;

                    // inserting to tblscore
                    InsertScore ins;
                    if(isLevel1){
                        ins = new InsertScore(GameController.loggedUserId, score, 1);
                        ins.insertScore();
                    }else if(isLevel2){
                        ins = new InsertScore(GameController.loggedUserId, score, 2);
                        ins.insertScore();
                    }else if(isLevel3){
                        ins = new InsertScore(GameController.loggedUserId, score, 3);
                        ins.insertScore();
                    }

                    exdialog.open();
                    score = 0;
                    hintPoints = 0;
                    isLevel1 = false;
                    isLevel2 = false;
                    isLevel3 = false;

                    exdialog.setOnCloseRequest(event -> {
                        System.out.println("Closed");
                    });
                    counter=0;
                    exdialog.btnmenu.setOnAction(event -> {
                        gameRoot.getChildren().removeAll();    // pero di mawala ang picture sa player :(
                        Stage stage1 = (Stage) scene.getWindow();
                        stage1.close();
                        Scene scene = ((Node) event.getSource()).getScene();
                        Stage stage2 = (Stage) scene.getWindow();
                        stage2.close();
                        //exdialog.close();         // I changed it to stage 2

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("level-ui.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Scene newScene = new Scene(root, 1280, 720);

                        newScene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());
                        Stage stage = new Stage();
                        stage.setScene(newScene);
                        stage.show();
                        running = true;

                    });

                }

                if (dialogEvent) {
                    dialogEvent = false;
                    keys.keySet().forEach(key -> keys.put(key, false));

                    dialog.setOnCloseRequest(event -> {
                        if (dialog.isCorrect()) {
                            System.out.println("Correct");
                        } else {
                            System.out.println("Wrong");
                        }
                        running = true;
                    });
                    dialog.open();
                }
            }
        };
        timer.start();
    }
}
