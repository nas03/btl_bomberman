package uet.oop.bomberman.moving_entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;

import java.util.List;
public abstract class MovingEntity {
    public boolean isAlive = true;
    public boolean pressW, pressA, pressS, pressD;
    private int speed = 1;
    public int xPos;
    public int yPos;

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

