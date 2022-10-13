package uet.oop.bomberman.entities.movingEntity.enemy.automove;

public class ComplexMovement extends AutomaticMovement {
    // Su dung thuat toan DFS
    public int row;
    public boolean solved = false;
    public int col;
    public int distance;
    public boolean[][] maze = new boolean[13][31]; // The maze
    public boolean[][] wasHere = new boolean[13][31];
    public boolean[][] correctPath = new boolean[13][31];

    public ComplexMovement(char[][] map) {
        row = 13;
        col = 31;
        distance = 0;
        ; // The solution to the maze

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (map[i][j] != '*' && map[i][j] != '#' && map[i][j] != 'x' && map[i][j] != 'f') {
                    maze[i][j] = false;
                } else {
                    maze[i][j] = true;
                }
            }
        }
        for (int i = 0; i < 13; i++) {
            // Sets boolean Arrays to default values
            for (int j = 0; j < 31; j++) {
                wasHere[i][j] = false;
                correctPath[i][j] = false;
            }
        }
    }

    @Override
    public int calculateMovement() {
        return -1;
    }

    @Override
    public void voidTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
            boolean b = recursiveSolve(enemyPosX, enemyPosY, bomberPosX, bomberPosY);
            if(b) {
                solved = true;
            }
    }

    @Override
    public int intTypeComplexMovement(char[][] map, int enemyPosX, int enemyPosY, int bomberPosX, int bomberPosY) {
        return 0;
    }


    public boolean recursiveSolve(int x, int y, int bomberPosX, int bomberPosY) {
        if (x == bomberPosX && y == bomberPosY) {
            return true; // If you reached the end
        }
        if (maze[x][y] || wasHere[x][y]) {
            return false;
        }
        // If you are on a wall or already were here
        wasHere[x][y] = true;
        if (x != 0) {
            if (recursiveSolve(x - 1, y, bomberPosX, bomberPosY)) { // Recalls method one to the left
                correctPath[x][y] = true; // Sets that path value to true;
                return true;
            }
        }
        if (x != 30) { // Checks if not on right edge{
            if (recursiveSolve(x + 1, y, bomberPosX, bomberPosY)) { // Recalls method one to the right
                correctPath[x][y] = true;
                return true;
            }
        }
        if (y != 0) {
            if (recursiveSolve(x, y - 1, bomberPosX, bomberPosY)) { // Recalls method one up
                correctPath[x][y] = true;
                return true;
            }
        }
        if (y != 13 - 1) {
            if (recursiveSolve(x, y + 1, bomberPosX, bomberPosY)) { // Recalls method one down
                correctPath[x][y] = true;
                return true;
            }
        }
        return false;
    }
}
