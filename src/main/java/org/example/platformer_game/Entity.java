package org.example.platformer_game;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public abstract class Entity extends Node{
    protected int x , y, w, h;
    protected Color color;

    public Entity(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

}