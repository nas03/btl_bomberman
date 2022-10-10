package uet.oop.bomberman.entities.movingEntity.enemy.automove;

public class NormalMovement extends AutomaticMovement{
    @Override
    public int calculateMovement() {

        return random.nextInt(2);

    }

    @Override
    public void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }

    @Override
    public int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        int direction = calculateMovement();
        // 0: yAxis 1:xAxis
        //
        if(direction == 0) {
            return xDistance(enemyPosX,bomberPosX);
        }
        return yDistance(enemyPosY,bomberPosY);
    }

    public int xDistance(int enemyPosX, int bomberPosX) {
        int direction =  enemyPosX - bomberPosX;
        if(direction < 0) {
            return 1;
        }
        return 0;
    }

    public int yDistance(int enemyYPos, int bomberYPos) {
        int direction =  enemyYPos - bomberYPos;
        if(direction < 0) {
            return 2;
        }
        return 3;
    }
}
