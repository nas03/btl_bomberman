package uet.oop.bomberman.entities.movingEntity.enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {

    public boolean collideWithWall = false;

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
        }
    }

    @Override
    public void enemyMovement(char[][] map) {
        frame++;
        if (frame % 50 == 0) {
            if (canMove(xPos - 1, yPos, map) && !collideWithWall) {
                xPos -= 1;
                left();
                //this. = new Balloon(xPos, yPos, Sprite.balloom_left1.getFxImage());
            } else if (!canMove(xPos - 1, yPos,map )){
                collideWithWall = true;
            }
            if (canMove(xPos + 1, yPos,map) && collideWithWall) {
                xPos += 1;
                right();
                //balloon1 = new Balloon(xPos, yPos, Sprite.balloom_right1.getFxImage());
            }else if (!canMove(xPos + 1, yPos,map)) {
                collideWithWall = false;
            }
        }
        resetFrame();
    }
}
