package org.example.platformer_game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {
    private ImageView imageView;
    private Image[] frames;
    private int frameIndex;
    private long lastFrameTime;
    private long frameDuration; // in nanoseconds

    public Animation(ImageView imageView, Image[] frames, Duration duration) {
        this.imageView = imageView;
        this.frames = frames;
        this.frameIndex = 0;
        this.frameDuration = (long) (duration.toMillis() * 1_000_000);
    }

    public void play() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastFrameTime >= frameDuration) {
                    frameIndex = (frameIndex + 1) % frames.length;
                    imageView.setImage(frames[frameIndex]);
                    lastFrameTime = now;
                }
            }
        };
        timer.start();
    }

    public void stop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {}
        };
        timer.stop();
    }
}
