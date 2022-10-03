package uet.oop.bomberman.entities;

import java.util.Objects;

public class Map {

  public int row = 13;
  public int col = 31;
   public char[][] map = new char[13][31];

  /**
   * 1: wall 0: grass 2: brick
   */

  int mapIndex = 0;
  String MAP = "################################p     ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";
  char[] mapArray = MAP.toCharArray();

  public Map() {
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        map[i][j] = mapArray[mapIndex++];
      }
    }
  }

  public String getEntity(int x, int y) {
    if (map[x][y] == ' ') {
      return "Grass";
    }
    if (map[x][y]=='#') {
      return "Wall";
    }
    if (map[x][y]=='x') {
      return "Portal";
    }
    if (map[x][y]=='b') {
      return "BombItem";
    }
    if (map[x][y]== 'f') {
      return "FlameItem";
    }
    if (map[x][y]== 's') {
      return "SpeedItem";
    }
    if (map[x][y]== '*') {
      return "Brick";
    }
    return "?";
  }

  void updateMap() {
    char[][] temp = new char[13][31];
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        temp[i][j] = map[i][j];
      }
    }
    map = temp;
  }
  public static void main(String[] args){
    Map map = new Map();
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        System.out.println(map.getEntity(i,j));
      }
      System.out.println("");
    }
  }
  void renderMap() {
    //Load image
  }
}
