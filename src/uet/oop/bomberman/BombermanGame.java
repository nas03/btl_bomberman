package uet.oop.bomberman;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.movingEntity.*;
import uet.oop.bomberman.entities.movingEntity.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    boolean newGame = false;
    private GraphicsContext gc;
    private Canvas canvas;
    int currentBomberX = 1, currentBomberY = 1;
    int frame = 0;
    public List<Entity> stillObjects = new ArrayList<>();
    public List<Enemy> balloon = new ArrayList<>();
    Bomber bomberman;
    Enemy oneal;
    Enemy minvo;
    Enemy kondoria;
    Enemy doll;
    int currentBomb = 0, bombLimit = 2;


    Entity bomb = null;
    Map mapArray = new Map();
    public char[][] map = mapArray.getMap();

    public int getEntityPosInArray(int x, int y) {
        return y * 31 + x;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        //Sound.backgroundMusic.play();
        // Tao root container
        SceneController sceneController = new SceneController();
        VBox root = sceneController.prepare();
        Canvas cv = (Canvas) root.getChildren().get(1);
        gc = cv.getGraphicsContext2D();
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        init();

        //Bomber movement
        /*scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos - 1)) {
                        bomberman.pressW = true;
                    } else {
                        bomberman.pressW = false;
                    }
                    break;
                case S:
                    if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos + 1)) {
                        bomberman.pressS = true;
                    } else bomberman.pressS = false;
                    break;
                case A:
                    if (bomberman.canMove(stillObjects, bomberman.xPos - 1, bomberman.yPos)) {
                        bomberman.pressA = true;
                    } else bomberman.pressA = false;
                    break;
                case D:
                    if (bomberman.canMove(stillObjects, bomberman.xPos + 1, bomberman.yPos)) {
                        bomberman.pressD = true;
                    } else bomberman.pressD = false;
                    break;
                case ENTER:
                    bomberman.pressD = false;
                    bomberman.pressW = false;
                    bomberman.pressS = false;
                    bomberman.pressA = false;
                    if (currentBomb < bombLimit) {
                        int bombX = bomberman.xPos;
                        int bombY = bomberman.yPos;
                        bomb = new Bomb(bombX, bombY, Sprite.bomb.getFxImage());
                        ((Bomb) bomb).setStartExplode(true);
                        currentBomb++;
                    }
                    break;
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    bomberman.pressW = false;
                    break;
                case S:
                    bomberman.pressS = false;
                    break;
                case A:
                    bomberman.pressA = false;
                    break;
                case D:
                    bomberman.pressD = false;
                    break;

            }
        });
*/
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W && bomberman.frame == 0) {
                System.out.println('W');
                if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos - 1)) {
                    bomberman.yPos -= 1;
                    bomberman.up();
                }
            }
               /* } else if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos - bomberman.getSpeed() + 1)) {
                    bomberman.yPos -= bomberman.getSpeed() - 1;
                    bomberman.up();
                }*/
            if (key.getCode() == KeyCode.S && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos + 1)) {
                    bomberman.yPos += 1;
                    bomberman.down();
                }
                   /* } else if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos + bomberman.getSpeed() - 1)) {
                        bomberman.yPos += bomberman.getSpeed() - 1;
                        bomberman.down();
                    }*/
            }
            if (key.getCode() == KeyCode.D && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos + 1, bomberman.yPos)) {
                    bomberman.xPos += 1;
                    bomberman.right();
                }
                   /* } else if (bomberman.canMove(stillObjects, bomberman.xPos + bomberman.getSpeed() - 1, bomberman.yPos)) {
                        bomberman.xPos += bomberman.getSpeed() - 1;
                        bomberman.right();
                    }*/
            }
            if (key.getCode() == KeyCode.A && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos - 1, bomberman.yPos)) {
                    bomberman.xPos -= 1;
                    bomberman.left();
                }
                    /*} else if (bomberman.canMove(stillObjects, bomberman.xPos - bomberman.getSpeed() + 1, bomberman.yPos)) {
                        bomberman.xPos -= bomberman.getSpeed() - 1;
                        bomberman.left();
                    }*/
            }
            if (key.getCode() == KeyCode.ENTER) {
                if (currentBomb < bombLimit) {
                    int bombX = bomberman.xPos;
                    int bombY = bomberman.yPos;
                    bomb = new Bomb(bombX, bombY, Sprite.bomb.getFxImage());
                    ((Bomb) bomb).setStartExplode(true);
                    currentBomb++;
                }
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (sceneController.status.equals("close")) {
                    stage.close();
                } else if (sceneController.status.equals("new-game")) {
                    newGame = true;
                    init();
                    sceneController.status = "";
                }
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
                } else if (map[i][j] == 's') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        frame++;
        checkInItem();
        bomberman.update();

        for (Enemy enemy : balloon) {

            if (enemy.getAlive()) {
                enemy.enemyMovement(stillObjects, map);
                if (enemy.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }

        if (frame == 400) {
            currentBomberX = bomberman.xPos;
            currentBomberY = bomberman.yPos;
            frame = 0;
            ((Oneal) oneal).locChange = true;
        }
        oneal.complexEnemyMovement(stillObjects, map, currentBomberX, currentBomberY);
        ((Oneal) oneal).locChange = false;

        minvo.complexEnemyMovement(stillObjects, map, bomberman.xPos, bomberman.yPos);
        kondoria.update();
        kondoria.enemyMovement(stillObjects, map);
        if (((Kondoria) kondoria).isSkillReady()) {
            Enemy s = (((Kondoria)kondoria).spawnBalloon(stillObjects));
            if(s != null) {
                balloon.add(s);
            }
            ((Kondoria)kondoria).setSkillReady(false);
        }
        doll.update();

        if(((Doll)doll).isSkillReady()) {
            ((Doll)doll).teleport(stillObjects,bomberman.xPos,bomberman.yPos);
            ((Doll)doll).setSkillReady(false);
        }
        doll.enemyMovement(stillObjects,map);



            //}
            //explosion handle
            if (bomb != null) {
                Sound.exploision.play();
                if (((Bomb) bomb).getExplode()) {
                    for (Enemy enemy : balloon) {
                        if (bomberman.getEnhancedFlame()) {
                            if (((Bomb) bomb).enhancedBombTouched(enemy.xPos, enemy.yPos)) {
                                enemy.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        } else if (((Bomb) bomb).bombTouched(enemy.xPos, enemy.yPos)) {
                            enemy.setAlive(false);
                            Sound.enemyDie.play();
                        }
                    }
                    if (bomberman.getEnhancedFlame()) {
                        if (((Bomb) bomb).enhancedBombTouched(bomberman.xPos, bomberman.yPos)) {
                            bomberman.setAlive(false);
                            Sound.bomber_die.play();
                        }
                    } else if (((Bomb) bomb).bombTouched(bomberman.xPos, bomberman.yPos)) {
                        bomberman.setAlive(false);
                        Sound.bomber_die.play();
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
        kondoria.render(gc);
        doll.render(gc);
        if (bomb != null) {
            if (((Bomb) bomb).isStartExplode()) {
                if (bomberman.getEnhancedFlame()) {
                    ((Bomb) bomb).enhancedBombExplosion(gc, map, stillObjects);
                } else {
                    ((Bomb) bomb).bombExplosion(gc, map, stillObjects);
                }
            } else {
                bomb = null;
                currentBomb--;
            }
        }
        minvo.render(gc);
        if (bomberman.isAlive()) {
            bomberman.render(gc);
        }
    }

    public void checkInItem() {
        int currentLocX = bomberman.xPos;
        int currentLocY = bomberman.yPos;
        if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof FlameItem) {
            System.out.println("In FlameItem");
            bomberman.setEnhancedFlame(true);
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));

        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof BombItem) {
            System.out.println("In BombItem");
            bombLimit++;
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));
        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof SpeedItem) {
            System.out.println("In SpeedItem");
            bomberman.speedUp();
            stillObjects.remove(getEntityPosInArray(currentLocX, currentLocY));
            stillObjects.add(getEntityPosInArray(currentLocX, currentLocY), new Grass(currentLocX, currentLocY, Sprite.grass.getFxImage()));
        } else if (stillObjects.get(getEntityPosInArray(currentLocX, currentLocY)) instanceof Portal) {
            System.out.println("in Portal");
            boolean flag = true;
            for (Enemy balloon : balloon) {
                if (balloon.getAlive()) {
                    flag = false;
                }
            }
            if (flag) {
                System.out.printf("Next Level");
            }
        }
    }

    public void init() {
        newGame = false;
        bomberman = null;
        balloon.removeAll(balloon);
        oneal = null;
        minvo = null;
        kondoria = null;
        doll = null;

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        balloon.add(new Balloon(13, 1, Sprite.balloom_left1.getFxImage()));
        balloon.add(new Balloon(18, 3, Sprite.balloom_left1.getFxImage()));
        balloon.add(new Balloon(24, 5, Sprite.balloom_left1.getFxImage()));
        oneal = new Oneal(25, 5, Sprite.oneal_right1.getFxImage());
        minvo = new Minvo(1, 3, Sprite.minvo_right2.getFxImage());
        kondoria = new Kondoria(1, 4, Sprite.kondoria_right1.getFxImage());
        doll = new Doll(2,1,Sprite.doll_left1.getFxImage());
    }

}