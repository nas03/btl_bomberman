package uet.oop.bomberman.entities.movingEntity;

import java.awt.event.KeyEvent;
import java.security.Key;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.event.KeyListener;
import javax.swing.JTextField;
import java.awt.Component.*;


public class Bomber extends MovingEntity {

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public Bomber(int x, int y, Image img) {
        super(x,y,img);
    }

    @Override
    public void update() {

    }
    @Override
    public void render(GraphicsContext gc) {
        if(pressS) {
            gc.drawImage(Sprite.player_down.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos*Sprite.SCALED_SIZE);
        } else if(pressA) {
            gc.drawImage(Sprite.player_left.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos*Sprite.SCALED_SIZE);
        } else if(pressW) {
            gc.drawImage(Sprite.player_up.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos*Sprite.SCALED_SIZE);
        } else {
            gc.drawImage(Sprite.player_right.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos*Sprite.SCALED_SIZE);
        }
    }

}

