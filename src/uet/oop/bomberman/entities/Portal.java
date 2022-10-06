package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Portal extends Entity {

  public boolean stepIn = false;
  public Portal(int x, int y, Image img) {
    super(x, y, img);
  }


  public int stepOutX(char[][] map, int x, int y){

    for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if(map[i][j] == 'x' && i != y && j != x) {
            return j;
          }
        }
      }
      return -1;
  }
  public int stepOutY(char[][] map, int x, int y) {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if(map[i][j] == 'x' && i != y && j != x) {
          return i;
        }
      }
    }
    return -1;
  }
}
