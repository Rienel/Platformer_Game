package org.example.platformer_game;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static org.example.platformer_game.MainApp.gameRoot;
import static org.example.platformer_game.MainApp.tile;

public class MapGenerator implements Runnable{
    private final ArrayList<Node> platforms = new ArrayList<>();
    private final ArrayList<Node> mysteryQ = new ArrayList<>();
    private final ArrayList<Node> hints = new ArrayList<>();

    private int level = 0;

    public MapGenerator(int level) {
        this.level = level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void run() {
        switch(level){
            case 1:
                getMapLvl1();
                break;
            case 2:
                getMapLvl2();
                break;
            case 3:
                getMapLvl3();
                break;
        }
    }

    public ImageView lvl1setBg(){
        Image bgk = new Image("LVLONE.gif");
        ImageView bg = new ImageView(bgk);

        bg.setFitHeight(LevelData.LEVEL_ONE.length * 60);
        bg.setFitWidth(1280);
        return bg;
    }

    public ImageView lvl2setBg(){
        Image bgk = new Image("LVLTWO.gif");
        ImageView bg = new ImageView(bgk);

        bg.setFitHeight(LevelData.LEVEL_TWO.length * 60);
        bg.setFitWidth(1280);
        return bg;
    }

    public ImageView lvl3setBg(){
        Image bgk = new Image("LVLTHREE.gif");
        ImageView bg = new ImageView(bgk);

        bg.setFitHeight(LevelData.LEVEL_THREE.length * 60);
        bg.setFitWidth(1280);
        return bg;
    }

    public ArrayList<Node> getPlatforms() {
        return platforms;
    }

    public ArrayList<Node> getMysteryQ() {
        return mysteryQ;
    }

    public ArrayList<Node> getHints() {
        return hints;
    }

    public MapGenerator() {
    }

    public void getMapLvl1(){
        for (int i = 0; i < LevelData.LEVEL_ONE.length; i++) {
            String line = LevelData.LEVEL_ONE[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 1);
                        gameRoot.getChildren().addAll( tile.getImageView());
                        break;
                    case '1':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 10);
                        gameRoot.getChildren().addAll( tile.getImageView());
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
                }
            }
        }
    }

    public void getMapLvl2(){
        for (int i = 0; i < LevelData.LEVEL_TWO.length; i++) {
            String line = LevelData.LEVEL_TWO[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 17);
                        gameRoot.getChildren().addAll( tile.getImageView());
                        break;
                    case '1':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 26);
                        gameRoot.getChildren().addAll( tile.getImageView());
                        break;
                    case '2':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 18);
                        mysteryQ.add(tile.getHitBox());
                        mysteryQ.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '3':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 19);
                        hints.add(tile.getHitBox());
                        hints.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '4':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 20);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '5':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 21);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '6':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 22);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '7':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 23);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '8':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 24);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '9':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 25);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'l':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 27);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'r':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 28);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    //u ,i ,j ,k for the corners i visualize lng gi tupad ra nako
                    case 'u':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 29);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'i':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 30);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'j':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 31);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'k':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 32);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                }
            }
        }
    }

    public void getMapLvl3(){
        for (int i = 0; i < LevelData.LEVEL_THREE.length; i++) {
            String line = LevelData.LEVEL_THREE[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 33);
                        gameRoot.getChildren().addAll( tile.getImageView());
                        break;
                    case '1':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 42);
                        gameRoot.getChildren().addAll( tile.getImageView());
                        break;
                    case '2':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 34);
                        mysteryQ.add(tile.getHitBox());
                        mysteryQ.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '3':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 35);
                        hints.add(tile.getHitBox());
                        hints.add(tile.getImageView());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '4':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 36);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '5':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 37);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '6':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 38);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '7':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 39);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '8':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 40);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case '9':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 41);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'l':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 43);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'r':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 44);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    //u ,i ,j ,k for the corners i visualize lng gi tupad ra nako
                    case 'u':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 45);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'i':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 46);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'j':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 47);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                    case 'k':
                        tile = new Tiles(j * 60, i * 60, 60, 60, 48);
                        platforms.add(tile.getHitBox());
                        gameRoot.getChildren().addAll(tile.getHitBox(), tile.getImageView());
                        break;
                }
            }
        }
    }
}