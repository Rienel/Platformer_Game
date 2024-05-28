package org.example.platformer_game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User{
    private final StringProperty uname;
    private final IntegerProperty score;


    public User(String uname, int score) {
        this.uname = new SimpleStringProperty(uname);
        this.score = new SimpleIntegerProperty(score);
    }

    public StringProperty unameProperty() {
        return uname;
    }
    public IntegerProperty score() {
        return score;
    }


}
