package uet.oop.bomberman.entities.movingEntity.enemy.automove;

import java.util.Random;

public class SimpleMovement extends AutomaticMovement{
    Random random = new Random();
    public SimpleMovement() {

    }
    @Override
    public int calculateMovement() {
        return direction = random.nextInt(4);
    }

    @Override
    public void calculateComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }
}
