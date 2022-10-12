package uet.oop.bomberman.entities.movingEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends MovingEntity {

    public int frame = 0;
    public boolean ableToMove = true;
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
        /*frame++;

            if (pressD) {
                xPos += 1;
            } else if (pressA) {
                xPos -= 1;
            } else if (pressW) {
                yPos -= 1;
            } else if (pressS) {
                yPos += 1;
            }
        }*/

    }

    @Override
    public void render(GraphicsContext gc) {
        if (pressA) {

            renderA(gc);

        } else if (pressW) {

            renderW(gc);

        } else if (pressD) {

            renderD(gc);

        } else if(pressS){

            renderS(gc);
        } else {
            gc.drawImage(Sprite.player_right.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE);
        }

    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.player_left.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.player_left_1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.player_left_2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressA = false;
        }

    }
    public void renderD(GraphicsContext gc) {
        frame++;

        if (frame < 11) {
            gc.drawImage(Sprite.player_right.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 +  frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.player_right_1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 +  frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.player_right_2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressD = false;
        }

    }
    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.player_down.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE -32 +  frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.player_down_1.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE -32 +  frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.player_down_2.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE -32 +  frame);
        }
        if (frame == 30) {
            frame = 0;
            pressS = false;
        }

    }
    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.player_up.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.player_up_1.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.player_up_2.getFxImage(), xPos * Sprite.SCALED_SIZE , yPos * Sprite.SCALED_SIZE + 32 - frame);
        }
        if (frame == 30) {
            frame = 0;
            pressW = false;
        }

    }


}

