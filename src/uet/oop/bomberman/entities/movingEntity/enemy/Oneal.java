package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.entities.movingEntity.enemy.automove.ComplexMovement;
import uet.oop.bomberman.entities.movingEntity.enemy.automove.SimpleMovement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Oneal extends Enemy{
    public int direction;
    public Oneal(int x, int y, Image img) {
        super(x,y,img);
    }
    public boolean solved = false;
    Map initMap = new Map();
    public boolean locChange = false;
    public boolean[][] moveMap = new boolean[13][31];
    public char[][] previousMap = initMap.getMap();
    public boolean getSolve() {
        return solved;
    }
    @Override
    public void render(GraphicsContext gc) {
        if(direction == 0) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(),xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
        if(direction == 1) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos* Sprite.SCALED_SIZE,yPos*Sprite.SCALED_SIZE);
        }
        if (direction == 2) {
            gc.drawImage(Sprite.oneal_right2.getFxImage(), xPos*Sprite.SCALED_SIZE,yPos*Sprite.SCALED_SIZE);
        }
        else {
            gc.drawImage(Sprite.oneal_left1.getFxImage(),xPos* Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE );
        }

    }
    @Override
    public void update() {

    }
    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {

    }
    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos, int bomberYPos) {
        frame++;
        if(frame % 25 == 0) {
            if((!solved) || locChange) {
                previousMap = map;
                ComplexMovement movement = new ComplexMovement(map);
                movement.calculateComplexMovement(map, yPos, xPos, bomberYPos, bomberXPos);
                if (movement.solved) {
                    moveMap = movement.correctPath;
                    solved = true;

                }
            }else {
                moveMap[yPos][xPos] = false;
                moveMap[bomberYPos][bomberXPos] = true;
                if (moveMap[yPos+1][xPos]) {
                    yPos += 1;
                    down();
                }
                else if (moveMap[yPos-1][xPos]) {
                    yPos -= 1;
                    up();
                }else if (moveMap[yPos][xPos+1]) {
                    xPos += 1;
                    right();
                }else if (moveMap[yPos][xPos - 1]) {
                    xPos -= 1;
                    left();
                }
            }
        }
        resetFrame();
    }
}
