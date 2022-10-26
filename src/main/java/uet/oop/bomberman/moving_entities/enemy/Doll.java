package uet.oop.bomberman.moving_entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.automove.NormalMovement;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

public class Doll extends Enemy {
    protected boolean skillReady = false;
    int counter = 0;
    NormalMovement movement = new NormalMovement();
    public Doll(int x, int y, Image img) {
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
        if (frame == 0) {
            counter++;
        }
        if (counter == 20) {
            skillReady = true;
            counter = 0;
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
        } else if (!getAlive()) {
            renderDie(gc);
        } else {
            gc.drawImage(Sprite.doll_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
    }

    @Override
    public void enemyMovement(List<Entity> stillObjects, char[][] map) {
        if (frame == 0) {
            int direction = movement.calculateMovement();
            if (direction == 1 && canMove(stillObjects, xPos - 1, yPos)) {
                xPos -= 1;
                left();
            }
            if (direction == 0 && canMove(stillObjects, xPos + 1, yPos)) {
                xPos += 1;
                right();
            }
            if (direction == 2 && canMove(stillObjects, xPos, yPos - 1)) {
                yPos -= 1;
                up();
            }
            if (direction == 3 && canMove(stillObjects, xPos, yPos + 1)) {
                yPos += 1;
                down();
            }
        }
    }

    @Override
    public void complexEnemyMovement(List<Entity> stillObjects, char[][] map, int bomberXPos, int bomberYPos) {

    }

    public void teleport(List<Entity> stillObjects, int bomberX, int bomberY) {
        Random random = new Random();
        int randomX = bomberX - 1;
        int randomY = bomberY;
        if (!(stillObjects.get(randomY * 31 + randomX) instanceof Brick || stillObjects.get(randomY * 31 + randomX) instanceof Wall)) {
            xPos = randomX;
            yPos = randomY;
        } else {
            randomX = bomberX + 1;
            randomY = bomberY;
            if (!(stillObjects.get(randomY * 31 + randomX) instanceof Brick || stillObjects.get(randomY * 31 + randomX) instanceof Wall)) {
                xPos = randomX;
                yPos = randomY;
            }
        }
    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.doll_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.doll_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.doll_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;

        if (frame < 11) {
            gc.drawImage(Sprite.doll_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.doll_right2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.doll_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.doll_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.doll_right2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.doll_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        }
        if (frame == 30) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.doll_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.doll_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.doll_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        }
        if (frame == 30) {
            frame = 0;
            pressW = false;
        }
    }

    public void renderDie(GraphicsContext gc) {
        frame++;
        if (frame < 21) {
            gc.drawImage(Sprite.doll_dead.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 20) {
            frame = 0;
            delete = true;
        }
    }
}
