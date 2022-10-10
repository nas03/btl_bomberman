package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class Kondoria extends Enemy{

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {

    }

    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos, int bomberYPos) {

    }
}
