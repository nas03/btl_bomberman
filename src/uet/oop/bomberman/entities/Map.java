package uet.oop.bomberman.entities;


public class Map {

  public int row = 13;
  public int col = 31;
   public char[][] map = new char[13][31];

  /**
   * 1: wall 0: grass 2: brick
   */

  public int mapIndex = 0;
  public String MAP = "################################p     ** *  1 * 2 *  * * *   ## # # #*# # #*#*# # # #*#*#*# ##  x*     ***  *  1   * 2 * * ## # # # # #*# # #*#*# # # # #*##f         x **  *  *   1     ## # # # # # # # # #*# #*# # # ##*  *      *  *      *        ## # # # #*# # # #*#*# # # # # ##*    **  *       *           ## #*# # # # # # #*# # # # # # ##           *   *  *          ################################";
  public char[] mapArray = MAP.toCharArray();

  public Map() {
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 31; j++) {
        map[i][j] = mapArray[mapIndex++];
      }
    }
  }
  public char[][] getMap() {
    return map;
  }

}
