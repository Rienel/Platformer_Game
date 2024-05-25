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
                image = new Image("inside1.png");
                break;
            case 2, 18, 34:
                image = new Image("mysteryQ.png");
                break;
            case 3, 19, 35:
                image = new Image("hint.png");
                break;
            case 4:
                image = new Image("top-left1.png");
                break;
            case 5:
                image = new Image("top-center1.png");
                break;
            case 6:
                image = new Image("top-right1.png");
                break;
            case 7:
                image = new Image("bottom-right1.png");
                break;
            case 8:
                image = new Image("bottom-center1.png");
                break;
            case 9:
                image = new Image("bottom-left1.png");
                break;
            case 10:
                image = new Image("background-tile1.png");
                break;
            case 11:
                image = new Image("left1.png");
                break;
            case 12:
                image = new Image("right1.png");
                break;
            case 13:
                image = new Image("corner-tl1.png");
                break;
            case 14:
                image = new Image("corner-tr1.png");
                break;
            case 15:
                image = new Image("corner-bl1.png");
                break;
            case 16:
                image = new Image("corner-br1.png");
                break;

            //2
            case 17:
                image = new Image("inside2.png");
                break;
            case 20:
                image = new Image("top-left2.png");
                break;
            case 21:
                image = new Image("top-center2.png");
                break;
            case 22:
                image = new Image("top-right2.png");
                break;
            case 23:
                image = new Image("bottom-right2.png");
                break;
            case 24:
                image = new Image("bottom-center2.png");
                break;
            case 25:
                image = new Image("bottom-left2.png");
                break;
            case 26:
                image = new Image("background-tile2.png");
                break;
            case 27:
                image = new Image("left2.png");
                break;
            case 28:
                image = new Image("right2.png");
                break;
            case 29:
                image = new Image("corner-tl2.png");
                break;
            case 30:
                image = new Image("corner-tr2.png");
                break;
            case 31:
                image = new Image("corner-bl2.png");
                break;
            case 32:
                image = new Image("corner-br2.png");
                break;

            //3
            case 33:
                image = new Image("inside3.png");
                break;
            case 36:
                image = new Image("top-left3.png");
                break;
            case 37:
                image = new Image("top-center3.png");
                break;
            case 38:
                image = new Image("top-right3.png");
                break;
            case 39:
                image = new Image("bottom-right3.png");
                break;
            case 40:
                image = new Image("bottom-center3.png");
                break;
            case 41:
                image = new Image("bottom-left3.png");
                break;
            case 42:
                image = new Image("background-tile3.png");
                break;
            case 43:
                image = new Image("left3.png");
                break;
            case 44:
                image = new Image("right3.png");
                break;
            case 45:
                image = new Image("corner-tl3.png");
                break;
            case 46:
                image = new Image("corner-tr3.png");
                break;
            case 47:
                image = new Image("corner-bl3.png");
                break;
            case 48:
                image = new Image("corner-br3.png");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
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
