package uet.oop.bomberman.entities.movingEntity.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.entities.movingEntity.enemy.automove.ComplexMovement;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

public class Oneal extends Enemy {
    public int direction;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean solved = false;
    public int mapResetFrame = 0;
    Map initMap = new Map("src/main/resources/levels/Level1.txt");
    public boolean locChange = false;
    public boolean[][] moveMap = new boolean[13][31];
    public char[][] previousMap = initMap.map;

    public boolean getSolve() {
        return solved;
    }

    @Override
    public void render(GraphicsContext gc) {
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
                    previousMap = map;
                    ComplexMovement movement = new ComplexMovement(map);
                    movement.voidTypeComplexMovement(map, yPos, xPos, bomberYPos, bomberXPos);
                    if (movement.solved) {
                        moveMap = movement.correctPath;
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
        if (frame < 11) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE + 32 - frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressA = false;
        }

    }

    public void renderD(GraphicsContext gc) {
        frame++;

        if (frame < 11) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 21) {
            gc.drawImage(Sprite.oneal_right2.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        } else if (frame < 31) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE - 32 + frame, yPos * Sprite.SCALED_SIZE);
        }
        if (frame == 30) {
            frame = 0;
            pressD = false;
        }

    }

    public void renderS(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.oneal_right2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.oneal_right1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE - 32 + frame);
        }
        if (frame == 30) {
            frame = 0;
            pressS = false;
        }

    }

    public void renderW(GraphicsContext gc) {
        frame++;
        if (frame < 11) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 21) {
            gc.drawImage(Sprite.oneal_left1.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        } else if (frame < 31) {
            gc.drawImage(Sprite.oneal_left2.getFxImage(), xPos * Sprite.SCALED_SIZE, yPos * Sprite.SCALED_SIZE + 32 - frame);
        }
        if (frame == 30) {
            frame = 0;
            pressW = false;
        }

    }
}
