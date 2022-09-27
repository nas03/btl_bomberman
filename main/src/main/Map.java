package src.main;

import java.util.*;
import java.text.*;

public class Map {

  public String[][] map = new String[13][31];

  /**
   * 1: wall 0: grass 2: brick
   */
  int mapIndex = 0;
  String MAP = "################################p     ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";
  String[] mapArray = MAP.split("", -1);

  Map() {
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        map[i][j] = mapArray[mapIndex++];
      }
    }
  }

  public String getEntity(int x, int y) {
    if (map[x][y].equals(" ")) {
      return "Grass";
    }
    if (map[x][y].equals("#")) {
      return "main.Wall";
    }
    if (map[x][y].equals("x")) {
      return "main.Portal";
    }
    if (map[x][y].equals("b")) {
      return "BombItem";
    }
    if (map[x][y].equals("f")) {
      return "FlameItem";
    }
    if (map[x][y].equals("s")) {
      return "SpeedItem";
    }
    return "";
  }

  void updateMap() {
    String[][] temp = new String[13][31];
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        temp[i][j] = map[i][j];
      }
    }
    map = temp;
  }

  void renderMap() {
//Render image
  }
}
