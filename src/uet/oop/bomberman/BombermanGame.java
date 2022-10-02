package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    Map map = new Map();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

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

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() {
        int mapIndex = 0;
        String MAP = "################################p     ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";
        char[][] map = new char[13][31];
        char[] mapArray = MAP.toCharArray();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    Entity object;
                    map[i][j] = mapArray[mapIndex++];

                    if (map[i][j]=='#') {
                        object = new Wall(i,j,Sprite.wall.getFxImage());
                    }
                    else if (map[i][j]=='x') {
                        object = new Portal(i,j,Sprite.portal.getFxImage());
                    }
                    /*if (map[i][j]=='b') {
                        return "BombItem";
                    }
                    if (map[i][j]== 'f') {
                        return "FlameItem";
                    }
                    if (map[i][j]== 's') {
                        return "SpeedItem";
                    }*/
                    else if (map[i][j]== '*') {
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
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}