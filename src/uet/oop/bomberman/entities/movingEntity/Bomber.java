package uet.oop.bomberman.entities.movingEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends MovingEntity {

    public int frame = 0;
    private boolean enhancedFlame = false;
    private int speed = 25;


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
        super(x, y, img);
    }

    public void speedUp() {
        speed = 15;
    }

    @Override
    public void update() {
        frame++;
        if(frame % speed == 0) {
            if (pressD) {
                xPos += 1;
            } else if (pressA) {
                xPos -= 1;
            } else if (pressW) {
                yPos -= 1;
            } else if (pressS) {
                yPos += 1;
            }
        }
        if(frame == 501) {
            frame = 0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
       if (pressA) {
            gc.drawImage(Sprite.player_left.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        } else if (pressW) {
            gc.drawImage(Sprite.player_up.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        } else if (pressD){
            gc.drawImage(Sprite.player_right.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        } else {
            gc.drawImage(Sprite.player_down.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
    }

}

