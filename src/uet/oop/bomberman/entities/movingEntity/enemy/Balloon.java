package uet.oop.bomberman.entities.movingEntity.enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Balloon extends Enemy {

    Random random = new Random();
    public Balloon(int x, int y, Image img) {
        super(x,y,img);
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
        } else if (!getAlive()) {
            gc.drawImage(Sprite.balloom_dead.getFxImage(),xPos*Sprite.SCALED_SIZE, yPos* Sprite.SCALED_SIZE);
        }
    }

    @Override
    public void enemyMovement(List<Entity> stillObjects) {
        frame++;
        if (frame % 50 == 0) {
            int direction = random.nextInt(2);

             if ( direction == 1 && canMove(stillObjects,xPos - 1, yPos) ) {
                xPos -= 1;
                left();
             }
             if ( direction == 0 && canMove(stillObjects,xPos + 1, yPos)) {
                xPos += 1;
                right();
            }

        }
        resetFrame();
    }
}
