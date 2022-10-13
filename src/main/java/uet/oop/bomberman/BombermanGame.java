package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.plaf.SliderUI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    boolean newGame = false;
    private GraphicsContext gc;
    int currentBomberX = 1, currentBomberY = 1;
    int frame = 0, currentBomb = 0;
    public List<Entity> stillObjects = new ArrayList<>();
    public List<Enemy> balloon = new ArrayList<>();
    Bomber bomberman;
    Enemy oneal, minvo, kondoria, doll;

    Entity bomb = null;
    Map mapArray = new Map();
    public char[][] map = mapArray.getMap();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sound.backgroundMusic.play();
        FXMLLoader loader = new FXMLLoader(BombermanGame.class.getResource("/uet/oop/bomberman/bomberman.fxml"));
        // Them scene vao stage
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void howToPlay() {
        System.out.println("how to play");
    }

    @FXML
    public void quit() {
        System.out.println("quit");
        Platform.exit();
    }

    @FXML
    public void playGame() {
        System.out.println("playGame");
        game();
    }

    public void game() {
        Stage stage = new Stage();
        // Tao root container
        SceneController sceneController = new SceneController();
        // Them scene vao stage
        VBox root = sceneController.prepare();
        Canvas cv = (Canvas) root.getChildren().get(1);
        gc = cv.getGraphicsContext2D();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        init();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W && bomberman.frame == 0) {
                System.out.println('W');
                if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos - 1)) {
                    bomberman.yPos -= 1;
                    bomberman.up();
                }
            }

            if (key.getCode() == KeyCode.S && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos, bomberman.yPos + 1)) {
                    bomberman.yPos += 1;
                    bomberman.down();
                }
            }
            if (key.getCode() == KeyCode.D && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos + 1, bomberman.yPos)) {
                    bomberman.xPos += 1;
                    bomberman.right();
                }
            }
            if (key.getCode() == KeyCode.A && bomberman.frame == 0) {
                if (bomberman.canMove(stillObjects, bomberman.xPos - 1, bomberman.yPos)) {
                    bomberman.xPos -= 1;
                    bomberman.left();
                }
            }
            if (key.getCode() == KeyCode.ENTER) {
                if (currentBomb < bomberman.bombLimit) {
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
        bomberman.checkInItem(stillObjects);
        bomberman.update();
        //balloon
        for (Enemy enemy : balloon) {
            if (enemy.getAlive()) {
                enemy.enemyMovement(stillObjects, map);
                if (enemy.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }

        //oneal
        if (oneal.getAlive()) {
            if (frame == 400) {
                currentBomberX = bomberman.xPos;
                currentBomberY = bomberman.yPos;
                frame = 0;
                ((Oneal) oneal).locChange = true;
            }
            oneal.complexEnemyMovement(stillObjects, map, currentBomberX, currentBomberY);
            ((Oneal) oneal).locChange = false;
        }
        //minvo
        if (minvo.getAlive()) {
            minvo.complexEnemyMovement(stillObjects, map, bomberman.xPos, bomberman.yPos);
        }
        //kondoria
        if (kondoria.getAlive()) {
            kondoria.update();
            kondoria.enemyMovement(stillObjects, map);
            if (((Kondoria) kondoria).isSkillReady()) {
                Enemy s = (((Kondoria) kondoria).spawnBalloon(stillObjects));
                if (s != null) {
                    balloon.add(s);
                }
                ((Kondoria) kondoria).setSkillReady(false);
            }
        }
        //doll
        if (doll.getAlive()) {
            doll.update();
            if (((Doll) doll).isSkillReady()) {
                ((Doll) doll).teleport(stillObjects, bomberman.xPos, bomberman.yPos);
                ((Doll) doll).setSkillReady(false);
            }
            doll.enemyMovement(stillObjects, map);
        }

        //}
        //explosion handle
        checkExplosion();
    }

    public void render() {
        gc.clearRect(0, 0, 992, 416);

        stillObjects.forEach(g -> g.render(gc));

        for (Enemy enemy : balloon) {
            if (enemy.getAlive()) {
                enemy.render(gc);
            }
        }
        if (oneal.getAlive()) oneal.render(gc);

        if (kondoria.getAlive()) kondoria.render(gc);

        if (doll.getAlive()) doll.render(gc);
        if (minvo.getAlive()) minvo.render(gc);
        if (bomberman.isAlive()) bomberman.render(gc);


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
        doll = new Doll(2, 1, Sprite.doll_left1.getFxImage());
    }

    public void checkExplosion() {

        if (bomb != null) {
          Sound.explosion.play();
            if (((Bomb) bomb).getExplode()) {
                if (bomberman.getEnhancedFlame()) {
                    //balloon
                    for (Enemy enemy : balloon) {
                        if (((Bomb) bomb).enhancedBombTouched(enemy.xPos, enemy.yPos)) {
                            enemy.setAlive(false);
                            Sound.enemyDie.play();
                        }
                    }
                    //bomberman
                    if (((Bomb) bomb).enhancedBombTouched(bomberman.xPos, bomberman.yPos)) {
                        bomberman.setAlive(false);
                        Sound.bomber_die.play();
                    }
                    //minvo
                    if (((Bomb) bomb).enhancedBombTouched(minvo.xPos, minvo.yPos)) {
                        minvo.setAlive(false);
                        Sound.enemyDie.play();
                    }
                    //Kondoria
                    if (((Bomb) bomb).enhancedBombTouched(kondoria.xPos, kondoria.yPos)) {
                        kondoria.setAlive(false);
                        Sound.enemyDie.play();
                    }
                    //Doll
                    if (((Bomb) bomb).enhancedBombTouched(doll.xPos, doll.yPos)) {
                        doll.setAlive(false);
                        Sound.enemyDie.play();
                    }
                } else {
                    for (Enemy enemy : balloon) {
                        if (((Bomb) bomb).bombTouched(enemy.xPos, enemy.yPos)) {
                            enemy.setAlive(false);
                            Sound.enemyDie.play();
                        }
                    }
                    //bomberman
                    if (((Bomb) bomb).bombTouched(bomberman.xPos, bomberman.yPos)) {
                        bomberman.setAlive(false);
                        Sound.bomber_die.play();
                    }
                    //minvo
                    if (((Bomb) bomb).bombTouched(minvo.xPos, minvo.yPos)) {
                        minvo.setAlive(false);
                        Sound.enemyDie.play();
                    }
                    //Kondoria
                    if (((Bomb) bomb).bombTouched(kondoria.xPos, kondoria.yPos)) {
                        kondoria.setAlive(false);
                        Sound.enemyDie.play();
                    }
                    //Doll
                    if (((Bomb) bomb).bombTouched(doll.xPos, doll.yPos)) {
                        doll.setAlive(false);
                        Sound.enemyDie.play();
                    }
                }

            }
        }
    }

}