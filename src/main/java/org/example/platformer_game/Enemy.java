package org.example.platformer_game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Entity {

    private final ImageView imageView;
    private final Rectangle hitBox;

    private final Image[] walkImages;
    private final Image[] RwalkImages;
    private final Image[] idleImages;
    private final Image[] RidleImages;
    private final Image[] attackImages;
    private final Image[] RattackImages;

    private int currentFrame = 0;
    private long lastFrameTime = 0;
    // pang pa delay, kay pas2 ra kaayo nano secs na
    private static final long FRAME_DELAY = 100_000_000;

    public Enemy(int x, int y, int w, int h) {
        super(x, y, w, h);

        attackImages = new Image[]{
                new Image("attck1.png"),
                new Image("attck2.png"),
                new Image("attck3.png"),
                new Image("attck4.png"),
                new Image("attck5.png"),
                new Image("attck6.png"),
                new Image("attck7.png"),
                new Image("attck8.png"),
                new Image("attck9.png"),
                new Image("attck10.png"),
                new Image("attck11.png"),
                new Image("attck12.png"),
                new Image("attck13.png")
        };

        RattackImages = new Image[]{
                new Image("Rattck1.png"),
                new Image("Rattck2.png"),
                new Image("Rattck3.png"),
                new Image("Rattck4.png"),
                new Image("Rattck5.png"),
                new Image("Rattck6.png"),
                new Image("Rattck7.png"),
                new Image("Rattck8.png"),
                new Image("Rattck9.png"),
                new Image("Rattck10.png"),
                new Image("Rattck11.png"),
                new Image("Rattck12.png"),
                new Image("Rattck13.png")
        };

        idleImages = new Image[]{
                new Image("Eidle1.png"),
                new Image("Eidle2.png"),
                new Image("Eidle3.png"),
                new Image("Eidle4.png")
        };

        RidleImages = new Image[]{
                new Image("REidle1.png"),
                new Image("REidle2.png"),
                new Image("REidle3.png"),
                new Image("REidle4.png")
        };

        walkImages = new Image[]{
                new Image("walk1.png"),
                new Image("walk2.png"),
                new Image("walk3.png"),
                new Image("walk4.png"),
                new Image("walk5.png"),
                new Image("walk6.png"),
                new Image("walk7.png"),
                new Image("walk8.png"),
                new Image("walk9.png"),
                new Image("walk10.png"),
                new Image("walk11.png"),
                new Image("walk12.png")
        };

        RwalkImages = new Image[]{
                new Image("Rwalk1.png"),
                new Image("Rwalk2.png"),
                new Image("Rwalk3.png"),
                new Image("Rwalk4.png"),
                new Image("Rwalk5.png"),
                new Image("Rwalk6.png"),
                new Image("Rwalk7.png"),
                new Image("Rwalk8.png"),
                new Image("Rwalk9.png"),
                new Image("Rwalk10.png"),
                new Image("Rwalk11.png"),
                new Image("Rwalk12.png")
        };

        imageView = new ImageView("Eidle1.png");
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        hitBox = new Rectangle(w, h);
        hitBox.setFill(Color.TRANSPARENT);
        hitBox.setStrokeWidth(1);
        hitBox.setStroke(Color.RED);

        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        imageView.setTranslateX(x - 50);
        imageView.setTranslateY(y - 75);
    }

    private boolean shouldUpdateFrame() {
        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime >= FRAME_DELAY) {
            lastFrameTime = currentTime;
            return true;
        }
        return false;
    }

    public void attackImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % attackImages.length;
            imageView.setImage(attackImages[currentFrame]);
        }
    }

    public void RattackImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RattackImages.length;
            imageView.setImage(RattackImages[currentFrame]);
        }
    }

    public void idleImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % idleImages.length;
            imageView.setImage(idleImages[currentFrame]);
        }
    }

    public void RidleImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RidleImages.length;
            imageView.setImage(RidleImages[currentFrame]);
        }
    }

    public void walkImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % walkImages.length;
            imageView.setImage(walkImages[currentFrame]);
        }
    }

    public void RwalkImages() {
        if (shouldUpdateFrame()) {
            currentFrame = (currentFrame + 1) % RwalkImages.length;
            imageView.setImage(RwalkImages[currentFrame]);
        }
    }

    public boolean isCollidingWithSomething() {
        for (Node platform : MainApp.gameRoot.getChildren()) {
            if (platform != hitBox && hitBox.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public boolean isInRangeOfPlayer(Player player) {
        double distance = hitBox.getTranslateX() - player.getHitBox().getTranslateX();
        return Math.abs(distance) < 200;
    }

    public void move(int dx, int dy) {
        imageView.setTranslateX(imageView.getTranslateX() + dx);
        imageView.setTranslateY(imageView.getTranslateY() + dy);
        hitBox.setTranslateX(hitBox.getTranslateX() + dx);
        hitBox.setTranslateY(hitBox.getTranslateY() + dy);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public ImageView getImage() {
        return imageView;
    }
}