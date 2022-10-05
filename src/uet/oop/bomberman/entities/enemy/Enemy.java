package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Map;

import java.awt.*;

public class Enemy extends Entity {
    private boolean isAlive = true;

    public boolean getAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Enemy(int x, int y, Image img) {
        super(x,y,img);
    }



}
