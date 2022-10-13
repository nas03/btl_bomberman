package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Sound {
    private MediaPlayer mediaPlayer;
    private Media media;
    private boolean loop = false;
    public String path;
    public Sound(String _path, boolean loop)  {
        this.loop = loop;
        File file = new File(_path);
        this.path = file.getAbsolutePath();
        media = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public static Sound backgroundMusic = new Sound("/Users/sonnguyen/IdeaProjects/bomberman/src/main/resources/sound/background.wav", true);
    public static Sound explosion = new Sound("/Users/sonnguyen/IdeaProjects/bomberman/src/main/resources/sound/bomb-ex.wav", false);
    public static Sound bomber_die = new Sound("/Users/sonnguyen/IdeaProjects/bomberman/src/main/resources/sound/bomber-dead.wav", false);
    public static Sound enemyDie = new Sound("/Users/sonnguyen/IdeaProjects/bomberman/src/main/resources/sound/enemy-dead.wav", false);

    public void play() {
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(new Media(Paths.get(path).toUri().toString()));
        });
        mediaPlayer.play();
    }


}