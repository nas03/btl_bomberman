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
import uet.oop.bomberman.entities.movingEntity.*;

import uet.oop.bomberman.entities.movingEntity.enemy.*;

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
    public int currentBomberX = 1;
    public int currentBomberY = 1;
    public int frame = 0;
    public List<Entity> stillObjects = new ArrayList<>();
    public List<Enemy> balloon = new ArrayList<>();
    Bomber bomberman;
    Enemy oneal;
    int currentBomb = 0;
    int bombLimit = 10;

    Entity bomb = null;
    Map mapArray = new Map();
    public char[][] map = mapArray.getMap();
    public int getEntityPosInArray(int x,int y) {
        return y*31 + x;
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
        balloon.add(new Balloon(18, 3, Sprite.balloom_left1.getFxImage()));
        balloon.add(new Balloon(24, 5, Sprite.balloom_left1.getFxImage()));
        oneal = new Oneal(25,5,Sprite.oneal_right1.getFxImage());
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        //Bomber movement
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                if (bomberman.canMove(stillObjects,bomberman.xPos, bomberman.yPos - bomberman.getSpeed())) {
                    bomberman.yPos -= bomberman.getSpeed();
                    bomberman.up();
                }
                else if (bomberman.canMove(stillObjects,bomberman.xPos, bomberman.yPos - bomberman.getSpeed()+1)) {
                    bomberman.yPos -= bomberman.getSpeed() - 1;
                    bomberman.up();
                }

            }
            if (key.getCode() == KeyCode.S) {
                if (bomberman.canMove(stillObjects,bomberman.xPos, bomberman.yPos + bomberman.getSpeed())) {
                    bomberman.yPos += bomberman.getSpeed();
                    bomberman.down();
                }
                else if (bomberman.canMove(stillObjects,bomberman.xPos, bomberman.yPos + bomberman.getSpeed() - 1)) {
                    bomberman.yPos += bomberman.getSpeed() - 1;
                    bomberman.down();
                }
            }
            if (key.getCode() == KeyCode.D) {
                if (bomberman.canMove(stillObjects,bomberman.xPos + bomberman.getSpeed(), bomberman.yPos)) {
                    bomberman.xPos += bomberman.getSpeed();
                    bomberman.right();
                } else if (bomberman.canMove(stillObjects,bomberman.xPos + bomberman.getSpeed() - 1, bomberman.yPos)) {
                    bomberman.xPos += bomberman.getSpeed() - 1;
                    bomberman.right();
                }
            }
            if (key.getCode() == KeyCode.A) {
                if (bomberman.canMove(stillObjects,bomberman.xPos - bomberman.getSpeed(), bomberman.yPos)) {
                    bomberman.xPos -= bomberman.getSpeed();
                    bomberman.left();
                } else if (bomberman.canMove(stillObjects,bomberman.xPos - bomberman.getSpeed() + 1, bomberman.yPos)) {
                    bomberman.xPos -= bomberman.getSpeed() - 1;
                    bomberman.left();
                }
            }
            if (key.getCode() == KeyCode.ENTER) {
                if (currentBomb < bombLimit) {
                    bombX = bomberman.xPos;
                    bombY = bomberman.yPos;
                    bomb = new Bomb(bombX, bombY, Sprite.bomb.getFxImage());
                    ((Bomb) bomb).setStartExplode(true);
                    currentBomb++;
                }
            }
            checkInPortal();
            checkInItem();
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
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else if (map[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else if (map[i][j] == 'f') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else if(map[i][j] == 's') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                }else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        frame++;
        System.out.println(frame);
        if(bomberman.isSpeedUp()) {
            bomberman.speedUp();
        }
        for (Enemy enemy : balloon) {
            if (enemy.getAlive()) {
                enemy.enemyMovement(stillObjects,map);
                if(enemy.touchBomber(bomberman.xPos,bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }
        /*if (((Oneal)oneal).solved){
            char[][] constMap = map;
            oneal.complexEnemyMovement(stillObjects,constMap,1, 1);
        }else {*/
        if(frame == 400) {
            currentBomberX = bomberman.xPos;
            currentBomberY = bomberman.yPos;
            frame = 0;
            ((Oneal)oneal).locChange = true;
        }
        oneal.complexEnemyMovement(stillObjects,map,currentBomberX, currentBomberY);
        ((Oneal)oneal).locChange = false;


        //}
        //explosion handle
        if (bomb != null) {

            if (((Bomb) bomb).getExplode()) {
                for (Enemy enemy : balloon) {
                    if(bomberman.getEnhancedFlame()) {
                        if (((Bomb) bomb).enhancedBombTouched(enemy.xPos, enemy.yPos)) {
                            enemy.setAlive(false);
                        }
                    }
                    else if (((Bomb) bomb).bombTouched(enemy.xPos, enemy.yPos)) {
                        enemy.setAlive(false);
                    }
                }
                if(bomberman.getEnhancedFlame()) {
                    if (((Bomb) bomb).enhancedBombTouched(bomberman.xPos, bomberman.yPos)) {
                        bomberman.setAlive(false);
                    }
                }else if (((Bomb) bomb).bombTouched(bomberman.xPos, bomberman.yPos)) {
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
        oneal.render(gc);
        if (bomb != null) {
            if (((Bomb) bomb).isStartExplode()) {
                if(bomberman.getEnhancedFlame()) {
                    ((Bomb) bomb).enhancedBombExplosion(gc, map, stillObjects);
                }
                else {
                    ((Bomb) bomb).bombExplosion(gc, map, stillObjects);
                }
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

    }
    public void checkInItem(){
        int currentLocX = bomberman.xPos;
        int currentLocY = bomberman.yPos;
        if(stillObjects.get(getEntityPosInArray(currentLocX,currentLocY)) instanceof FlameItem) {
            System.out.println("In FlameItem");
            bomberman.setEnhancedFlame(true);
            stillObjects.remove(getEntityPosInArray(currentLocX,currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX,currentLocY), new Grass(currentLocX,currentLocY,Sprite.grass.getFxImage()));

        }
        else if(stillObjects.get(getEntityPosInArray(currentLocX,currentLocY)) instanceof BombItem) {
            System.out.println("In BombItem");
            bombLimit++;
            stillObjects.remove(getEntityPosInArray(currentLocX,currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX,currentLocY), new Grass(currentLocX,currentLocY,Sprite.grass.getFxImage()));
        }
        else if(stillObjects.get(getEntityPosInArray(currentLocX,currentLocY)) instanceof SpeedItem) {
            System.out.println("In SpeedItem");
            bomberman.setSpeedUp(true);
            stillObjects.remove(getEntityPosInArray(currentLocX,currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX,currentLocY), new Grass(currentLocX,currentLocY,Sprite.grass.getFxImage()));
        }else if(stillObjects.get(getEntityPosInArray(currentLocX,currentLocY)) instanceof Portal) {
            System.out.println("in Portal");
            boolean flag = true;
            for(Enemy balloon: balloon) {
                if(balloon.getAlive()) {
                    flag = false;
                }
            }
            if(flag) {
                System.out.printf("Next Level");
            }

        }

    }

}