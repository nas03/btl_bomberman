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


    KeyHandler keyH = new KeyHandler();
    char move;
    int speed = 16;

    public int xPos = 1;
    public int yPos = 1;


    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }


    @Override
    public void update() {
       // movePlayer();
    }

    /*public void movePlayer() {
        if (move == 'W') {
            y += speed;
        }
        if (move == 'S') {
            y -= speed;
        }
        if (move == 'A') {
            x -= speed;
        }
        if (move == 'D') {
            x += speed;
        }
    }*/
}
