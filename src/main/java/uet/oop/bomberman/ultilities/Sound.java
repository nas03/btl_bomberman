package uet.oop.bomberman.ultilities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;


public class Sound {
    public static Sound backgroundMusic = new Sound("src/main/resources/sound/background.wav", true);
    public static Sound explosion = new Sound("src/main/resources/sound/bomb-ex.wav", false);
    public static Sound bomber_die = new Sound("src/main/resources/sound/bomber-dead.wav", false);
    public static Sound enemyDie = new Sound("src/main/resources/sound/enemy-dead.wav", false);
    public String path;
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean loop = false;

    public Sound(String path, boolean loop) {
        this.loop = loop;
        this.path = path;
        media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play() {
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        });
        mediaPlayer.play();
    }

    public void loopPlay() {
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }

}