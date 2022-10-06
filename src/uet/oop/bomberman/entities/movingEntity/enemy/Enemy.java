package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

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

    public abstract void enemyMovement(char[][] map);

    public void resetFrame() {
        if (frame == 501) {
            frame = 0;
        }
    }

}
