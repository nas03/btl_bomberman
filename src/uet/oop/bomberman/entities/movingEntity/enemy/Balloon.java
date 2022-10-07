package uet.oop.bomberman.entities.movingEntity.enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;
import uet.oop.bomberman.entities.movingEntity.enemy.automove.SimpleMovement;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Balloon extends Enemy {

   SimpleMovement movement = new SimpleMovement();
    public Balloon(int x, int y, Image img) {
        super(x,y,img);
    }
    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos,int bomberYPos) {

    }
    @Override
    public void update() {

    }
    @Override
    public void render(GraphicsContext gc) {
        if (pressA) {
            gc.drawImage(Sprite.balloom_left1.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos*Sprite.SCALED_SIZE);
        } else if (pressD) {
            gc.drawImage(Sprite.balloom_right1.getFxImage(), xPos*Sprite.SCALED_SIZE,yPos*Sprite.SCALED_SIZE);
        } else if (pressW) {
            gc.drawImage(Sprite.balloom_right2.getFxImage(), xPos*Sprite.SCALED_SIZE,yPos *Sprite.SCALED_SIZE);
        } else if (pressS) {
            gc.drawImage(Sprite.balloom_left2.getFxImage(), xPos* Sprite.SCALED_SIZE, yPos* Sprite.SCALED_SIZE);
        } else if (!getAlive()) {
            gc.drawImage(Sprite.balloom_dead.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos* Sprite.SCALED_SIZE);
        }
    }

    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {
        frame++;
        if (frame % 40 == 0) {
            int direction = movement.calculateMovement();
             if ( direction == 1 && canMove(stillObjects,xPos - 1, yPos) ) {

                 xPos -= 1;
                left();
             }
             if ( direction == 0 && canMove(stillObjects,xPos + 1, yPos)) {
                xPos += 1;
                right();
            }
             if(direction == 2 && canMove(stillObjects,xPos, yPos -1)) {
                 yPos -= 1;
                 up();
             }
             if(direction == 3 && canMove(stillObjects,xPos, yPos + 1)) {
                 yPos += 1;
                 down();
             }
        }
        resetFrame();
    }
}
