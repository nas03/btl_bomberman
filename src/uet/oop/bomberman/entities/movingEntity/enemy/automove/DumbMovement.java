package uet.oop.bomberman.entities.movingEntity.enemy.automove;

import uet.oop.bomberman.entities.Entity;

import java.util.List;

public class DumbMovement extends AutomaticMovement{

    @Override
    public int calculateMovement() {
        return 0;
    }

    @Override
    public void calculateComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }
}
