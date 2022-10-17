package uet.oop.bomberman.automove;

import java.util.Random;

public abstract class AutomaticMovement {
    Random random = new Random();
    public abstract int calculateMovement();
    public abstract void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY);
    public abstract int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY);


}
