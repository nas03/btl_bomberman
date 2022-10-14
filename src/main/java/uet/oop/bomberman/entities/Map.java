package uet.oop.bomberman.entities;


import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.entities.Entity.HEIGHT;

public class Map {

    public List<Entity> stillObjects = new ArrayList<>();
    public char[][] map = new char[13][31];
    /* public int mapIndex = 0;
     public String MAP = "################################p     ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";
     public char[] mapArray = MAP.toCharArray();

     public Map() {
         for (int i = 0; i < 13; i++) {
             for (int j = 0; j < 31; j++) {
                 map[i][j] = mapArray[mapIndex++];
             }
         }
     }*/
    public BufferedImage img;
    public GraphicsContext gc;
    public char[] mapArray;
    int mapIndex = 0;
    public String MAP = "";
    public void getMapString() throws  FileNotFoundException, URISyntaxException {
        MAP = "";
        URL res = Map.class.getClassLoader().getResource("levels/Level1.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        Scanner scan = new Scanner(new BufferedReader(new FileReader(absolutePath)));
        while(scan.hasNext()) {
            MAP += scan.nextLine();
        }
        scan.close();
        if(MAP == null) {
            throw  new FileNotFoundException();
        }

    }
    public Map() {
        try {
            getMapString();
            mapArray = MAP.toCharArray();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    map[i][j] = mapArray[mapIndex++];
                }
            }
        } catch (Exception e) {
            System.out.println("cant construct map array");
        }

    }
    public static void main(String[] args) throws URISyntaxException {
        URL res = Map.class.getClassLoader().getResource("game_progress/game.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);
        /*out.flush();
        out.write("hello");
        out.close();*/
    }
    //public void getMap()...

    /*public void loadGame() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/SaveGame/saveFile.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            MovingEntity.xPos = Integer.parseInt(reader.readLine());// .readLine : read a line of text.
            MovingEntity.yPos = Integer.parseInt(reader.readLine());

            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Bomber bomber = new Bomber(MovingEntity.xPos, MovingEntity.yPos, Sprite.player_right.getFxImage());
    }*/

  public void saveGame(char[][] map) throws IOException {
      FileWriter out = new FileWriter(String.valueOf(Map.class.getResource("game_progress/game.txt")));
      out.flush();
      out.write("hello");
  }
    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public List<Entity> createMap() {
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
                } else if (map[i][j] == 'b') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        return stillObjects;

    }
}
