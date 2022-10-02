package uet.oop.bomberman.entities;

import java.awt.event.KeyEvent;
import java.security.Key;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import java.awt.Component.*;

public class Bomber extends Entity {
    protected int xPos = 0;
    protected int yPos = 0;
    KeyHandler keyH;
    int speed = 16;


    public Bomber(int x, int y, Image img) {
        super( x, y, img);

    }

    @Override
    public void update() {

    }
}
