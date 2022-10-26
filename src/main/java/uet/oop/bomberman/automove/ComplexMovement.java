package uet.oop.bomberman.automove;

public class ComplexMovement extends AutomaticMovement {
    // Su dung thuat toan DFS
    private boolean solved = false;
    private boolean[][] maze = new boolean[13][31];
    private boolean[][] wasHere = new boolean[13][31];
    private boolean[][] correctPath = new boolean[13][31];

    public ComplexMovement() {

    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean[][] getCorrectPath() {
        return correctPath;
    }

    @Override
    public int calculateMovement() {
        return -1;
    }

    @Override
    public void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                if (map[i][j] == '#' || map[i][j] == '*') {
                    maze[i][j] = true;
                } else {
                    maze[i][j] = false;
                }
            }
        }

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                wasHere[i][j] = false;
                correctPath[i][j] = false;
            }
        }
        boolean b = recursiveSolve(enemyPosX, enemyPosY, bomberPosX, bomberPosY);
        if (b) {
            solved = true;
        }
    }

    @Override
    public int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        return 0;
    }


    public boolean recursiveSolve(int x, int y, int bomberPosX, int bomberPosY) {
        if (x == bomberPosX && y == bomberPosY) {
            return true;
        }
        if (maze[x][y] || wasHere[x][y]) {
            correctPath[x][y] = false;
            return false;
        }

        wasHere[x][y] = true;
        if (x != 0) {
            if (recursiveSolve(x - 1, y, bomberPosX, bomberPosY)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (x != 12) { // Checks if not on right edge{
            if (recursiveSolve(x + 1, y, bomberPosX, bomberPosY)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (y != 0) {
            if (recursiveSolve(x, y - 1, bomberPosX, bomberPosY)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        if (y != 30) {
            if (recursiveSolve(x, y + 1, bomberPosX, bomberPosY)) {
                correctPath[x][y] = true;
                return true;
            }
        }
        return false;
    }
}
