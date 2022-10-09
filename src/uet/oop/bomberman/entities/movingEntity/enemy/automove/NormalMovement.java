package uet.oop.bomberman.entities.movingEntity.enemy.automove;

public class NormalMovement extends AutomaticMovement{
    @Override
    public int calculateMovement() {
        return 0;
    }

    @Override
    public void calculateComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }
}
