package uet.oop.bomberman.moving_entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.automove.SimpleMovement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

public class Kondoria extends Enemy {
    SimpleMovement movement = new SimpleMovement();
    protected int spellCounter = 0;
    private boolean skillReady = false;
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isSkillReady() {
        return skillReady;
    }

    public void setSkillReady(boolean skillReady) {
        this.skillReady = skillReady;
    }

    @Override
    public void update() {
        if(spellCounter == 30){
            skillReady = true;
            spellCounter = 0;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (pressA) {
            renderA(gc);
        } else if (pressD) {
            renderD(gc);
        } else if (pressW) {
            renderW(gc);
        } else if (pressS) {
            renderS(gc);
        } else {
            gc.drawImage(Sprite.kondoria_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
    }

    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {
        if (frame == 0) {
            spellCounter += 1;
            int direction = movement.calculateMovement();
            if (direction == 0 && canMove(stillObjects, xPos + 1, yPos)) {
                right();
                xPos += 1;
            }
            if (direction == 1 && canMove(stillObjects, xPos - 1, yPos)) {
                left();
                xPos -= 1;
            }
            if (direction == 2 && canMove(stillObjects, xPos, yPos + 1)) {
                down();
                yPos += 1;
            }
            if (direction == 3 && canMove(stillObjects, xPos, yPos - 1)) {
                up();
                yPos -= 1;
            }
        }

    }

    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos, int bomberYPos) {

    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.kondoria_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.kondoria_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.kondoria_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;

        if (frame < 11) {
            gc.drawImage(Sprite.kondoria_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.kondoria_right2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.kondoria_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.kondoria_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.kondoria_right2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.kondoria_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        }
        if (frame == 30) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.kondoria_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.kondoria_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.kondoria_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        }
        if (frame == 30) {
            frame = 0;
            pressW = false;
        }

    }

    public Enemy spawnBalloon(List <Entity> stillObjects) {
       Random random = new Random();
       int randomX = random.nextInt(30) + 1;
       int randomY = random.nextInt(12) + 1;
       if(!(stillObjects.get(randomY*31 + randomX) instanceof Brick || stillObjects.get(randomY*31 + randomX) instanceof Wall)) {
               return new Balloon(randomX, randomY, Sprite.balloom_right1.getFxImage());
       }
       return null;
    }
}
