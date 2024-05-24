package org.example.platformer_game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sounds {

    Clip clip;

    URL soundURL[] = new URL[30];

    public Sounds() {
        soundURL[0] = getClass().getResource("/Sound/Jump.wav");
        soundURL[1] = getClass().getResource("/Sound/finishedGame.wav");
        soundURL[2] = getClass().getResource("/Sound/Correct.wav");
        soundURL[3] = getClass().getResource("/Sound/LevelOne.wav");
        soundURL[4] = getClass().getResource("/Sound/LevelTwo.wav");
        soundURL[5] = getClass().getResource("/Sound/LevelThree.wav");
        soundURL[6] = getClass().getResource("/Sound/MainPage.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            //nujabes for the groovy yayayayyy
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }


}
