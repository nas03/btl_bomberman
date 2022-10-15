package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private GraphicsContext gc;
    boolean newGame = false;
    boolean test = false;
    boolean nextLevel = false, loadSavedGame = false;
    boolean permaLevel = true;
    int currentBomberX = 1, currentBomberY = 1;
    int frame = 0, currentBomb = 0;
    boolean mute = true;
    public List<Enemy> balloon = new ArrayList<>();
    public List<Enemy> minvo = new ArrayList<>();
    public List<Enemy> oneal = new ArrayList<>();
    public List<Enemy> kondoria = new ArrayList<>();
    public List<Enemy> doll = new ArrayList<>();
    Bomber bomberman;
    public List<Entity> stillObjects = new ArrayList<>();
    List<Entity> bombs = new ArrayList<>();
    Map board = new Map("src/main/resources/levels/Level1.txt");
    char[][] map = board.map;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sound.backgroundMusic.play();
        FXMLLoader loader = new FXMLLoader(BombermanGame.class.getResource("/uet/oop/bomberman/bomberman.fxml"));
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
        //saveMap();

        Platform.exit();
    }

    @FXML
    public void playGame() {
        System.out.println("playGame");
        game();
    }

    @FXML
    public void loadSavedGame() {
        loadSavedGame = true;
    }
    public void game() {
        Stage stage = new Stage();

        SceneController sceneController = new SceneController();
        VBox root = sceneController.prepare();
        Canvas cv = (Canvas) root.getChildren().get(1);
        gc = cv.getGraphicsContext2D();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if(loadSavedGame) {
            map = board.loadSavedMap();
            loadSavedEnemy();
            bomberman = new Bomber(2,1,Sprite.player_right_2.getFxImage());
            stillObjects = board.createMap(map);
            bomberman = board.loadBombermanFromTxt();
            loadSavedGame = false;
        }else {
            init();
        }
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W && bomberman.frame == 0) {
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
                    bombs.add(new Bomb(bombX, bombY, Sprite.bomb.getFxImage()));
                    for (Entity bomb : bombs) ((Bomb) bomb).setStartExplode(true);
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
                    sceneController.status = "";
                }
                if(permaLevel) getNextLevel();
                update();
                render();
                board.saveGame(map);
                board.saveMovingEntity(balloon,oneal,minvo,kondoria,doll,bomberman);

                //if(!test) test();
            }
        };

        timer.start();
    }


    public void update() {
        frame++;
        bomberman.checkInItem(stillObjects);
        if(bomberman.portal && nextLevel) {
            nextLevel();
            nextLevel = false;
            permaLevel = false;
        }
        bomberman.update();
        //balloon
        for (Enemy balloon : balloon) {
            if (balloon.getAlive()) {
                balloon.enemyMovement(stillObjects, map);
                if (balloon.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }

        //oneal
        for (Enemy oneal : oneal) {
            if (oneal.getAlive()) {
                if (frame == 400) {
                    currentBomberX = bomberman.xPos;
                    currentBomberY = bomberman.yPos;
                    frame = 0;
                    ((Oneal) oneal).locChange = true;
                }
                oneal.complexEnemyMovement(stillObjects, map, currentBomberX, currentBomberY);
                if (oneal.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
                ((Oneal) oneal).locChange = false;
            }
        }
        //minvo
        for (Enemy minvo : minvo) {
            if (minvo.getAlive()) {
                minvo.complexEnemyMovement(stillObjects, map, bomberman.xPos, bomberman.yPos);
                if (minvo.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
        }
        //kondoria
        for (Enemy kondoria : kondoria) {
            if (kondoria.getAlive()) {
                kondoria.update();
                kondoria.enemyMovement(stillObjects, map);
                if (kondoria.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
                if (((Kondoria) kondoria).isSkillReady()) {
                    Enemy s = (((Kondoria) kondoria).spawnBalloon(stillObjects));
                    if (s != null) {
                        balloon.add(s);
                    }
                    ((Kondoria) kondoria).setSkillReady(false);
                }
            }
        }
        //doll
        for (Enemy doll : doll) {
            if (doll.getAlive()) {
                doll.update();
                if (((Doll) doll).isSkillReady()) {
                    ((Doll) doll).teleport(stillObjects, bomberman.xPos, bomberman.yPos);
                    ((Doll) doll).setSkillReady(false);
                }
                doll.enemyMovement(stillObjects, map);
                if (doll.touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
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
        for (Enemy oneal : oneal)
            if (oneal.getAlive()) oneal.render(gc);
        for (Enemy kondoria : kondoria)
            if (kondoria.getAlive()) kondoria.render(gc);
        for (Enemy doll : doll)
            if (doll.getAlive()) doll.render(gc);
        for (Enemy minvo : minvo)
            if (minvo.getAlive()) minvo.render(gc);
        if (bomberman.isAlive()) bomberman.render(gc);


        if (!bombs.isEmpty()) {
            for (int i = 0; i < bombs.size(); i++) {
                if (((Bomb) bombs.get(i)).isStartExplode()) {
                    if (bomberman.getEnhancedFlame()) {
                        ((Bomb) bombs.get(i)).enhancedBombExplosion(gc, map, stillObjects);
                    } else {
                        ((Bomb) bombs.get(i)).bombExplosion(gc, map, stillObjects);
                    }

                } else {
                    currentBomb -= 1;
                    bombs.remove(bombs.get(i));
                    i--;
                }
            }
        }
    }


    public void init() {
        map = board.map;
        stillObjects = board.createMap(map);
        minvo = board.getMinvo();
        balloon = board.getBalloon();
        doll = board.getDoll();
        kondoria = board.getKondoria();
        oneal = board.getOneal();
        bomberman = board.getBomberman();
    }

    public void checkExplosion() {
        if (!bombs.isEmpty()) {

            for (Entity bomb : bombs) {
                if (((Bomb) bomb).getExplode()) {
                    map = ((Bomb) bomb).getReturnedMap();
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
                        for (Enemy minvo : minvo) {
                            if (((Bomb) bomb).enhancedBombTouched(minvo.xPos, minvo.yPos)) {
                                minvo.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Kondoria
                        for (Enemy kondoria : kondoria) {
                            if (((Bomb) bomb).enhancedBombTouched(kondoria.xPos, kondoria.yPos)) {
                                kondoria.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Doll
                        for (Enemy doll : doll) {
                            if (((Bomb) bomb).enhancedBombTouched(doll.xPos, doll.yPos)) {
                                doll.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Oneal
                        for (Enemy oneal : oneal) {
                            if (((Bomb) bomb).enhancedBombTouched(oneal.xPos, oneal.yPos)) {
                                oneal.setAlive(false);
                                Sound.enemyDie.play();
                            }
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
                        for (Enemy minvo : minvo) {
                            if (((Bomb) bomb).bombTouched(minvo.xPos, minvo.yPos)) {
                                minvo.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Kondoria
                        for (Enemy kondoria : kondoria) {
                            if (((Bomb) bomb).bombTouched(kondoria.xPos, kondoria.yPos)) {
                                kondoria.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Doll
                        for (Enemy doll : doll) {
                            if (((Bomb) bomb).bombTouched(doll.xPos, doll.yPos)) {
                                doll.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Oneal
                        for (Enemy oneal : oneal) {
                            if (((Bomb) bomb).bombTouched(oneal.xPos, oneal.yPos)) {
                                oneal.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                    }

                }
            }
        }
    }

    public void getNextLevel() {
        for (Enemy balloon : balloon) {
            if(balloon.getAlive()) {
                return;
            }
        }
        for (Enemy oneal : oneal) {
            if(oneal.getAlive()) {
                return;
            }
        }
        nextLevel = true;
    }
    public void nextLevel() {
        Map level2 = new Map("src/main/resources/levels/Level2.txt");
        map = level2.map;
        stillObjects =  level2.createMap(level2.map);
        bomberman = level2.getBomberman();
        kondoria = level2.getKondoria();
        doll = level2.getDoll();
        minvo = level2.getMinvo();
    }
    public void test() {
        for (Enemy enemy : balloon) {
            enemy.setAlive(false);
        }

        //oneal
        for (Enemy oneal : oneal) {
            oneal.setAlive(false);
        }
        test = true;
    }
    public void loadSavedEnemy() {
        Enemy balloon1 = new Balloon(1,1,Sprite.player_up_2.getFxImage());
        Enemy doll1 = new Doll(1,1,Sprite.oneal_right1.getFxImage());
        Enemy kondoria1 = new Kondoria(1,1,Sprite.player_up_2.getFxImage());
        Enemy minvo1 = new Minvo(1,1,Sprite.player_left_2.getFxImage());
        Enemy oneal1 = new Oneal(1,1,Sprite.oneal_dead.getFxImage());
        balloon = board.loadEnemyFromTxt("src/main/resources/game_progress/balloon.txt",balloon1);
        oneal = board.loadEnemyFromTxt("src/main/resources/game_progress/oneal.txt",oneal1);
        if(nextLevel) {
            doll = board.loadEnemyFromTxt("src/main/resources/game_progress/balloon.txt", doll1);
            kondoria = board.loadEnemyFromTxt("src/main/resources/game_progress/kondoria.txt", kondoria1);
            minvo = board.loadEnemyFromTxt("src/main/resources/game_progress/minvo.txt", minvo1);
            bomberman = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
        }
    }
}