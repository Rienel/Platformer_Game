package org.example.platformer_game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User{
    private final StringProperty uname;
    private final IntegerProperty lvl1score;
    private final IntegerProperty lvl2score;
    private final IntegerProperty lvl3score;


    public User(String uname, int lvl1score, int lvl2score, int lvl3score) {
        this.uname = new SimpleStringProperty(uname);
        this.lvl1score = new SimpleIntegerProperty(lvl1score);
        this.lvl2score = new SimpleIntegerProperty(lvl2score);
        this.lvl3score = new SimpleIntegerProperty(lvl3score);
    }

    public StringProperty unameProperty() {
        return uname;
    }
    public IntegerProperty lvl1Property() {
        return lvl1score;
    }
    public IntegerProperty lvl2Property() {
        return lvl2score;
    }
    public IntegerProperty lvl3Property() {
        return lvl3score;
    }


}
