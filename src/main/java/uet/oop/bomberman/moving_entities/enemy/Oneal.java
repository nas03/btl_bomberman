package uet.oop.bomberman.moving_entities.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.automove.ComplexMovement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Random;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }
    private int speed = 1;
    Random random = new Random();
    protected boolean solved = false;
    int mapResetFrame = 0;
    private boolean locChange = false;
    private boolean[][] moveMap = new boolean[13][31];
    public void setLocChange(boolean locChange) {
        this.locChange = locChange;
    }

    @Override
    public void render(GraphicsContext gc) {
        /*int fast = random.nextInt(2);
        if(fast == 0) speed = 1;
        else speed = 2;*/
        speed = 1;
        if (pressD) {
            renderD(gc);
        } else if (pressA) {
            renderA(gc);
        } else if (pressW) {
            renderW(gc);
        } else if (pressS) {
            renderS(gc);
        } else {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE);
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
        if(frame == 0) {
            mapResetFrame++;
            if (mapResetFrame % 25 == 0) {
                if ((!solved) || locChange) {
                    ComplexMovement movement = new ComplexMovement();
                    movement.voidTypeComplexMovement(map, yPos, xPos, bomberYPos, bomberXPos);
                    if (movement.isSolved()) {
                        moveMap = movement.getCorrectPath();
                        solved = true;

                    }
                } else {
                    moveMap[yPos][xPos] = false;
                    moveMap[bomberYPos][bomberXPos] = true;
                    if (moveMap[yPos + 1][xPos]) {
                        yPos += 1;
                        down();
                    } else if (moveMap[yPos - 1][xPos]) {
                        yPos -= 1;
                        up();
                    } else if (moveMap[yPos][xPos + 1]) {
                        xPos += 1;
                        right();
                    } else if (moveMap[yPos][xPos - 1]) {
                        xPos -= 1;
                        left();
                    }
                }
            }
        }
    }

    public void renderA(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame*speed, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.oneal_right2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame*speed, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame*speed);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame <= 10/speed) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        } else if (frame <= 20/speed) {
            gc.drawImage(Sprite.oneal_right2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        } else if (frame <= 30/speed) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame*speed);
        }
        if (frame == 30/speed) {
            frame = 0;
            pressW = false;
        }

    }
}
