package uet.oop.bomberman.automove;

public class SimpleMovement extends AutomaticMovement {
    @Override
    public int calculateMovement() {
        return random.nextInt(4);
    }

    @Override
    public void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {

    }

    @Override
    public int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        return 0;
    }
}
