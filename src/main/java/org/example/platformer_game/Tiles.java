package org.example.platformer_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tiles {

    int type;
    private Image image;
    private ImageView imageView;
    private Rectangle hitBox;
    int x, y, h, w;

    public ImageView getImageView() {
        return imageView;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Tiles(int x, int y, int h, int w, int type) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.type = type;

        switch (type) {
            case 1:
                image = new Image("Inside.png");
                break;
            case 2:
                image = new Image("mysteryQ.png");
                break;
            case 3:
                image = new Image("hint.png");
                break;
            case 4:
                image = new Image("top-left.png");
                break;
            case 5:
                image = new Image("top-center.png");
                break;
            case 6:
                image = new Image("top-right.png");
                break;
            case 7:
                image = new Image("bottom-right.png");
                break;
            case 8:
                image = new Image("bottom-center.png");
                break;
            case 9:
                image = new Image("bottom-left.png");
                break;
            case 10:
                image = new Image("background-tile.png");
                break;
            case 11:
                image = new Image("left.png");
                break;
            case 12:
                image = new Image("right.png");
                break;
            case 13:
                image = new Image("corner-tl.png");
                break;
            case 14:
                image = new Image("corner-tr.png");
                break;
            case 15:
                image = new Image("corner-bl.png");
                break;
            case 16:
                image = new Image("corner-br.png");
                break;
        }

        imageView = new ImageView(image);

        hitBox = new Rectangle(w, h);
        hitBox.setTranslateX(x);
        hitBox.setTranslateY(y);

        hitBox.setFill(Color.TRANSPARENT);
        hitBox.getProperties().put("alive", true);

        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
        imageView.getProperties().put("alive", true);

        hitBox.xProperty().bindBidirectional(imageView.xProperty());
        hitBox.yProperty().bindBidirectional(imageView.yProperty());
    }
}
