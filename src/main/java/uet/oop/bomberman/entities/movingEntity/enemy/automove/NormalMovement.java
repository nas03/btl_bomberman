package uet.oop.bomberman.entities.movingEntity.enemy.automove;

public class NormalMovement extends AutomaticMovement {
    @Override
    public int calculateMovement() {

        return random.nextInt(2);

    }

    @Override
    public void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }

    /**
     * normal movement calculated by xAxis and yAxis.
     *
     * @param map        map
     * @param enemyPosX  eneny x
     * @param enemyPosY  enemy y
     * @param bomberPosX bomber x
     * @param bomberPosY bomber y
     * @return 1:right 0:left 2:down 3:up
     */
    @Override
    public int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        int direction = calculateMovement();
        if (direction == 0) {
            return xDistance(enemyPosX, bomberPosX);
        }
        return yDistance(enemyPosY, bomberPosY);
    }

    public int xDistance(int enemyPosX, int bomberPosX) {
        int direction = enemyPosX - bomberPosX;
        if (direction < 0) {
            return 1;
        }
        return 0;
    }

    public int yDistance(int enemyYPos, int bomberYPos) {
        int direction = enemyYPos - bomberYPos;
        if (direction < 0) {
            return 2;
        }
        return 3;
    }
}
