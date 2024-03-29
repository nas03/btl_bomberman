package uet.oop.bomberman.ultilities;


import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.moving_entities.Bomber;
import uet.oop.bomberman.moving_entities.enemy.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.entities.Entity.HEIGHT;

public class Map {
    public String MAP = "";
    char[] mapArray;
    int mapIndex = 0;
    private List<Enemy> balloon = new ArrayList<>();
    private List<Enemy> minvo = new ArrayList<>();
    private List<Enemy> oneal = new ArrayList<>();
    private List<Enemy> kondoria = new ArrayList<>();
    private List<Enemy> doll = new ArrayList<>();
    private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
    private List<Entity> stillObjects = new ArrayList<>();
    private char[][] map = new char[13][31];
    private int level = 1;

    public Map(String path) {
        try {
            mapIndex = 0;
            getMapString(path);
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

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void getMapString(String path) throws FileNotFoundException, URISyntaxException {
        MAP = "";
        Scanner scan = new Scanner(new BufferedReader(new FileReader(path)));
        while (scan.hasNext()) {
            MAP += scan.nextLine();
        }
        scan.close();
        if (MAP == null) {
            throw new FileNotFoundException();
        }

    }

    public char[][] getMap() {
        try {
            mapIndex = 0;
            if (level == 1)
                getMapString("src/main/resources/levels/Level1.txt");
            else
                getMapString("src/main/resources/levels/Level2.txt");
            mapArray = MAP.toCharArray();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    map[i][j] = mapArray[mapIndex++];
                }
            }
        } catch (Exception e) {
            System.out.println("cant construct map array");
        }
        return map;
    }

    public void saveGame(char[][] map) {
        try {
            FileWriter out = new FileWriter("src/main/resources/game_progress/game.txt");
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) {
                    out.write(map[i][j]);
                }
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Cant load game.txt");
        }
    }

    public void saveMovingEntity(List<Enemy> balloons, List<Enemy> oneals, List<Enemy> minvos, List<Enemy> kondorias, List<Enemy> dolls, Bomber bomberman) {
        saveEnemy("src/main/resources/game_progress/balloon.txt", balloons);
        saveEnemy("src/main/resources/game_progress/oneal.txt", oneals);
        saveEnemy("src/main/resources/game_progress/doll.txt", dolls);
        saveEnemy("src/main/resources/game_progress/kondoria.txt", kondorias);
        saveEnemy("src/main/resources/game_progress/minvo.txt", minvos);
        saveBomber("src/main/resources/game_progress/bomber.txt", bomberman);
    }

    public void saveEnemy(String path, List<Enemy> enemies) {
        try {
            FileWriter out = new FileWriter(path);
            for (Enemy enemy : enemies) {
                if (enemy.getAlive()) {
                    out.write(enemy.xPos + " " + enemy.yPos + '\n');
                }
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Cant save enemy");
        }
    }

    public List<Enemy> loadEnemyFromTxt(String path, Enemy type) {
        List<Enemy> enemies = new ArrayList<>();
        try {
            Scanner in = new Scanner(new BufferedReader(new FileReader(path)));
            while (in.hasNext()) {
                String s = in.nextLine();
                String[] splitter = s.split(" ");
                int a = Integer.parseInt(splitter[0]);
                int b = Integer.parseInt(splitter[1]);
                if (type instanceof Balloon) {
                    enemies.add(new Balloon(a, b, Sprite.balloom_right1.getFxImage()));
                } else if (type instanceof Doll) {
                    enemies.add(new Doll(a, b, Sprite.doll_right1.getFxImage()));
                } else if (type instanceof Oneal) {
                    enemies.add(new Oneal(a, b, Sprite.oneal_right1.getFxImage()));
                } else if (type instanceof Kondoria) {
                    enemies.add(new Kondoria(a, b, Sprite.kondoria_right1.getFxImage()));
                } else {
                    enemies.add(new Minvo(a, b, Sprite.minvo_right1.getFxImage()));
                }
            }
        } catch (Exception e) {
            System.out.println("Cant load data from resources");
        }
        return enemies;
    }

    public Bomber loadBombermanFromTxt() {
        try {
            Scanner scan = new Scanner(new BufferedReader(new FileReader("src/main/resources/game_progress/bomber.txt")));
            String pos = scan.nextLine();
            String[] position = pos.split(" ");
            int a = Integer.parseInt(position[0]);
            int b = Integer.parseInt(position[1]);
            Bomber bomberman = new Bomber(a, b, Sprite.player_right.getFxImage());
            String bombLimit = scan.nextLine();
            bomberman.bombLimit = Integer.parseInt(bombLimit);
            String speed = scan.nextLine();
            bomberman.setSpeed(Integer.parseInt(speed));
            String enhancedFlame = scan.nextLine();
            if (enhancedFlame.equals("true")) {
                bomberman.setEnhancedFlame(true);
            } else {
                bomberman.setEnhancedFlame(false);
            }
            return bomberman;
        } catch (Exception e) {
            System.out.println("cant load bomber.txt");
        }
        return null;
    }

    public void saveBomber(String path, Bomber bomberman) {
        try {
            FileWriter out = new FileWriter(path);
            out.write(bomberman.xPos + " " + bomberman.yPos + '\n');
            String bombLimit = String.valueOf(bomberman.bombLimit);
            out.write(bombLimit + '\n');
            String speed = String.valueOf(bomberman.getSpeed());
            out.write(speed + '\n');
            if (bomberman.getEnhancedFlame()) {
                out.write("true\n");
            } else {
                out.write("false\n");
            }

            out.close();
        } catch (Exception e) {
            System.out.println("Cant save enemy\n");
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

    public List<Entity> createMap(char[][] map) {
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

    public List<Enemy> getBalloon() {
        balloon.removeAll(balloon);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '1') {
                    balloon.add(new Balloon(j, i, Sprite.balloom_right1.getFxImage()));
                }
            }
        }
        return balloon;
    }

    public List<Enemy> getMinvo() {
        minvo.removeAll(minvo);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '3') {
                    minvo.add(new Minvo(j, i, Sprite.balloom_right1.getFxImage()));
                }
            }
        }
        return minvo;
    }

    public List<Enemy> getOneal() {
        oneal.removeAll(oneal);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '2') {
                    oneal.add(new Oneal(j, i, Sprite.balloom_right1.getFxImage()));
                }
            }
        }
        return oneal;
    }

    public List<Enemy> getKondoria() {
        kondoria.removeAll(kondoria);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '4') {
                    kondoria.add(new Kondoria(j, i, Sprite.balloom_right1.getFxImage()));
                }
            }
        }
        return kondoria;
    }

    public List<Enemy> getDoll() {
        doll.removeAll(doll);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == '5') {
                    doll.add(new Doll(j, i, Sprite.balloom_right1.getFxImage()));
                }
            }
        }
        return doll;
    }

    public Bomber getBomberman() {
        bomberman = null;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map[i][j] == 'p') {
                    bomberman = new Bomber(j, i, Sprite.balloom_right1.getFxImage());
                }
            }
        }
        return bomberman;
    }

}
