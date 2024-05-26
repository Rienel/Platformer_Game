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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


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

    private MapGenerator mapGenerator = new MapGenerator();

    private int levelWidth;
    private Stage gameStage;

    private boolean dialogEvent = false;
    private static boolean running = true;
    public static Tiles tile;

    private int counter = 0;

    private boolean exdialogbool = false;
    private Scene scene;

    public boolean isLevel1 = false;
    public boolean isLevel2 = false;
    public boolean isLevel3 = false;

    //isBooleans
    private boolean facingLeft = false;
    private boolean enemyFacingLeft = false;
    private boolean onGround = true;
    private boolean enemyOnGround = true;

    //Music
    private Sounds sounds = new Sounds();

    //Camera
    private double cameraX = 0;
    private double cameraY = 0;
    private final double smoothing = 0.1;

    //Player
    private final Player player = new Player(70, 430, 40, 40);
    private Point2D playerVelocity = new Point2D(0, 0);

    //Enemy
    private final Enemy enemy = new Enemy(70, 430, 40, 40);
    private Point2D enemyVelocity = new Point2D(0, 0);

    public MainApp() {
    }

    public Scene getScene() {
        return scene;
    }

    private void initLevelContent(int level) {
        Rectangle board = new Rectangle(200, 100, Color.WHITESMOKE);
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

        String[] levelData;
        switch (level) {
            case 1:
                levelData = LevelData.LEVEL_ONE;
                mapGenerator.setLevel(1);
                break;
            case 2:
                levelData = LevelData.LEVEL_TWO;
                mapGenerator.setLevel(2);
                break;
            case 3:
                levelData = LevelData.LEVEL_THREE;
                mapGenerator.setLevel(3);
                break;
            default:
                throw new IllegalArgumentException("Invalid level: " + level);
        }

        levelWidth = levelData[0].length() * 60;
        mapGenerator.run();

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage(), enemy.getHitBox(), enemy.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        switch (level) {
            case 1:
                appRoot.getChildren().addAll(mapGenerator.lvl1setBg(), board, gameRoot, uiRoot, scoreTxt, hintPointsTxt);
                break;
            case 2:
                appRoot.getChildren().addAll(mapGenerator.lvl2setBg(), board, gameRoot, uiRoot, scoreTxt, hintPointsTxt);
                break;
            case 3:
                appRoot.getChildren().addAll(mapGenerator.lvl3setBg(), board, gameRoot, uiRoot, scoreTxt, hintPointsTxt);
                break;
        }
    }

    private void levelOneInitContent() {
        initLevelContent(1);
    }

    private void levelTwoInitContent() {
        initLevelContent(2);
    }

    private void levelThreeInitContent() {
        initLevelContent(3);
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

    private void update() {

        if(isPressed(KeyCode.A) && isPressed(KeyCode.D) && onGround) {
            if(facingLeft) {
                player.animateIdleR();
            } else {
                player.animateIdle();
            }
        }

        if (!isPressed(KeyCode.W) && !isPressed(KeyCode.A) && !isPressed(KeyCode.D) && onGround) {
            if(facingLeft) {
                player.animateIdleR();
            } else {
                player.animateIdle();
            }
        }

        if (isPressed(KeyCode.W) && player.getHitBox().getTranslateY() >= 5 && onGround) {
            jumpPlayer();
            playSE(0);
        } else if (!onGround && playerVelocity.getY() > 0) {
            if(facingLeft) {
                player.animateFallR();
            } else {
                player.animateFall();
            }
        }

        if(!onGround) {
            if(facingLeft) {
                player.animateJumpR();
            } else {
                player.animateJump();
            }
        }

        if (isPressed(KeyCode.A) && player.getHitBox().getTranslateX() >= 5) {
            player.animateRunR();
            facingLeft = true;
            movePlayerX(-5);
        }

        if (isPressed(KeyCode.D) && player.getHitBox().getTranslateX() + 40 <= levelWidth - 5) {
            player.animateRun();
            facingLeft = false;
            movePlayerX(5);
        }

        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        movePlayerY((int) playerVelocity.getY());
        handleInteractions();
        dialog.setCorrect(false);

        centerCameraOnPlayer();
    }

    private void updateEnemy() {
        if (enemy.isCollidingWithSomething()) {
            enemyFacingLeft = !enemyFacingLeft;
        }

        if (enemyFacingLeft) {
            enemy.RwalkImages();
            moveEnemyX(-2);
        } else {
            enemy.walkImages();
            moveEnemyX(2);
        }

        if (enemyVelocity.getY() < 10) {
            enemyVelocity = enemyVelocity.add(0, 1);
        }

        moveEnemyY((int) enemyVelocity.getY());

        // If the enemy is close to the player, initiate attack animations
        if (enemy.isInRangeOfPlayer(player)) {
            if (enemyFacingLeft) {
                enemy.RattackImages();
            } else {
                enemy.attackImages();
            }
        }
    }

    //Camera
    private void centerCameraOnPlayer() {
        double targetX = player.getHitBox().getTranslateX() - 640;
        double targetY = player.getHitBox().getTranslateY() - 360;

        cameraX += (targetX - cameraX) * smoothing;
        cameraY += (targetY - cameraY) * smoothing;

        gameRoot.setLayoutX(-cameraX);
        gameRoot.setLayoutY(-cameraY);
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
                playMusic(1);
                System.out.println("Done");
                exdialog.open();
                running = false;
            }else{
                counter++;
                playSE(2);
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

    //ENEMY
    private void moveEnemyX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : mapGenerator.getPlatforms()) {
                if (enemy.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (enemy.getHitBox().getTranslateX() + enemy.getHitBox().getWidth() == platform.getTranslateX()) {
                            enemyFacingLeft = true;
                            return;
                        }
                    } else {
                        if (enemy.getHitBox().getTranslateX() == platform.getTranslateX() + platform.getBoundsInParent().getWidth()) {
                            enemyFacingLeft = false;
                            return;
                        }
                    }
                }
            }
            enemy.getHitBox().setTranslateX(enemy.getHitBox().getTranslateX() + (movingRight ? 1 : -1));
            enemy.getImage().setTranslateX(enemy.getImage().getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void moveEnemyY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : mapGenerator.getPlatforms()) {
                if (enemy.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (enemy.getHitBox().getTranslateY() + enemy.getHitBox().getHeight() == platform.getTranslateY()) {
                            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() - 1);
                            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + enemy.getHitBox().getHeight());
                            enemyVelocity = new Point2D(enemyVelocity.getX(), 0);
                            enemyOnGround = true;
                            return;
                        }
                    } else {
                        if (enemy.getHitBox().getTranslateY() == platform.getTranslateY() + platform.getBoundsInParent().getHeight()) {
                            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() + 1);
                            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + enemy.getHitBox().getHeight());
                            enemyVelocity = new Point2D(enemyVelocity.getX(), 0);
                            return;
                        }
                    }
                }
            }
            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() + (movingDown ? 1 : -1));
            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + player.getHitBox().getHeight());
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
    private void BACK(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
    private void levelOne(ActionEvent actionEvent) throws IOException {
        playMusic(3);
        isLevel1 = true;
        levelOneInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlOneButton.getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        Platform.runLater(this::runGameLoop);
    }

    @FXML
    private void levelTwo(ActionEvent actionEvent) throws IOException {
        playMusic(4);
        isLevel2 = true;
        levelTwoInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlTwoButton.getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        Platform.runLater(this::runGameLoop);
    }

    @FXML
    private void levelThree(ActionEvent actionEvent) throws IOException {
        playMusic(5);
        isLevel3 = true;
        levelThreeInitContent();

        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlThreeButton.getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        Platform.runLater(this::runGameLoop);
    }

    // TODO fix the wide screen bug

    @FXML
    private void GameSystem(ActionEvent actionEvent) throws IOException {
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        Stage Level = (Stage) scene.getWindow();
        Level.close();

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
                    updateEnemy();
                }
                if (counter == 10) {
                    stopMusic();
                    System.out.println("done\n");
                    running = false;

                    // inserting to tblscore
                    InsertScore ins;
                    if (isLevel1) {
                        ins = new InsertScore(GameController.loggedUserId, score, 1);
                        ins.insertScore();
                    } else if (isLevel2) {
                        ins = new InsertScore(GameController.loggedUserId, score, 2);
                        ins.insertScore();
                    } else if (isLevel3) {
                        ins = new InsertScore(GameController.loggedUserId, score, 3);
                        ins.insertScore();
                    }

                    stopMusic();
                    exdialog.open();
                    stopMusic();
                    score = 0;
                    hintPoints = 0;
                    isLevel1 = false;
                    isLevel2 = false;
                    isLevel3 = false;

                    exdialog.setOnCloseRequest(closeEvent -> {
                        System.out.println("Closed");
                    });
                    counter = 0;
                    exdialog.btnmenu.setOnAction(actionEvent -> {
                        stopMusic();
                        gameRoot.getChildren().clear();
                        Scene scene1 = ((Node) actionEvent.getSource()).getScene();
                        Stage stage1 = (Stage) scene1.getWindow();
                        stage1.close(); // Close the current stage

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
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
                    });
                }

                if (dialogEvent) {
                    dialogEvent = false;
                    keys.keySet().forEach(key -> keys.put(key, false));

                    dialog.setOnCloseRequest(closeEvent -> {
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
