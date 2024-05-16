package org.example.platformer_game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class MainApp {
    @FXML
    private Button lvlOneButton;

    private final HashMap<KeyCode, Boolean> keys = new HashMap<>();

    private final Pane appRoot = new Pane();
    public static final Pane gameRoot = new Pane();
    private final Pane uiRoot = new Pane();

    private final GameDialog dialog = new GameDialog();

    public static int hintPoints = 0;
    public static int score = 0;

    public static Label hintPointsTxt = new Label();
    public static Label scoreTxt = new Label();

    private final Player player = new Player(70, 430, 40, 40);
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    private boolean onGround = true;

    private MapGenerator mapGenerator = new MapGenerator();

    private int levelWidth;

    private boolean dialogEvent = false;
    private static boolean running = true;
    public static Tiles tile;

    private void initContent() {

        hintPointsTxt.setText(String.valueOf(hintPoints));
        hintPointsTxt.setPrefHeight(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setPrefWidth(hintPointsTxt.getFont().getSize());
        hintPointsTxt.setTextFill(Color.GREEN);
        hintPointsTxt.setLayoutX(30);
        hintPointsTxt.setLayoutY(30);

        scoreTxt.setText(String.valueOf(score));
        scoreTxt.setPrefHeight(scoreTxt.getFont().getSize());
        scoreTxt.setPrefWidth(scoreTxt.getFont().getSize());
        scoreTxt.setTextFill(Color.GREEN);
        scoreTxt.setLayoutX(30);
        scoreTxt.setLayoutY(60);

        levelWidth = LevelData.LEVEL_ONE[0].length() * 60;
        mapGenerator.run();

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        appRoot.getChildren().addAll(mapGenerator.setBg(),gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    private void update() {
        ImageView playerImageView = player.getImage();

        if (!isPressed(KeyCode.W) && !isPressed(KeyCode.A) && !isPressed(KeyCode.D)) {
            startAnimation(playerImageView, "/idle.png", 1, 1, 1, 48, 80, 5);
        }

        if (isPressed(KeyCode.W) && player.getHitBox().getTranslateY() >= 5 && onGround) {
            jumpPlayer();
        }

        if (isPressed(KeyCode.A) && player.getHitBox().getTranslateX() >= 5) {
            startAnimation(playerImageView, "/run.png", 1, 1, 1, 288, 8, 60);
            playerImageView.setScaleX(-1);
            movePlayerX(-5);
        }

        if (isPressed(KeyCode.D) && player.getHitBox().getTranslateX() + 40 <= levelWidth - 5) {
            startAnimation(playerImageView, "/run.png", 1, 1, 1, 288, 8, 60);
            playerImageView.setScaleX(1);
            movePlayerX(5);
        }

        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        if (playerVelocity.getY() < 0) {
            setFrame(playerImageView, "/jump.png", 1, 1, 2, 48, 80, 1, 1);
        }

        movePlayerY((int) playerVelocity.getY());
        handleInteractions();
        dialog.setCorrect(false);
    }

    private void jumpPlayer() {
        if (onGround) {
            playerVelocity = playerVelocity.add(0, -20);
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

    private void startAnimation(ImageView imageView, String imagePath, int columns, int rows, int totalFrames, int frameWidth, int frameHeight, float fps) {
        Animation animation = new Animation(imageView, new Image(imagePath), columns, rows, totalFrames, frameWidth, frameHeight, fps);
        animation.start();
    }

    private void setFrame(ImageView imageView, String imagePath, int columns, int rows, int totalFrames, int frameWidth, int frameHeight, float fps, int frame) {
        Animation animation = new Animation(imageView, new Image(imagePath), columns, rows, totalFrames, frameWidth, frameHeight, fps);
        animation.setFrame(frame);
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
                        if (player.getHitBox().getTranslateY() + 40 == platform.getTranslateY()) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() - 1);
                            player.getImage().setTranslateY(player.getImage().getTranslateY() - 1);
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);
                            canJump = true;
                            onGround = true;
                            return;
                        }
                    } else {
                        if (player.getHitBox().getTranslateY() == platform.getTranslateY() + 60) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + 1);
                            player.getImage().setTranslateY(player.getImage().getTranslateY() + 1);
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);
                            return;
                        }
                    }
                }
            }
            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + (movingDown ? 1 : -1));
            player.getImage().setTranslateY(player.getImage().getTranslateY() + (movingDown ? 1 : -1));
        }

        if (!movingDown && !onGround) {
            canJump = false;
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @FXML
    private void MainApp(ActionEvent actionEvent) throws IOException {
        initContent();

        Stage stage = (Stage) lvlOneButton.getScene().getWindow();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(),false));
        stage.setScene(scene);
        stage.show();

        Platform.runLater(this::runGameLoop);
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
