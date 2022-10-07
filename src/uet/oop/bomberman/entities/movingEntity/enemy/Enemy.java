package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;

import java.awt.*;
import java.util.List;

public abstract class Enemy extends MovingEntity {
    public int frame = 0;
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
    @Override
    public abstract void update();
    @Override
    public abstract void render(GraphicsContext gc);

    public abstract void enemyMovement(List <Entity> stillObjects);

    public void resetFrame() {
        if (frame == 500) {
            frame = 0;
        }
    }

}
