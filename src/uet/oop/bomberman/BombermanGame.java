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

import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingEntity.enemy.Balloon;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    int bombX;
    int bombY;
    private GraphicsContext gc;
    private Canvas canvas;

    public List<Entity> stillObjects = new ArrayList<>();
    public List<Enemy> balloon = new ArrayList<>();
    Bomber bomberman;
    int currentBomb = 0;

    Entity bomb = null;
    Map mapArray = new Map();
    public char[][] map = mapArray.getMap();
    public int getEntityPosInArray(int x,int y) {
        return (y *31 + x);
    }
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(992, 416);
        gc = canvas.getGraphicsContext2D();
        //Init
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        balloon.add(new Balloon(13, 1, Sprite.balloom_left1.getFxImage()));

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        //Bomber movement
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                if (bomberman.canMove(bomberman.xPos, bomberman.yPos - 1, map)) {
                    bomberman.yPos -= 1;
                    bomberman.up();
                }

            }
            if (key.getCode() == KeyCode.S) {
                if (bomberman.canMove(bomberman.xPos, bomberman.yPos + 1, map)) {
                    bomberman.yPos += 1;
                    bomberman.down();
                }
            }
            if (key.getCode() == KeyCode.D) {
                if (bomberman.canMove(bomberman.xPos + 1, bomberman.yPos, map)) {
                    bomberman.xPos += 1;
                    bomberman.right();
                }
            }
            if (key.getCode() == KeyCode.A) {
                if (bomberman.canMove(bomberman.xPos - 1, bomberman.yPos, map)) {
                    bomberman.xPos -= 1;
                    bomberman.left();
                }
            }
            if (key.getCode() == KeyCode.ENTER) {
                if (currentBomb == 0) {
                    bombX = bomberman.xPos;
                    bombY = bomberman.yPos;
                    bomb = new Bomb(bombX, bombY, Sprite.bomb.getFxImage());
                    ((Bomb) bomb).setStartExplode(true);
                    currentBomb++;
                }
            }
            checkInPortal();
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
                if (map[i][j] == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (map[i][j] == 'x') {
                    object = new Portal(j, i, Sprite.portal.getFxImage());
                } else if (map[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {

        for (Enemy enemy : balloon) {
            if (enemy.getAlive()) {
                enemy.enemyMovement(map);
            }
        }
        //explosion handle
        if (bomb != null) {
            if (((Bomb) bomb).getExplode()) {
                for (Enemy enemy : balloon) {
                    if (((Bomb) bomb).bombTouched(enemy.xPos, enemy.yPos)) {
                        enemy.setAlive(false);
                    }
                }

                if (((Bomb) bomb).bombTouched(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }

    }

    public void render() {
        gc.clearRect(0, 0, 992, 416);
        stillObjects.forEach(g -> g.render(gc));
        for (Enemy enemy : balloon) {
            if (enemy.getAlive()) {
                enemy.render(gc);
            }
        }
        if (bomb != null) {
            if (((Bomb) bomb).isStartExplode()) {
                ((Bomb) bomb).bombExplosion(gc, map, stillObjects);
            } else {
                bomb = null;
                currentBomb--;
            }
        }
        if (bomberman.isAlive()) {
            bomberman.render(gc);
        }
    }
    public void checkInPortal() {
        if(stillObjects.get(getEntityPosInArray(bomberman.xPos,bomberman.yPos)) instanceof Portal) {
            System.out.println("in Portal");
            int currentLocX = bomberman.xPos;
            int currentLocY = bomberman.yPos;
            int findPortalX = ((Portal)stillObjects.get(getEntityPosInArray(currentLocX,currentLocY))).stepOutX(map,currentLocX,currentLocY);
            int findPortalY = ((Portal)stillObjects.get(getEntityPosInArray(currentLocX,currentLocY))).stepOutY(map,currentLocX,currentLocY);
            bomberman.xPos = findPortalX;
            bomberman.yPos = findPortalY;
        }
    }

}