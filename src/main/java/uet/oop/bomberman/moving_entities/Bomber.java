package uet.oop.bomberman.moving_entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;


public class Bomber extends MovingEntity {
    public int frame = 0;
    private boolean enhancedFlame = false;
    private int speed = 1;
    public int bombLimit = 1;
    private boolean portal = false;
    private Image currentSprite = Sprite.player_right.getFxImage();

    public boolean isPortal() {
        return portal;
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
        super(x, y, img);
    }

    public void speedUp() {
        speed = 2;
    }

    @Override
    public void update() {
    }
    public void checkInItem(List<Entity> stillObjects) {
        int currentLocX = xPos;
        int currentLocY = yPos;
        if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof FlameItem) {
            System.out.println("In FlameItem");
            setEnhancedFlame(true);
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));

        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof BombItem) {
            System.out.println("In BombItem");
            bombLimit++;
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));
        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof SpeedItem) {
            System.out.println("In SpeedItem");
            speedUp();
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));
        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof Portal) {
            System.out.println("in Portal");
            portal = true;
        } else {
            portal = false;
        }

    }
    @Override
    public void render(GraphicsContext gc) {
        if (pressA) {
            currentSprite = Sprite.player_left.getFxImage();
            renderA(gc);
        } else if (pressW) {
            currentSprite = Sprite.player_up.getFxImage();
            renderW(gc);
        } else if (pressD) {
            currentSprite = Sprite.player_right.getFxImage();
            renderD(gc);
        } else if (pressS) {
            currentSprite = Sprite.player_down.getFxImage();
            renderS(gc);
        } else {
            gc.drawImage(currentSprite, xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.player_left.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.player_left_1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.player_left_2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.player_right.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.player_right_1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.player_right_2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.player_down.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.player_down_1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.player_down_2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.player_up.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.player_up_1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.player_up_2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressW = false;
        }

    }

    public int getEntityPosInArray(int x, int y) {
        return y * 31 + x;
    }



}

