package uet.oop.bomberman.entities;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
        } else if (bombFrame < 155) {
            gc.drawImage(Sprite.bomb_exploded1.getFxImage(), x, y);
        } else if (bombFrame < 160) {
            explode1 = true;
            gc.drawImage(Sprite.bomb_exploded2.getFxImage(), x, y);
            //get object affected by flame
            int destroyBrick_left = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE - 1;
            int destroyBrick_right = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE + 1;
            int destroyBrick_up = (y / Sprite.SCALED_SIZE - 1) * 31 + x / Sprite.SCALED_SIZE;
            int destroyBrick_down = (y / Sprite.SCALED_SIZE + 1) * 31 + x / Sprite.SCALED_SIZE;

            if (!(stillObjects.get(destroyBrick_down) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(), x, (y + 32));
            }
            if (!(stillObjects.get(destroyBrick_left) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(), (x - 32), y);
            }
            if (!(stillObjects.get(destroyBrick_right) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(), x + 32, y);
            }
            if (!(stillObjects.get(destroyBrick_up) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(), x, (y - 32));
            }

            //handle object affected by flame
            if (stillObjects.get(destroyBrick_left) instanceof Brick) {
                stillObjects.remove(destroyBrick_left);
                if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == 'f') {
                    stillObjects.add(destroyBrick_left, new FlameItem(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == 'x') {
                    stillObjects.add(destroyBrick_left, new Portal(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                } else if(map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == '*'){
                    stillObjects.add(destroyBrick_left, new Grass(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] = ' ';
                }
            }


            if (stillObjects.get(destroyBrick_right) instanceof Brick) {
                stillObjects.remove(destroyBrick_right);
                if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == 'f') {
                    stillObjects.add(destroyBrick_right, new FlameItem(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == 'x') {
                    stillObjects.add(destroyBrick_right, new Portal(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                } else if(map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == '*'){
                    stillObjects.add(destroyBrick_right, new Grass(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] = ' ';
                }
            }

            if (stillObjects.get(destroyBrick_up) instanceof Brick) {
                stillObjects.remove(destroyBrick_up);
                if (map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] == 'f') {
                    stillObjects.add(destroyBrick_up, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] == 'x') {
                    stillObjects.add(destroyBrick_up, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.portal.getFxImage()));
                } else {
                    stillObjects.add(destroyBrick_up, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] = ' ';
                }
            }

            if (stillObjects.get(destroyBrick_down) instanceof Brick) {
                stillObjects.remove(destroyBrick_down);
                if (map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] == 'f') {
                    stillObjects.add(destroyBrick_down, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] == 'x') {
                    stillObjects.add(destroyBrick_down, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.portal.getFxImage()));
                } else {
                    stillObjects.add(destroyBrick_down, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] = ' ';
                }
            }
        } else if (bombFrame > 160) {
            resetBomb();
            flag = false;
        }

    }

    public void enhancedBombExplosion(GraphicsContext gc, char[][] map, List<Entity> stillObjects) {
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
            int destroyBrick_left = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE - 1;
            int destroyBrick_left1 = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE - 2;

            int destroyBrick_right = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE + 1;
            int destroyBrick_right1 = y / Sprite.SCALED_SIZE * 31 + x / Sprite.SCALED_SIZE + 2;

            int destroyBrick_up = (y / Sprite.SCALED_SIZE - 1) * 31 + x / Sprite.SCALED_SIZE;
            int destroyBrick_up1 = (y / Sprite.SCALED_SIZE - 2) * 31 + x / Sprite.SCALED_SIZE;

            int destroyBrick_down = (y / Sprite.SCALED_SIZE + 1) * 31 + x / Sprite.SCALED_SIZE;
            int destroyBrick_down1 = (y / Sprite.SCALED_SIZE + 2) * 31 + x / Sprite.SCALED_SIZE;


            if (!(stillObjects.get(destroyBrick_down) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x, (y + 32));
            }

            if (!(stillObjects.get(destroyBrick_left) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal2.getFxImage(), (x - 32), y);
            }
            if (!(stillObjects.get(destroyBrick_right) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_horizontal2.getFxImage(), x + 32, y);
            }
            if (!(stillObjects.get(destroyBrick_up) instanceof Wall)) {
                gc.drawImage(Sprite.explosion_vertical2.getFxImage(), x, (y - 32));
            }
            //enhanced render
            try {
                if ((!(stillObjects.get(destroyBrick_down1) instanceof Wall)) && (y / Sprite.SCALED_SIZE) + 2 <= HEIGHT && (!(stillObjects.get(destroyBrick_down) instanceof Wall))) {
                    gc.drawImage(Sprite.explosion_vertical_down_last2.getFxImage(), x, (y + 64));
                }
                if (stillObjects.get(destroyBrick_down1) instanceof Brick && y / Sprite.SCALED_SIZE + 2 <= HEIGHT && (!(stillObjects.get(destroyBrick_down) instanceof Wall))) {
                    stillObjects.remove(destroyBrick_down1);
                    if (map[y / Sprite.SCALED_SIZE + 2][x / Sprite.SCALED_SIZE] == 'f') {
                        stillObjects.add(destroyBrick_down1, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 2, Sprite.powerup_flames.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE + 2][x / Sprite.SCALED_SIZE] == 'x') {
                        stillObjects.add(destroyBrick_down1, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 2, Sprite.portal.getFxImage()));
                    } else {
                        stillObjects.add(destroyBrick_down1, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 2, Sprite.grass.getFxImage()));
                        map[y / Sprite.SCALED_SIZE + 2][x / Sprite.SCALED_SIZE] = ' ';
                    }
                }
            } catch (Exception e) {
                System.out.println("map out of bound");
            }

            try {
                if (!(stillObjects.get(destroyBrick_left1) instanceof Wall) && x / Sprite.SCALED_SIZE - 2 >= 0 && !(stillObjects.get(destroyBrick_left) instanceof Wall)) {
                    gc.drawImage(Sprite.explosion_horizontal_left_last2.getFxImage(), (x - Sprite.SCALED_SIZE * 2), y);
                }
                if (stillObjects.get(destroyBrick_left1) instanceof Brick && x / Sprite.SCALED_SIZE - 2 >= 0 && !(stillObjects.get(destroyBrick_left) instanceof Wall)) {
                    stillObjects.remove(destroyBrick_left1);
                    if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 2] == 'f') {
                        stillObjects.add(destroyBrick_left1, new FlameItem(x / Sprite.SCALED_SIZE - 2, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 2] == 'x') {
                        stillObjects.add(destroyBrick_left1, new Portal(x / Sprite.SCALED_SIZE - 2, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 2] == '*') {
                        stillObjects.add(destroyBrick_left1, new Grass(x / Sprite.SCALED_SIZE - 2, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                        map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 2] = ' ';
                    }
                }
            } catch (Exception e) {
                System.out.println(" map left1 out of bound");
            }

            try {
                if (!(stillObjects.get(destroyBrick_right1) instanceof Wall) && x / Sprite.SCALED_SIZE + 2 <= WIDTH && !(stillObjects.get(destroyBrick_right) instanceof Wall)) {
                    gc.drawImage(Sprite.explosion_horizontal_right_last2.getFxImage(), x + Sprite.SCALED_SIZE * 2, y);
                }
                if (stillObjects.get(destroyBrick_right1) instanceof Brick && x / Sprite.SCALED_SIZE + 2 <= WIDTH && !(stillObjects.get(destroyBrick_right) instanceof Wall)) {
                    stillObjects.remove(destroyBrick_right1);
                    if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 2] == 'f') {
                        stillObjects.add(destroyBrick_right1, new FlameItem(x / Sprite.SCALED_SIZE + 2, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 2] == 'x') {
                        stillObjects.add(destroyBrick_right1, new Portal(x / Sprite.SCALED_SIZE + 2, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 2] == '*') {
                        stillObjects.add(destroyBrick_right1, new Grass(x / Sprite.SCALED_SIZE + 2, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                        map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 2] = ' ';
                    }
                }
            } catch (Exception e) {
                System.out.println("Map out of bound righ1");
            }

            try {
                if (!(stillObjects.get(destroyBrick_up1) instanceof Wall) && x / Sprite.SCALED_SIZE - 2 >= 0 && !(stillObjects.get(destroyBrick_up) instanceof Wall)) {
                    gc.drawImage(Sprite.explosion_vertical_top_last2.getFxImage(), x, (y - Sprite.SCALED_SIZE * 2));
                }
                if (stillObjects.get(destroyBrick_up1) instanceof Brick && y / Sprite.SCALED_SIZE - 2 > 0 && !(stillObjects.get(destroyBrick_up1) instanceof Wall)) {
                    stillObjects.remove(destroyBrick_up1);
                    if (map[y / Sprite.SCALED_SIZE - 2][x / Sprite.SCALED_SIZE] == 'f') {
                        stillObjects.add(destroyBrick_up1, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 2, Sprite.powerup_flames.getFxImage()));
                    } else if (map[y / Sprite.SCALED_SIZE - 2][x / Sprite.SCALED_SIZE] == 'x') {
                        stillObjects.add(destroyBrick_up1, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 2, Sprite.portal.getFxImage()));
                    } else {
                        stillObjects.add(destroyBrick_up1, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 2, Sprite.grass.getFxImage()));
                        map[y / Sprite.SCALED_SIZE - 2][x / Sprite.SCALED_SIZE] = ' ';
                    }
                }
            } catch (Exception e) {
                System.out.println("Map out of bound up1");
            }

            //handle object affected by flame
            if (stillObjects.get(destroyBrick_left) instanceof Brick) {
                stillObjects.remove(destroyBrick_left);
                if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == 'f') {
                    stillObjects.add(destroyBrick_left, new FlameItem(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == 'x') {
                    stillObjects.add(destroyBrick_left, new Portal(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                } else if(map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] == '*'){
                    stillObjects.add(destroyBrick_left, new Grass(x / Sprite.SCALED_SIZE - 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1] = ' ';
                }
            }

            if (stillObjects.get(destroyBrick_right) instanceof Brick) {
                stillObjects.remove(destroyBrick_right);
                if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == 'f') {
                    stillObjects.add(destroyBrick_right, new FlameItem(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == 'x') {
                    stillObjects.add(destroyBrick_right, new Portal(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.portal.getFxImage()));
                } else if(map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] == '*'){
                    stillObjects.add(destroyBrick_right, new Grass(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE +1] = ' ';
                }
            }

            if (stillObjects.get(destroyBrick_up) instanceof Brick) {
                stillObjects.remove(destroyBrick_up);
                if (map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] == 'f') {
                    stillObjects.add(destroyBrick_up, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] == 'x') {
                    stillObjects.add(destroyBrick_up, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.portal.getFxImage()));
                } else {
                    stillObjects.add(destroyBrick_up, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE] = ' ';
                }
            }

            if (stillObjects.get(destroyBrick_down) instanceof Brick) {
                stillObjects.remove(destroyBrick_down);
                if (map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] == 'f') {
                    stillObjects.add(destroyBrick_down, new FlameItem(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.powerup_flames.getFxImage()));
                } else if (map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] == 'x') {
                    stillObjects.add(destroyBrick_down, new Portal(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.portal.getFxImage()));
                } else {
                    stillObjects.add(destroyBrick_down, new Grass(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1, Sprite.grass.getFxImage()));
                    map[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE] = ' ';
                }
            }
            //enhanceExplosion








        } else if (bombFrame > 250) {
            resetBomb();
            flag = false;
        }

    }


    public boolean enhancedBombTouched(int xPos, int yPos) {

        if (xPos == x / 32 && yPos == y / 32) {
            return true;
        }
        if (explode1) {
            if (xPos < x / 32 + 2 && yPos == y / 32 && xPos >= x/32 -2) {
                return true;
            } else if (xPos == x/32 && yPos >= y/32 - 2 && yPos <= y/32 + 2 ) {
                return true;
            }
        }
        return false;

    }

}


