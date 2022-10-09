package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.nio.file.Paths;


public class Sound {

    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean loop = false;
    String path;
    public Sound(String path, boolean loop) {
        this.loop = loop;
        this.path = path;
        media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public static Sound backgroundMusic = new Sound("res/sound/background.wav", true);
    public static Sound exploision = new Sound("res/sound/bomb-ex.wav", false);
    public static Sound bomber_die = new Sound("res/sound/bomber-dead.wav", false);
    public static Sound enemyDie = new Sound("res/sound/enemy-dead.wav", false);

    public void play() {
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        });
        mediaPlayer.play();
    }


}