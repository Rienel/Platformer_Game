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
    private Image[] RrunImages;
    private Image[] idleImages;
    private Image[] RidleImages;
    private Image[] jumpImages;
    private Image[] RjumpImages;
    private Image[] fallImages;
    private Image[] RfallImages;
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
        RrunImages = new Image[] {
                new Image("run1r.png"),
                new Image("run2r.png"),
                new Image("run3r.png"),
                new Image("run4r.png"),
                new Image("run5r.png"),
                new Image("run6r.png")
        };

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

        RidleImages = new Image[]{
                new Image("idle1r.png"),
                new Image("idle2r.png"),
                new Image("idle3r.png"),
                new Image("idle4r.png")
        };

        jumpImages = new Image[]{
                new Image("jump2.png")
        };

        RjumpImages = new Image[]{
                new Image("jump2r.png")
        };

        fallImages = new Image[]{
                new Image("fall2.png")
        };

        RfallImages = new Image[]{
                new Image("fall2r.png")
        };

        imageView = new ImageView("Punk.png");
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

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
        imageView.setTranslateY(y);

    }

    private boolean shouldUpdateFrame() {
        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            lastFrameTime = currentTime;
            return true;
        }
        return false;
    }

    public void animateRunR() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RrunImages.length;
            imageView.setImage(RrunImages[currentFrame]);
        }
    }

    public void animateIdleR() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RidleImages.length;
            imageView.setImage(RidleImages[currentFrame]);
        }
    }

    public void animateJumpR() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RjumpImages.length;
            imageView.setImage(RjumpImages[currentFrame]);
        }
    }

    public void animateFallR() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RfallImages.length;
            imageView.setImage(RfallImages[currentFrame]);
        }
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
