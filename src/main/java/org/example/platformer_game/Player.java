package org.example.platformer_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Player {

    private ImageView imageView;
    private Rectangle hitBox;
    private Animation runAnimation;
    private Animation idleAnimation;
    private Animation jumpAnimation;
    private Animation hurtAnimation;

    private String currentState;

    private int x, y, h, w;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public ImageView getImage() {
        return imageView;
    }

    public Player(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;

        imageView = new ImageView();
        hitBox = new Rectangle(w, h);
        hitBox.setFill(Color.TRANSPARENT);
        hitBox.setStrokeWidth(1);
        hitBox.setStroke(Color.BLUE);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);
        imageView.setTranslateX(x);
        imageView.setTranslateY(y);

        // Load frames for different animations
        Image[] runFrames = loadFramesFromSpriteSheet("/run.png", 6);
        Image[] idleFrames = loadFramesFromSpriteSheet("/idle.png", 4);
        Image[] jumpFrames = loadFramesFromSpriteSheet("/jump.png", 4);
        Image[] hurtFrames = loadFramesFromSpriteSheet("/hurt.png", 2);

        runAnimation = new Animation(imageView, runFrames, Duration.millis(100));
        idleAnimation = new Animation(imageView, idleFrames, Duration.millis(200));
        jumpAnimation = new Animation(imageView, jumpFrames, Duration.millis(200));
        hurtAnimation = new Animation(imageView, hurtFrames, Duration.millis(300));

        currentState = "idle";
        idleAnimation.play();
    }

    private Image[] loadFramesFromSpriteSheet(String spriteSheetPath, int frameCount) {
        Image spriteSheet = new Image(getClass().getResourceAsStream(spriteSheetPath));
        int frameWidth = (int) spriteSheet.getWidth() / frameCount;
        int frameHeight = (int) spriteSheet.getHeight();
        Image[] frames = new Image[frameCount];

        PixelReader reader = spriteSheet.getPixelReader();
        for (int i = 0; i < frameCount; i++) {
            frames[i] = new WritableImage(reader, i * frameWidth, 0, frameWidth, frameHeight);
        }

        return frames;
    }

    public void setState(String state) {
        if (state.equals(currentState)) {
            return;
        }

        currentState = state;
        switch (state) {
            case "run":
                idleAnimation.stop();
                jumpAnimation.stop();
                hurtAnimation.stop();
                runAnimation.play();
                break;
            case "idle":
                runAnimation.stop();
                jumpAnimation.stop();
                hurtAnimation.stop();
                idleAnimation.play();
                break;
            case "jump":
                runAnimation.stop();
                idleAnimation.stop();
                hurtAnimation.stop();
                jumpAnimation.play();
                break;
            case "hurt":
                runAnimation.stop();
                idleAnimation.stop();
                jumpAnimation.stop();
                hurtAnimation.play();
                break;
        }
    }
}
