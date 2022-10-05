package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;

import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    int frame = 0;
    int bombFrame = 0;
    public int bomberXPos = 1;
    public int bomberYPos = 1;
    public int balloon1XPos = 13;
    public int balloon1YPoS = 1;

    boolean balloon1IsAlive = true;
    int bombX;
    int bombY;
    boolean explode = false;
    private GraphicsContext gc;
    private Canvas canvas;
    Entity bomberman;
    boolean collideWithWall = false;

    private List<Entity> bomberMovement = new ArrayList<>();
    public List<Entity> stillObjects = new ArrayList<>();

    private List<Entity> balloonMovement = new ArrayList<>();
    int currentBomb = 0;
    private List<Entity> bombExplode = new ArrayList<>();
    Entity balloon1;
    Entity bomb;
    Map mapArray = new Map();
    public char[][] map = mapArray.getMap();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(992, 416);
        gc = canvas.getGraphicsContext2D();
        //Bomberman
        bomberman = new Bomber(bomberXPos,bomberYPos,Sprite.player_right.getFxImage());
        bomberMovement.add(bomberman);
        //Balloon
        balloon1 = new Balloon(balloon1XPos,balloon1YPoS,Sprite.balloom_left1.getFxImage());
        balloonMovement.add(balloon1);


        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        //Bomber movement
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.W) {
                if (canMove(bomberXPos,bomberYPos-1)) {
                    bomberYPos -= 1;
                    bomberman = new Bomber(bomberXPos,bomberYPos,Sprite.player_up.getFxImage());
                }

            }
            if(key.getCode()== KeyCode.S) {
                if (canMove(bomberXPos,bomberYPos + 1)) {
                    bomberYPos += 1;
                    bomberman = new Bomber(bomberXPos,bomberYPos,Sprite.player_down.getFxImage());
                }
            }
            if (key.getCode() == KeyCode.D) {

                if (canMove(bomberXPos + 1,bomberYPos) ) {
                    bomberXPos += 1;
                    bomberman = new Bomber(bomberXPos,bomberYPos,Sprite.player_right.getFxImage());
                }
            }
            if (key.getCode() == KeyCode.A) {
                if (canMove(bomberXPos - 1, bomberYPos)) {
                    bomberXPos -= 1;
                    bomberman = new Bomber(bomberXPos,bomberYPos,Sprite.player_left.getFxImage());
                }
            }
            if(key.getCode() == KeyCode.ENTER) {
                if (currentBomb == 0) {
                    bombX = bomberXPos;
                    bombY = bomberYPos;
                    bomb = new Bomb(bombX,bombY,Sprite.bomb.getFxImage());
                    explode = true;
                    bombExplode.add(bomb);
                    currentBomb++;
                }
            }
            bomberMovement.clear();
            bomberMovement.add(bomberman);
        });

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        timer.start();
        createMap();
    }

    public void createMap() {

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (map[i][j] =='#') {
                    object = new Wall(j,i,Sprite.wall.getFxImage());
                }
                else if (map[i][j]=='x') {
                    object = new Portal(j,i,Sprite.portal.getFxImage());
                }

                else if (map[i][j] == '*') {
                    object = new Brick(j,i,Sprite.brick.getFxImage());
                }

                else {
                    object = new Grass(j,i,Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
      if (frame == 500) {
          frame = 0;
      }
      frame++;
      if(((Enemy)balloon1).getAlive()) {
          balloon1Movement();
      }
      if(explode) {
          bombExplosion(bombX,bombY);
      }
    }

    public void render() {
        gc.clearRect(0, 0, 992, 416);
        stillObjects.forEach(g -> g.render(gc));
        bomberMovement.forEach(g -> g.render(gc));
        if(((Enemy)balloon1).getAlive()) {
            balloonMovement.forEach(g -> g.render(gc));
        }
        if (explode) {
                bombExplode.forEach(g -> g.render(gc));
        }

    }
    public boolean canMove(int x, int y) {

        if (map[y][x] == '#' || map[y][x] == '*') {
            return false;
        }
        if (x-1 < 0 || x + 1 > WIDTH || y -1 < 0 || y + 1 > HEIGHT) {
            return false;
        }
        return true;
    }
    void balloon1Movement() {

        if (frame % 50 == 0) {
            if (canMove(balloon1XPos - 1, balloon1YPoS ) && !collideWithWall) {
                balloon1XPos -= 1;
                balloon1 = new Balloon(balloon1XPos, balloon1YPoS, Sprite.balloom_left1.getFxImage());
            } else if (!canMove(balloon1XPos - 1, balloon1YPoS )){
                collideWithWall = true;
            }
            if (canMove(balloon1XPos + 1, balloon1YPoS) && collideWithWall) {
                balloon1XPos += 1;
                balloon1 = new Balloon(balloon1XPos, balloon1YPoS, Sprite.balloom_right1.getFxImage());
            }else if (!canMove(balloon1XPos + 1, balloon1YPoS)) {
                collideWithWall = false;
            }
            balloonMovement.clear();
            balloonMovement.add(balloon1);

        }
    }
    void bombExplosion(int x, int y) {
        bombFrame++;
        if (bombFrame == 50) {
            bomb = new Bomb(x,y,Sprite.bomb_exploded.getFxImage());
            bombExplode.clear();
            bombExplode.add(bomb);
        } else if(bombFrame == 100) {
            bomb = new Bomb(x,y,Sprite.bomb_exploded1.getFxImage());
            bombExplode.clear();
            bombExplode.add(bomb);
        }else if (bombFrame == 150) {
            bomb = new Bomb(x,y,Sprite.bomb_exploded2.getFxImage());
            bombExplode.clear();
            bombExplode.add(bomb);
            bombExplode.add(new Bomb(x+1,y,Sprite.explosion_horizontal_right_last.getFxImage()));
            bombExplode.add(new Bomb(x-1,y,Sprite.explosion_horizontal_left_last.getFxImage()));
            bombExplode.add(new Bomb(x,y+1,Sprite.explosion_vertical_down_last.getFxImage()));
            bombExplode.add(new Bomb(x,y-1,Sprite.explosion_vertical_top_last.getFxImage()));
            int destroyBrick = y*31 + x -1;
            int destroyBrick1 = y*31 + x + 1;
            int destroyBrick2 = (y-1)*31 + x ;
            int destroyBrick3 = (y+1)*31 + x;
            if (stillObjects.get(destroyBrick) instanceof Brick) {
                stillObjects.remove(destroyBrick);
                stillObjects.add(destroyBrick,new Grass(x-1,y,Sprite.grass.getFxImage()));
                map[y][x-1] = ' ';

            }
            if (stillObjects.get(destroyBrick1) instanceof Brick) {
                stillObjects.remove(destroyBrick1);
                stillObjects.add(destroyBrick1,new Grass(x+1,y,Sprite.grass.getFxImage()));
                map[y][x+1] = ' ';
            }
            if (stillObjects.get(destroyBrick2) instanceof Brick) {
                stillObjects.remove(destroyBrick2);
                stillObjects.add(destroyBrick2,new Grass(x,y-1,Sprite.grass.getFxImage()));
                map[y-1][x] = ' ';
            }
            if (stillObjects.get(destroyBrick3) instanceof Brick) {
                stillObjects.remove(destroyBrick3);
                stillObjects.add(destroyBrick3,new Grass(x,y+1,Sprite.grass.getFxImage()));
                map[y+1][x] = ' ';
            }

            if(balloon1XPos == x+1 && balloon1YPoS == y) {
                ((Enemy)balloon1).setAlive(false);
            }
            else if(balloon1XPos == x-1 && balloon1YPoS == y) {
                ((Enemy)balloon1).setAlive(false);
            }

            else if(balloon1XPos == x && balloon1YPoS == y+1) {
                ((Enemy)balloon1).setAlive(false);
            }

            else if(balloon1XPos == x+1 && balloon1YPoS == y-1) {
                ((Enemy)balloon1).setAlive(false);
            }
            else if(balloon1XPos == x && balloon1YPoS == y) {
                ((Enemy)balloon1).setAlive(false);
            }
            if (!((Enemy)balloon1).getAlive()) {
                balloonMovement.clear();
            }

        }else if (bombFrame == 200) {
            explode = false;
            bombExplode.clear();
            currentBomb = 0;
            bombFrame = 0;
        }
    }

}