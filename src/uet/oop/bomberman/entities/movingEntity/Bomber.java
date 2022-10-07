package uet.oop.bomberman.entities.movingEntity;

import java.awt.event.KeyEvent;
import java.security.Key;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;


import java.awt.event.KeyListener;
import javax.swing.JTextField;
import java.awt.Component.*;


public class Bomber extends MovingEntity {

    private boolean enhancedFlame = false;
    private boolean speedUp = false;
    private int speedUpTime = 0;

    public boolean isSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(boolean speedUp) {
        this.speedUp = speedUp;
    }

    public void setEnhancedFlame(boolean enhancedFlame) {
        this.enhancedFlame = enhancedFlame;
    }
    public boolean getEnhancedFlame() {
        return enhancedFlame;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public Bomber(int x, int y, Image img) {
        super(x,y,img);
    }

    public void speedUp() {
        speedUpTime++;
        if(speedUpTime < 100) {
            setSpeed(2);
        }else {
            speedUpTime = 0;
            setSpeed(1);
            speedUp = false;
        }
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

