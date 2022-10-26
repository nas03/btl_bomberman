package uet.oop.bomberman.moving_entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.automove.NormalMovement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Minvo extends Enemy {
    NormalMovement movement = new NormalMovement();

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (pressA) {
            renderA(gc);
        } else if (pressS) {
            renderS(gc);
        } else if (pressD) {
            renderD(gc);
        } else if (pressW) {
            renderW(gc);
        } else if(!getAlive()) {
            renderDie(gc);
        }
        else  {
            gc.drawImage(Sprite.minvo_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
    }

    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {

    }

    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos, int bomberYPos) {
        if(frame == 0) {
            int direction = movement.intTypeComplexMovement(map, xPos, yPos, bomberXPos, bomberYPos);
            if (direction == 0 && canMove(stillObjects, xPos - 1, yPos)) {
                xPos -= 1;
                left();
            } else if (direction == 1 && canMove(stillObjects, xPos + 1, yPos)) {
                xPos += 1;
                right();
            } else if (direction == 2 && canMove(stillObjects, xPos, yPos + 1)) {
                down();
                yPos += 1;
            } else if (direction == 3 && canMove(stillObjects, xPos, yPos - 1)) {
                up();
                yPos -= 1;
            }
        }
    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.minvo_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.minvo_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.minvo_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;

        if (frame < 11) {
            gc.drawImage(Sprite.minvo_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.minvo_right2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.minvo_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.minvo_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.minvo_right2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.minvo_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        }
        if (frame == 30) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.minvo_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.minvo_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.minvo_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        }
        if (frame == 30) {
            frame = 0;
            pressW = false;
        }

    }

    public void renderDie(GraphicsContext gc) {
        frame++;
        if(frame < 21) {
            gc.drawImage(Sprite.minvo_dead.getFxImage(), xPos* Sprite.SCALED_SIZE, yPos* Sprite.SCALED_SIZE);
        }
        if(frame == 20) {
            frame = 0;
            delete = true;
        }
    }
}
