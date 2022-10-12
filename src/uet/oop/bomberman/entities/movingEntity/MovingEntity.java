package uet.oop.bomberman.entities.movingEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
public abstract class MovingEntity {
    protected final int WIDTH = 31;
    protected final int HEIGHT = 13;
    public boolean isAlive = true;
    public int frame = 0;
    public boolean pressW, pressA, pressS, pressD;
    private int speed = 1;
    public int xPos = 1, yPos = 1;

    protected Image img;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public MovingEntity(int x, int y, Image img) {
        xPos = x;
        yPos = y;
        this.img = img;
    }

    public abstract void update();

    public abstract void render(GraphicsContext gc);

    public boolean canMove(List<Entity> stillObjects, int x, int y) {
        int getObjectIndex = y * 31 + x;
        if (stillObjects.get(getObjectIndex) instanceof Brick) {
            return false;
        }
        return !(stillObjects.get(getObjectIndex) instanceof Wall);
    }

    public void left() {
        pressA = true;
        pressW = false;
        pressS = false;
        pressD = false;
    }

    public void up() {
        pressA = false;
        pressW = true;
        pressS = false;
        pressD = false;

    }

    public void down() {
        pressA = false;
        pressW = false;
        pressS = true;
        pressD = false;

    }

    public void right() {
        pressA = false;
        pressW = false;
        pressS = false;
        pressD = true;
    }
}

