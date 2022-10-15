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

    public BufferedImage img;
    public GraphicsContext gc;
    public char[] mapArray;
    int mapIndex = 0;
    public String MAP = "";
    public void getMapString(String path) throws  FileNotFoundException, URISyntaxException {
        MAP = "";
        Scanner scan = new Scanner(new BufferedReader(new FileReader(path)));
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
            getMapString("src/main/resources/levels/Level1.txt");
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

    public void saveGameMap(char[][] map) {
        try {
            FileWriter out = new FileWriter("src/main/resources/game_progress/game.txt");
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    out.write(map[i][j]);
                }
            }
            out.close();
        }
        catch (Exception e) {
            System.out.println("Cant load game.txt");
        }
    }
    public char[][] loadSavedMap() {
        try {
           getMapString("src/main/resources/game_progress/game.txt");
           mapIndex = 0;
            mapArray = MAP.toCharArray();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    map[i][j] = mapArray[mapIndex++];
                }
            }

        } catch (Exception e) {
            System.out.println("cant load game progress file");
        }
        return map;
    }
    public static void main(String[] args) throws URISyntaxException, IOException {

    }
    //public void getMap()...

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
