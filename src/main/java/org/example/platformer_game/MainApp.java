package org.example.platformer_game;

import SQL.InsertScore;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    public static Label hp = new Label();
    public static Label sc = new Label();

    private MapGenerator mapGenerator = new MapGenerator();

    private int levelWidth;
    private Stage gameStage;

    private boolean dialogEvent = false;
    private static boolean running = true;
    public static Tiles tile;

    private int counter = 0;

    private boolean qtdialogbool = false;
    private Scene scene;

    public boolean isLevel1 = false;
    public boolean isLevel2 = false;
    public boolean isLevel3 = false;

    //isBooleans
    private boolean facingLeft = false;
    private boolean enemyFacingLeft = false;
    private boolean onGround = true;
    private boolean enemyOnGround = true;
    private boolean isFinished = false;

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
//    private final Enemy enemy = new Enemy(70, 430, 40, 40);
    private Point2D enemyVelocity = new Point2D(0, 0);




    public MainApp() {
    }

    public Scene getScene() {
        return scene;
    }

    private void initLevelContent(int level) {
        Rectangle board = new Rectangle(115, 60, Color.AQUAMARINE);
        board.setLayoutY(25);
        board.setLayoutX(25);

        board.setArcHeight(15);
        board.setArcWidth(15);

        Font font = new Font("Arial", 16);


        hp.setText("Hint Points: ");
        hp.setPrefHeight(hintPointsTxt.getFont().getSize());
        hp.setPrefWidth(32*hp.getText().toString().length());
        hp.setTextFill(Color.BLACK);
        hp.setFont(font);
        hp.setLayoutX(30);
        hp.setLayoutY(30);

        sc.setText("Score: ");
        sc.setPrefHeight(hintPointsTxt.getFont().getSize());
        sc.setPrefWidth(32*hp.getText().toString().length());
        sc.setTextFill(Color.BLACK);
        sc.setFont(font);
        sc.setLayoutX(30);
        sc.setLayoutY(60);

        hintPointsTxt.setText(String.valueOf(hintPoints));
        hintPointsTxt.setPrefHeight(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setPrefWidth(hintPointsTxt.getFont().getSize() * 32);
        hintPointsTxt.setTextFill(Color.BLACK);
        hintPointsTxt.setFont(font);
        hintPointsTxt.setLayoutX(120);
        hintPointsTxt.setLayoutY(30);

        scoreTxt.setText(String.valueOf(score));
        scoreTxt.setPrefHeight(scoreTxt.getFont().getSize());
        scoreTxt.setPrefWidth(scoreTxt.getFont().getSize() * 32);
        scoreTxt.setTextFill(Color.BLACK);
        scoreTxt.setFont(font);
        scoreTxt.setLayoutX(120);
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

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        switch (level) {
            case 1:
                appRoot.getChildren().addAll(mapGenerator.lvl1setBg(),gameRoot, uiRoot,board,scoreTxt, hintPointsTxt,hp,sc);
                break;
            case 2:
                appRoot.getChildren().addAll(mapGenerator.lvl2setBg(),gameRoot, uiRoot,board,scoreTxt, hintPointsTxt,hp,sc);
                break;
            case 3:
                appRoot.getChildren().addAll(mapGenerator.lvl3setBg(),gameRoot, uiRoot,board,scoreTxt, hintPointsTxt,hp,sc);
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
//            playSE(0);
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

//    private void updateEnemy() {
//        if (enemy.isCollidingWithSomething()) {
//            enemyFacingLeft = !enemyFacingLeft;
//        }
//
//        if (enemyFacingLeft) {
//            enemy.RwalkImages();
//            moveEnemyX(-2);
//        } else {
//            enemy.walkImages();
//            moveEnemyX(2);
//        }
//
//        if (enemyVelocity.getY() < 10) {
//            enemyVelocity = enemyVelocity.add(0, 1);
//        }
//
//        moveEnemyY((int) enemyVelocity.getY());
//
//        // If the enemy is close to the player, initiate attack animations
//        if (enemy.isInRangeOfPlayer(player)) {
//            if (enemyFacingLeft) {
//                enemy.RattackImages();
//            } else {
//                enemy.attackImages();
//            }
//        }
//    }

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
                System.out.println("Done");
                running = false;
            }else{
                counter++;
                System.out.println("Counter Check: " + counter);
            }
        }

        if (isPressed(KeyCode.X)) {
            qtdialogbool = true;
            running = false;
        }else{
            qtdialogbool = false;
            running = true;
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
//                playSE(2);
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
//    private void moveEnemyX(int value) {
//        boolean movingRight = value > 0;
//
//        for (int i = 0; i < Math.abs(value); i++) {
//            for (Node platform : mapGenerator.getPlatforms()) {
//                if (enemy.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
//                    if (movingRight) {
//                        if (enemy.getHitBox().getTranslateX() + enemy.getHitBox().getWidth() == platform.getTranslateX()) {
//                            enemyFacingLeft = true;
//                            return;
//                        }
//                    } else {
//                        if (enemy.getHitBox().getTranslateX() == platform.getTranslateX() + platform.getBoundsInParent().getWidth()) {
//                            enemyFacingLeft = false;
//                            return;
//                        }
//                    }
//                }
//            }
//            enemy.getHitBox().setTranslateX(enemy.getHitBox().getTranslateX() + (movingRight ? 1 : -1));
//            enemy.getImage().setTranslateX(enemy.getImage().getTranslateX() + (movingRight ? 1 : -1));
//        }
//    }
//
//    private void moveEnemyY(int value) {
//        boolean movingDown = value > 0;
//
//        for (int i = 0; i < Math.abs(value); i++) {
//            for (Node platform : mapGenerator.getPlatforms()) {
//                if (enemy.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
//                    if (movingDown) {
//                        if (enemy.getHitBox().getTranslateY() + enemy.getHitBox().getHeight() == platform.getTranslateY()) {
//                            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() - 1);
//                            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + enemy.getHitBox().getHeight());
//                            enemyVelocity = new Point2D(enemyVelocity.getX(), 0);
//                            enemyOnGround = true;
//                            return;
//                        }
//                    } else {
//                        if (enemy.getHitBox().getTranslateY() == platform.getTranslateY() + platform.getBoundsInParent().getHeight()) {
//                            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() + 1);
//                            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + enemy.getHitBox().getHeight());
//                            enemyVelocity = new Point2D(enemyVelocity.getX(), 0);
//                            return;
//                        }
//                    }
//                }
//            }
//            enemy.getHitBox().setTranslateY(enemy.getHitBox().getTranslateY() + (movingDown ? 1 : -1));
//            enemy.getImage().setTranslateY(enemy.getHitBox().getTranslateY() - enemy.getImage().getFitHeight() + player.getHitBox().getHeight());
//        }
//    }


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
        if(isFinished) {
            stopMusic();
        }
        playMusic(3);
        isLevel1 = true;
        levelOneInitContent();
        dialog.setLvl(1);
        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlOneButton.getScene().getWindow();
        stage.setWidth(1280);
        stage.setHeight(720);
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
        if(isFinished) {
            stopMusic();
        }
        playMusic(4);
        isLevel2 = true;
        levelTwoInitContent();
        dialog.setLvl(2);
        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlTwoButton.getScene().getWindow();
        stage.setWidth(1280);
        stage.setHeight(720);
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
        System.out.println("Entering levelThree");
        if(isFinished) {
            stopMusic();
        }
        playMusic(5);
        isLevel3 = true;
        levelThreeInitContent();
        dialog.setLvl(3);
        scene = ((Node) actionEvent.getSource()).getScene();
        Stage Login = (Stage) scene.getWindow();
        Login.close();

        Stage stage = (Stage) lvlThreeButton.getScene().getWindow();
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();
        Platform.runLater(this::runGameLoop);
    }

    // TODO fix the wide screen bug

//    @FXML
//    private void GameSystem(ActionEvent actionEvent) throws IOException {
//        Scene scene = ((Node) actionEvent.getSource()).getScene();
//        Stage Level = (Stage) scene.getWindow();
//        Level.close();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(GameSystem.class.getResource("login.fxml"));
//        Parent root = fxmlLoader.load();
//        scene = new Scene(root, 1280, 720);
//        Stage stage = (Stage) logoutButton.getScene().getWindow();
//        stage.setScene(scene);
//
//        //para di mu resize ang window
//        stage.setResizable(false);
//        stage.show();
//    }

    public void finishedGame() {
        stopMusic();
        playSE(1);
        isFinished = true;
    }

    @FXML
    private void BACK(ActionEvent event) throws IOException {
        stopMusic();
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
//                    updateEnemy();
                }

                if (counter == 10) {
                    isFinished = true;
                    finishedGame();
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

                    exdialog.open();
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
                        Stage stage1 = (Stage) scene.getWindow();
                        stage1.close();
                        exdialog.close();
                        running = true;
                        gameRoot.getChildren().clear();

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

                if (qtdialogbool) {
                    qtdialogbool = false;

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

                    keys.keySet().forEach(key -> keys.put(key, false));

                    exdialog.setOnCloseRequest(closeEvent -> {
                        running = true;
                    });

                    exdialog.btnmenu.setOnAction(actionEvent -> {

                        Stage stage1 = (Stage) scene.getWindow();
                        stage1.close();
                        exdialog.close();
                        running = true;
                        gameRoot.getChildren().clear();

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
                    exdialog.openQuit();
                    score = 0;
                    hintPoints = 0;
                    isLevel1 = false;
                    isLevel2 = false;
                    isLevel3 = false;
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
