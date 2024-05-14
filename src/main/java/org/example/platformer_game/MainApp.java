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

    private final HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private final ArrayList<Node> platforms = new ArrayList<>();
    private final ArrayList<Node> mysteryQ = new ArrayList<>();
    private final ArrayList<Node> hints = new ArrayList<>();

    private final Pane appRoot = new Pane();
    private final Pane gameRoot = new Pane();
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

    private int levelWidth;

    private boolean dialogEvent = false;
    private static boolean running = true;
    private Tiles tile;

    private void initContent() {
        Image bgk = new Image("background-img.png");
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

        for (int i = 0; i < LevelData.LEVEL_ONE.length; i++) {
            String line = LevelData.LEVEL_ONE[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 1);
                        gameRoot.getChildren().addAll(tile.getImageView());
                        break;
                    case '1':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 10);
                        gameRoot.getChildren().addAll(tile.getImageView());
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
                    case '4':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 4);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                            break;
                    case '5':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 5);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '6':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 6);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '7':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 7);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '8':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 8);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '9':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 9);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'l':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 11);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'r':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 12);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                        //u ,i ,j ,k for the corners i visualize lng gi tupad ra nako
                    case 'u':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 13);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'i':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 14);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'j':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 15);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'k':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 16);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
//                    default:
//                        tile = new Tiles(j * 60, i * 60, 60, 60, 1);
//                        gameRoot.getChildren().addAll(tile.getImageView());
//                        break;
                }
            }
        }

        gameRoot.getChildren().addAll(player.getHitBox(), player.getImage());

        player.getHitBox().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot, scoreTxt, hintPointsTxt);
    }

    private void update() {
        ImageView playerImageView = player.getImage();
//        boolean OnGround = false;

        if (!isPressed(KeyCode.W) && !isPressed(KeyCode.A) && !isPressed(KeyCode.D)) {
            startAnimation(playerImageView, "/idle.png", 4, 1, 4, 48, 80, 5);
        }

        if (isPressed(KeyCode.W) && player.getHitBox().getTranslateY() >= 5 && canJump && onGround) {
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getHitBox().getTranslateX() >= 5) {
            startAnimation(playerImageView, "/run.png", 6, 1, 6, 48, 80, 5);
            playerImageView.setScaleX(-1);
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.getHitBox().getTranslateX() + 40 <= levelWidth - 5) {
            startAnimation(playerImageView, "/run.png", 6, 1, 6, 48, 80, 5);
            playerImageView.setScaleX(1);
            movePlayerX(5);
        }
        if (playerVelocity.getY() < 10) {
            playerVelocity = playerVelocity.add(0, 1);
        }

        if (playerVelocity.getY() < 0) {
            setFrame(playerImageView, "/jump.png", 4, 1, 4, 48, 80, 1, 1);
        } else if (playerVelocity.getY() > 0 && !canJump) {
            setFrame(playerImageView, "/jump.png", 4, 1, 4, 48, 80, 1, 2);
        }

        movePlayerY((int) playerVelocity.getY());

        handleInteractions();
    }

    private void handleInteractions() {
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
                    score++;
                    scoreTxt.setText(String.valueOf(score));
                }
            }
        }

        for (Node coin : hints) {
            if (player.getHitBox().getBoundsInParent().intersects(coin.getBoundsInParent())) {
                gameRoot.getChildren().remove(coin);
                gameRoot.getChildren().remove(tile.getImageView());
                coin.getProperties().put("alive", false);
                hintPoints++;
                hintPointsTxt.setText(String.valueOf(hintPoints));
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
            for (Node platform : platforms) {
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
            for (Node platform : platforms) {
                if (player.getHitBox().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (player.getHitBox().getTranslateY() + 40 == platform.getTranslateY()) {
                            player.getHitBox().setTranslateY(player.getHitBox().getTranslateY() - 1);
                            player.getImage().setTranslateY(player.getImage().getTranslateY() - 1);
                            playerVelocity = new Point2D(playerVelocity.getX(), 0);  // stop downward velocity on collision
                            canJump = true;
                            onGround = true;
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

        if (!movingDown && !onGround) {
            canJump = false;
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
