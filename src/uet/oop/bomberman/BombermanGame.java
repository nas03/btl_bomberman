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
/*
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Map;
import uet.oop.bomberman.entities.Wall;
*/
import uet.oop.bomberman.graphics.Sprite;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public char[][] map = new char[13][31];
    public int xPos = 1;
    public int yPos = 1;
    private GraphicsContext gc;
    private Canvas canvas;
    Entity bomberman;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        bomberman = new Bomber(xPos,yPos, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()== KeyCode.W) {
                System.out.println("You pressed W");
                if (canMove(xPos,yPos-1)) {
                    yPos -= 1;
                    bomberman = new Bomber(xPos,yPos,Sprite.player_up.getFxImage());
                    entities.clear();
                    entities.add(bomberman);
                }

            }
            if(key.getCode()== KeyCode.S) {
                System.out.println("S");
                if (canMove(xPos,yPos + 1)) {
                    yPos += 1;
                    bomberman = new Bomber(xPos,yPos,Sprite.player_down.getFxImage());
                    entities.clear();
                    entities.add(bomberman);
                }
            }
            if (key.getCode() == KeyCode.D) {
                System.out.println("D");

                if (canMove(xPos + 1,yPos)) {
                    xPos += 1;
                    bomberman = new Bomber(xPos,yPos,Sprite.player_right.getFxImage());
                    entities.clear();
                    entities.add(bomberman);
                }
            }
            if (key.getCode() == KeyCode.A) {
                System.out.println("A");
                if (canMove(xPos-1,yPos)) {
                    xPos -= 1;
                    bomberman = new Bomber(xPos,yPos,Sprite.player_left.getFxImage());
                    entities.clear();
                    entities.add(bomberman);
                }
            }
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
        int mapIndex = 0;
        char[][] map = new char[13][31];
        String MAP = "################################      ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";

        char[] mapArray = MAP.toCharArray();
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                map[i][j] = mapArray[mapIndex++];
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (map[i][j] =='#') {
                    object = new Wall(i,j,Sprite.wall.getFxImage());
                }
                else if (map[i][j]=='x') {
                    object = new Portal(i,j,Sprite.portal.getFxImage());
                }

                else if (map[i][j] == '*') {
                    object = new Brick(i,j,Sprite.brick.getFxImage());
                }

                else {
                    object = new Grass(i,j,Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).render(gc);
        }
        entities.forEach(g -> g.render(gc));
    }

    public boolean canMove(int x, int y) {
        if (map[x][y] == '#' || map[x][y] == '*') {
            return false;
        }
        if (x-1 < 0 || x + 1 > WIDTH || y -1 < 0 || y + 1 > HEIGHT) {
            return false;
        }
        return true;
    }
}