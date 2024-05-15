package org.example.platformer_game;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Animation extends AnimationTimer {

    private final ImageView imageView;
    private final int totalFrames;
    private final float fps;
    private final int cols;
    private final int rows;
    private final int frameWidth;
    private final int frameHeight;

    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private final long frameDuration;

    public Animation(ImageView imageView, Image image, int columns, int rows, int totalFrames, int frameWidth, int frameHeight, float framesPerSecond) {
        this.imageView = imageView;
        this.imageView.setImage(image);
        this.imageView.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));

        this.cols = columns;
        this.rows = rows;
        this.totalFrames = totalFrames;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.fps = framesPerSecond;
        this.frameDuration = (long) (1_000_000_000 / fps);
        this.lastFrameTime = System.nanoTime();
    }

    @Override
    public void handle(long now) {
        if (now - lastFrameTime >= frameDuration) {
            lastFrameTime = now;

            currentFrame = (currentFrame + 1) % totalFrames;
            int x = (currentFrame % cols) * frameWidth;
            int y = (currentFrame / cols) * frameHeight;

            imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameHeight));
        }
    }

    public void setFrame(int frame) {
        currentFrame = frame % totalFrames;
        int x = (currentFrame % cols) * frameWidth;
        int y = (currentFrame / cols) * frameHeight;

        imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameHeight));
    }
}
