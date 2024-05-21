package org.example.platformer_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Player {

    private ImageView imageView;
    private Rectangle hitBox;
    private int x, y, h, w;

    private Image[] runImages;
    private Image[] idleImages;
    private Image[] jumpImages;
    private Image[] fallImages;
    private int currentFrame = 0;

    private long lastFrameTime = 0;
    // pang pa delay, kay pas2 ra kaayo nano secs na
    private static final long FRAME_DELAY = 100_000_000;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public ImageView getImage() {
        return imageView;
    }

    public Player(int x, int y, int w, int h) {
        runImages = new Image[]{
                new Image("run1.png"),
                new Image("run2.png"),
                new Image("run3.png"),
                new Image("run4.png"),
                new Image("run5.png"),
                new Image("run6.png")
        };

        idleImages = new Image[]{
                new Image("idle1.png"),
                new Image("idle2.png"),
                new Image("idle3.png"),
                new Image("idle4.png")
        };


        // wa pako kabaw unsaon ang jump ug fall animation
        // na human na fall animation lets go
        jumpImages = new Image[]{
                new Image("jump2.png")
        };

        fallImages = new Image[]{
                new Image("fall2.png")
        };

        imageView = new ImageView("Punk.png");
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);

        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;

        hitBox = new Rectangle(w, h);
        hitBox.setFill(Color.TRANSPARENT);
        hitBox.setStrokeWidth(1);
        hitBox.setStroke(Color.RED);

        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        imageView.setTranslateX(x);
        imageView.setTranslateY(y - h / 2.0);
    }

    private boolean shouldUpdateFrame() {
        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            lastFrameTime = currentTime;
            return true;
        }
        return false;
    }

    public void animateRun() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % runImages.length;
            imageView.setImage(runImages[currentFrame]);
        }
    }

    public void animateIdle() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % idleImages.length;
            imageView.setImage(idleImages[currentFrame]);
        }
    }

    public void animateJump() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % jumpImages.length;
            imageView.setImage(jumpImages[currentFrame]);
        }
    }

    public void animateFall() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % fallImages.length;
            imageView.setImage(fallImages[currentFrame]);
        }
    }
}
