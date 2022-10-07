package uet.oop.bomberman.entities.movingEntity.enemy.automove;

public abstract class AutomaticMovement {
    public int direction;
    public abstract int calculateMovement();
    public abstract void calculateComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY);
}
