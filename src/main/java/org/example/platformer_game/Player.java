package org.example.platformer_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {

    private Image image;
    private ImageView imageView;
    private Rectangle hitBox;
    int x, y, h, w;

    public Rectangle getHitBox() {
        return hitBox;
    }

    public ImageView getImage() {
        return imageView;
    }

    public Player(int x, int y, int w, int h) {
        image = new Image("PunkRun.png");

        imageView = new ImageView(image);

        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;

        hitBox = new Rectangle(w, h);
        hitBox.setFill(Color.TRANSPARENT);

        hitBox.setStrokeWidth(1);
        hitBox.setStroke(Color.BLUE);

        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        // pra mo saka gamay
        imageView.setTranslateX(x);
        imageView.setTranslateY(y - (double) h / 5);

    }
}
