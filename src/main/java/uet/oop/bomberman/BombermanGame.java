package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.moving_entities.Bomber;
import uet.oop.bomberman.moving_entities.enemy.*;
import uet.oop.bomberman.ultilities.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public List<Enemy> balloons = new ArrayList<>();
    public List<Enemy> minvos = new ArrayList<>();
    public List<Enemy> oneals = new ArrayList<>();
    public List<Enemy> kondorias = new ArrayList<>();
    public List<Enemy> dolls = new ArrayList<>();
    public List<Entity> stillObjects = new ArrayList<>();
    boolean newGame = false;
    boolean played = false;
    boolean gameOver = false, winGame = false, checkWin = true;
    boolean test = false;
    boolean nextLevel = false, loadSavedGame = false;
    boolean permaLevel = true;
    int currentBomberX = 1, currentBomberY = 1;
    int frame = 0, currentBomb = 0;
    boolean mute = true;
    Bomber bomberman;
    List<Entity> bombs = new ArrayList<>();
    Map board = new Map("src/main/resources/levels/Level1.txt");
    char[][] map = board.getMap();
    Stage parentStage = new Stage();
    Stage stage = new Stage();
    private GraphicsContext gc;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Sound.backgroundMusic.loopPlay();

        FXMLLoader loader = new FXMLLoader(BombermanGame.class.getResource("/uet/oop/bomberman/bomberman.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void howToPlay() {
        Image img = new Image("guide.png");
        ImageView view = new ImageView();
        view.setImage(img);
        VBox root = new VBox(view);
        Scene scene = new Scene(root, 992, 416);
        Stage guide = new Stage();
        guide.setTitle("How To Play");
        guide.setScene(scene);
        guide.show();
    }


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
        if (played) game();
    }

    @FXML

    public void saveGame() {
        board.saveGame(map);
        board.saveMovingEntity(balloons, oneals, minvos, kondorias, dolls, bomberman);
    }

    public void gameOver() {
        if (gameOver) {
            FXMLLoader loader = new FXMLLoader(BombermanGame.class.getResource("/uet/oop/bomberman/gameOver.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load fxml");
            }
        }

    }

    public void winGame() {
        if (winGame) {
            FXMLLoader loader = new FXMLLoader(BombermanGame.class.getResource("/uet/oop/bomberman/youWin.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load fxml");
            }
        }

    }

    public void game() {
        SceneController sceneController = new SceneController();
        VBox root = sceneController.prepare();
        Canvas cv = (Canvas) root.getChildren().get(1);
        gc = cv.getGraphicsContext2D();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Bomberman-ver1.0");

        if (loadSavedGame) {
            map = board.loadSavedMap();
            loadSavedEnemy();
            bomberman = new Bomber(2, 1, Sprite.player_right_1.getFxImage());
            stillObjects = board.createMap(map);
            bomberman = board.loadBombermanFromTxt();
            loadSavedGame = false;
        } else {
            played = true;
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
                if (stage.isShowing()) {
                /*if (sceneController.status.equals("close")) {
                    stage.close();
                } else if (sceneController.status.equals("new-game")) {
                    newGame = true;
                    sceneController.status = "";
                }*/
                    {
                        if (permaLevel) getNextLevel();
                        if(checkWin) isWin();
                        update();
                        render();
                        if (sceneController.status.equals("save-game")) {
                            saveGame();
                            sceneController.status = "";
                        }
                    }
                }
                //if(!test) test();
            }
        };

        timer.start();
    }


    public void update() {
        if(winGame) {

            winGame();
            winGame = false;
        }
        if (!bomberman.isAlive && !gameOver) {
            gameOver = true;
            gameOver();
        }
        frame++;
        bomberman.checkInItem(stillObjects);
        if (bomberman.isPortal() && nextLevel) {
            nextLevel();
            nextLevel = false;
            permaLevel = false;
        }

        bomberman.update();
        //balloon
        for (int i = 0; i < balloons.size(); i++) {
            if (balloons.get(i).getAlive()) {
                balloons.get(i).enemyMovement(stillObjects, map);
                if (balloons.get(i).touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
            if (balloons.get(i).delete) {
                balloons.remove(balloons.get(i));
                i--;
            }
        }

        //oneal
        for (int i = 0; i < oneals.size(); i++) {
            if (oneals.get(i).getAlive()) {
                if (frame == 400) {
                    currentBomberX = bomberman.xPos;
                    currentBomberY = bomberman.yPos;
                    frame = 0;
                    ((Oneal) oneals.get(i)).setLocChange(true);
                }
                oneals.get(i).complexEnemyMovement(stillObjects, map, currentBomberX, currentBomberY);
                if (oneals.get(i).touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
                ((Oneal) oneals.get(i)).setLocChange(false);
            }
            if (oneals.get(i).delete) {
                oneals.remove(oneals.get(i));
                i--;
            }
        }
        //minvo
        for (int i = 0; i < minvos.size(); i++) {
            if (minvos.get(i).getAlive()) {
                minvos.get(i).complexEnemyMovement(stillObjects, map, bomberman.xPos, bomberman.yPos);
                if (minvos.get(i).touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
            if (minvos.get(i).delete) {
                minvos.remove(minvos.get(i));
                i--;
            }
        }
        //kondoria
        for (int i = 0; i < kondorias.size(); i++) {
            if (kondorias.get(i).getAlive()) {
                kondorias.get(i).update();
                kondorias.get(i).enemyMovement(stillObjects, map);
                if (kondorias.get(i).touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
                if (((Kondoria) kondorias.get(i)).isSkillReady()) {
                    Enemy s = (((Kondoria) kondorias.get(i)).spawnBalloon(stillObjects));
                    if (s != null) {
                        balloons.add(s);
                    }
                    ((Kondoria) kondorias.get(i)).setSkillReady(false);
                }
            }
            if (kondorias.get(i).delete) {
                kondorias.remove(kondorias.get(i));
                i--;
            }
        }
        //doll
        for (int i = 0; i < dolls.size(); i++) {
            if (dolls.get(i).getAlive()) {
                dolls.get(i).update();
                if (((Doll) dolls.get(i)).isSkillReady()) {
                    ((Doll) dolls.get(i)).teleport(stillObjects, bomberman.xPos, bomberman.yPos);
                    ((Doll) dolls.get(i)).setSkillReady(false);
                }
                dolls.get(i).enemyMovement(stillObjects, map);
                if (dolls.get(i).touchBomber(bomberman.xPos, bomberman.yPos)) {
                    bomberman.setAlive(false);
                }
            }
            if (dolls.get(i).delete) {
                dolls.remove(dolls.get(i));
                i--;
            }

        }

        //}
        //explosion handle
        checkExplosion();
    }

    public void render() {
        gc.clearRect(0, 0, 992, 416);
        stillObjects.forEach(g -> g.render(gc));

        for (Enemy balloon : balloons)
            balloon.render(gc);
        for (Enemy oneal : oneals)
            oneal.render(gc);
        for (Enemy kondoria : kondorias)
            kondoria.render(gc);
        for (Enemy doll : dolls)
            doll.render(gc);
        for (Enemy minvo : minvos)
            minvo.render(gc);
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
        map = board.getMap();
        stillObjects = board.createMap(map);

        bomberman = board.getBomberman();
        minvos = board.getMinvo();
        balloons = board.getBalloon();
        dolls = board.getDoll();
        kondorias = board.getKondoria();
        oneals = board.getOneal();
        bomberman = board.getBomberman();
        gameOver = false;
    }

    public void checkExplosion() {
        if (!bombs.isEmpty()) {
            for (Entity bomb : bombs) {
                if (((Bomb) bomb).getExplode()) {
                    //map = ((Bomb) bomb).getReturnedMap();
                    if (bomberman.getEnhancedFlame()) {
                        //balloon
                        for (Enemy enemy : balloons) {
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
                        for (Enemy minvo : minvos) {
                            if (((Bomb) bomb).enhancedBombTouched(minvo.xPos, minvo.yPos)) {
                                minvo.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Kondoria
                        for (Enemy kondoria : kondorias) {
                            if (((Bomb) bomb).enhancedBombTouched(kondoria.xPos, kondoria.yPos)) {
                                kondoria.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Doll
                        for (Enemy doll : dolls) {
                            if (((Bomb) bomb).enhancedBombTouched(doll.xPos, doll.yPos)) {
                                doll.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Oneal
                        for (Enemy oneal : oneals) {
                            if (((Bomb) bomb).enhancedBombTouched(oneal.xPos, oneal.yPos)) {
                                oneal.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                    } else {
                        for (Enemy enemy : balloons) {
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
                        for (Enemy minvo : minvos) {
                            if (((Bomb) bomb).bombTouched(minvo.xPos, minvo.yPos)) {
                                minvo.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Kondoria
                        for (Enemy kondoria : kondorias) {
                            if (((Bomb) bomb).bombTouched(kondoria.xPos, kondoria.yPos)) {
                                kondoria.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Doll
                        for (Enemy doll : dolls) {
                            if (((Bomb) bomb).bombTouched(doll.xPos, doll.yPos)) {
                                doll.setAlive(false);
                                Sound.enemyDie.play();
                            }
                        }
                        //Oneal
                        for (Enemy oneal : oneals) {
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
        if(!balloons.isEmpty()) return;
        if(!oneals.isEmpty()) return;
        nextLevel = true;
    }
    public void isWin() {
        if(!balloons.isEmpty()) return;
        if(!oneals.isEmpty()) return;
        if(!kondorias.isEmpty()) return;
        if(!dolls.isEmpty()) return;
        if(!minvos.isEmpty()) return;
        if(board.getLevel() < 2) return;
        winGame = true;
        checkWin = false;
    }
    public void nextLevel() {
        board = new Map("src/main/resources/levels/Level2.txt");
        board.setLevel(2);
        map = board.getMap();
        stillObjects = board.createMap(map);
        //bomberman = board.getBomberman();
        balloons.removeAll(balloons);
        oneals.removeAll(oneals);
        kondorias = board.getKondoria();
        dolls = board.getDoll();
        minvos = board.getMinvo();
        bomberman.xPos = 1;
        bomberman.yPos = 1;
    }

    public void loadSavedEnemy() {
        Enemy balloon1 = new Balloon(1, 1, Sprite.player_up_2.getFxImage());
        Enemy doll1 = new Doll(1, 1, Sprite.oneal_right1.getFxImage());
        Enemy kondoria1 = new Kondoria(1, 1, Sprite.player_up_2.getFxImage());
        Enemy minvo1 = new Minvo(1, 1, Sprite.player_left_2.getFxImage());
        Enemy oneal1 = new Oneal(1, 1, Sprite.oneal_dead.getFxImage());
        balloons = board.loadEnemyFromTxt("src/main/resources/game_progress/balloon.txt", balloon1);
        oneals = board.loadEnemyFromTxt("src/main/resources/game_progress/oneal.txt", oneal1);
        if (nextLevel) {
            dolls = board.loadEnemyFromTxt("src/main/resources/game_progress/balloon.txt", doll1);
            kondorias = board.loadEnemyFromTxt("src/main/resources/game_progress/kondoria.txt", kondoria1);
            minvos = board.loadEnemyFromTxt("src/main/resources/game_progress/minvo.txt", minvo1);
            bomberman = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
        }
    }
}