package uet.oop.bomberman.entities.movingEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.*;

import java.util.List;


public abstract class MovingEntity {
    protected final int WIDTH = 31;
    protected final int HEIGHT = 13;
    public boolean isAlive = true;
    public boolean pressW, pressA, pressS, pressD;
    private int speed = 1;
    public static int xPos = 1;
    public static int yPos = 1;

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
        if (stillObjects.get(getObjectIndex) instanceof Wall) {
            return false;
        }
        return x - 1 >= 0 && x + 1 <= WIDTH && y - 1 >= 0 && y + 1 <= HEIGHT;
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

