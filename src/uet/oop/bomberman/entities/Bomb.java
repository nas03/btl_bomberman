package uet.oop.bomberman.entities;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

    public int bombFrame = 0;
    public boolean startExplode = false;
    public boolean flag = true;
    private boolean explode = false;
    private boolean explode1 = false;

    public boolean isStartExplode() {
        return startExplode;
    }

    public void setStartExplode(boolean startExplode) {
        this.startExplode = startExplode;
    }

    public boolean getExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public void resetBomb() {
        explode = false;
        bombFrame = 0;
        startExplode = false;
        explode1 = false;
    }

    public boolean bombTouched(int xPos, int yPos) {

        if (xPos == x / 32 && yPos == y / 32) {
            return true;
        }
        if (explode1) {
            if (xPos == (x / 32) + 1 && yPos == y / 32) {
                return true;
            } else if (xPos == (x / 32) - 1 && yPos == y / 32) {
                return true;
            } else if (xPos == x / 32 && yPos == (y / 32) + 1) {
                return true;
            } else if (xPos == x / 32 && yPos == (y / 32) - 1) {
                return true;
            }
        }
        return false;

    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        startExplode = true;
    }

    public void bombExplosion(GraphicsContext gc, char[][] map, List<Entity> stillObjects) {
        bombFrame++;

        if (bombFrame < 100) {
            gc.drawImage(Sprite.bomb.getFxImage(), x, y);
        } else if (bombFrame < 150) {
            explode = true;
            gc.drawImage(Sprite.bomb_exploded.getFxImage(), x, y);
        } else if (bombFrame < 200) {
            gc.drawImage(Sprite.bomb_exploded1.getFxImage(), x, y);
        } else if (bombFrame < 250) {
            explode1 = true;
            gc.drawImage(Sprite.bomb_exploded2.getFxImage(), x, y);
            //get object affected by flame
            int destroyBrick = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE - 1;
            int destroyBrick1 = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE + 1;
            int destroyBrick2 = (y / Sprite.SCALED_SIZE - 1) * 31 + x / Sprite.SCALED_SIZE;
            int destroyBrick3 = (y / Sprite.SCALED_SIZE + 1) * 31 + x / Sprite.SCALED_SIZE;
            //handle object affected by flame
            if (stillObjects.get(destroyBrick) instanceof Brick) {
                stillObjects.remove(destroyBrick);
                stillObjects.add(destroyBrick, new Grass(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] = ' ';
            } else if (!(stillObjects.get(destroyBrick) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal_left_last.getFxImage(), (x - 32), y);
            }

            if (stillObjects.get(destroyBrick1) instanceof Brick) {
                stillObjects.remove(destroyBrick1);
                stillObjects.add(destroyBrick1, new Grass(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1] = ' ';
            } else if (!(stillObjects.get(destroyBrick1) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal_right_last.getFxImage(), x + 32, y);
            }

            if (stillObjects.get(destroyBrick2) instanceof Brick) {
                stillObjects.remove(destroyBrick2);
                stillObjects.add(destroyBrick2, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1,
                                Sprite.grass.getFxImage()));
                map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] = ' ';
            } else if (!(stillObjects.get(destroyBrick2) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical_top_last.getFxImage(), x, (y - 32));
            }

            if (stillObjects.get(destroyBrick3) instanceof Brick) {
                stillObjects.remove(destroyBrick3);
                stillObjects.add(destroyBrick3, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1,
                                Sprite.grass.getFxImage()));
                map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] = ' ';
            } else if (!(stillObjects.get(destroyBrick3) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical_down_last.getFxImage(), x, (y + 32));
            }

        } else if (bombFrame > 250) {
            resetBomb();
            flag = false;
        }

    }
}


