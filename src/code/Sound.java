package code;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    private AudioClip clip;

    Sound(String filename) {
        clip = Applet.newAudioClip(getClass().getResource("/resources/" + filename));
    }

    public void play() {
        new Thread() {
            public void run() {
                clip.play();
            }
        }.start();
    }
}
