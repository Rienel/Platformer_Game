package org.example.platformer_game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class MainApp extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private ArrayList<Node> mysteryQ = new ArrayList<>();
    private ArrayList<Node> hints = new ArrayList<>();

    // appRoot = main game window
    private Pane appRoot = new Pane();
    // gameRoot = game scene
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private GameDialog dialog = new GameDialog();

    public static int hintPoints = 0;
    public static int score = 0;

    public static Label hintPointsTxt = new Label();
    public static Label scoreTxt = new Label();

    private Player player = new Player(30, 600, 40, 40);
    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;

    private int levelWidth;

    private boolean dialogEvent = false;
    private static boolean running = true;
    private Tiles tile;


    //For the animation ni siya
//    private int columns = 6;
//    private int rows = 1;
//    private int totalNumberOfFrames = 6;
//    private int frameWidth = 48;
//    private int frameHeight = 80;
//    private float FPS = 10;

    // frameWidth = original width / frames (frameHeight is 80 lng ghapon)

    private void initContent() {
        Image bgk = new Image("Picasso-Room.jpg");
        ImageView bg = new ImageView(bgk);

        bg.setFitHeight(LevelData.LEVEL_ONE.length * 60);
        bg.setFitWidth(1280);



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

        // adding tiles platform
        for (int i = 0; i < LevelData.LEVEL_ONE.length; i++) {
            String line = LevelData.LEVEL_ONE[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 1);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '2':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 2);
                        mysteryQ.add(tile.getHitBox());
                        mysteryQ.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '3':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 3);
                        hints.add(tile.getHitBox());
                        hints.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                }
            }
        }

        // adding player entity
        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        // for moving map
        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        // adding all Node to main game window
        appRoot.getChildren().addAll(bg, gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    private void update() {
        ImageView playerImageView = player.getImage();

        if(!isPressed(KeyCode.W) && !isPressed(KeyCode.A) && !isPressed(KeyCode.D)) {
            int columns = 4;
            int rows = 1;
            int totalNumberOfFrames = 4;
            int frameWidth = 48;
            int frameHeight= 80;
            int FPS = 5;
            Animation anim = new Animation(playerImageView, new Image("/idle.png"), columns, rows, totalNumberOfFrames, frameWidth, frameHeight, FPS);
            anim.start();
        }

        if (isPressed(KeyCode.W) && player.getHitBox().getTranslateY() >= 5) {
            int columns = 4;
            int rows = 1;
            int totalNumberOfFrames = 4;
            int frameWidth = 48;
            int frameHeight= 80;
            int FPS = 5;
            Animation anim = new Animation(playerImageView, new Image("/jump.png"), columns, rows, totalNumberOfFrames, frameWidth, frameHeight, FPS);
            anim.start();
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getHitBox().getTranslateX() >= 5) {
            int columns = 6;
            int rows = 1;
            int totalNumberOfFrames = 6;
            int frameWidth = 48;
            int frameHeight= 80;
            int FPS = 5;
            Animation anim = new Animation(playerImageView, new Image("/run.png"), columns, rows, totalNumberOfFrames, frameWidth, frameHeight, FPS);
            anim.start();
            playerImageView.setScaleX(-1);
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.getHitBox().getTranslateX() + 40 <= levelWidth - 5) {
            int columns = 6;
            int rows = 1;
            int totalNumberOfFrames = 6;
            int frameWidth = 48;
            int frameHeight= 80;
            int FPS = 10;
            Animation anim = new Animation(playerImageView, new Image("/run.png"), columns, rows, totalNumberOfFrames, frameWidth, frameHeight, FPS);
            anim.start();
            playerImageView.setScaleX(1);
            movePlayerX(5);
        }
        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        movePlayerY((int) playerVelocity.getY());

        for (Node coin : mysteryQ) {
            if (player.getHitBox().getBoundsInParent().intersects(coin.getBoundsInParent())) {
                if (isPressed(KeyCode.E)) {
                    if ((boolean) coin.getProperties().get("alive")) {
                        dialogEvent = true;
                        running = false;
                    }
                }
                if (dialog.isCorrect()) {
                    gameRoot.getChildren().remove(coin);
                    gameRoot.getChildren().remove(tile.getImageView());
                    coin.getProperties().put("alive", false);
                }
            }
        }
        dialog.setCorrect(false);

        for (Iterator<Node> it = mysteryQ.iterator(); it.hasNext(); ) {
            Node coin = it.next();
            if (dialog.isCorrect()) {
                if (!(Boolean) coin.getProperties().get("alive")) {
                    it.remove();
                    gameRoot.getChildren().remove(coin);
                }
            }
        }

        for (Node hint : hints) {
            if (player.getHitBox().getBoundsInParent().intersects(hint.getBoundsInParent())) {
                hint.getProperties().put("alive", false);
                hintPoints++;
                hintPointsTxt.setText(String.valueOf(hintPoints));
            }
        }

        for (Iterator<Node> it = hints.iterator(); it.hasNext(); ) {
            Node coin = it.next();
            if (!(Boolean) coin.getProperties().get("alive")) {
                it.remove();
                gameRoot.getChildren().remove(coin);
            }
        }
    }

    private void movePlayerX(int value) {
        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (player.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getHitBox().getTranslateX() + 40 == platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (player.getHitBox().getTranslateX() == platform.getTranslateX() + 60) {
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
            for (Node platform : platforms) {
                if (player.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getHitBox().getTranslateY() + 40 == platform.getTranslateY()) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() - 1);
                            player.getImage().setTranslateY(player.getImage().getTranslateY() - 1);
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);  // stop downward velocity on collision
                            canJump = true;
                            return;
                        }
                    } else {
                        if (player.getHitBox().getTranslateY() == platform.getTranslateY() + 60) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + 1);
                            player.getImage().setTranslateY(player.getImage().getTranslateY() + 1);
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);  // stop upward velocity on collision
                            return;
                        }
                    }
                }
            }
            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() + (movingDown ? 1 : -1));
            player.getImage().setTranslateY(player.getImage().getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -20);
            canJump = false;
        }
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initContent();

        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        stage.setScene(scene);
        stage.show();

        Platform.runLater(this::runGameLoop);
    }

    public static void main(String[] args) {
        launch();
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
