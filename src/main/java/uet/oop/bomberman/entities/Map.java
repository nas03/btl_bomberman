package uet.oop.bomberman.entities;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.entities.movingEntity.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


public class Map {
  public BufferedImage img;
  public  GraphicsContext gc ;
  public int row = 13;
  public int col = 31;
  public int [][] map = new int[13][31];




    public void loadMap() {
       try{
           InputStream inputStream = getClass().getResourceAsStream("/levels/map1.txt");
           BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

           int col = 0 ;
           int row = 0 ;
           while(col < Entity.WIDTH && row < Entity.HEIGHT) {
               String line = reader.readLine();
               while(col < Entity.WIDTH) {
                   String numbers[] = line.split(" "); // space between element in Array which stored map.
                   int num = Integer.parseInt(numbers[col]); // use col as an index for numbers[].
                   map[col][row] = num;
                   col ++;
               }
               if(col == Entity.WIDTH) {
                   col = 0 ;
                   row ++;
               }
           }
           reader.close();
       }catch(IOException e) {
           e.printStackTrace();
       }
    }

    //public void getMap()...

    public void loadGame() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/SaveGame/saveFile.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            MovingEntity.xPos = Integer.parseInt(reader.readLine());// .readLine : read a line of text.
            MovingEntity.yPos = Integer.parseInt(reader.readLine());

            reader.close();


        }catch (IOException e) {
            e.printStackTrace();
        }

        Bomber bomber = new Bomber(MovingEntity.xPos , MovingEntity.yPos , Sprite.player_right.getFxImage());
    }

    public void saveGame(char map[][]) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("saveFile.txt"));
            writer.write("" + MovingEntity.xPos);
            writer.newLine();
            writer.write("" + MovingEntity.yPos);
            writer.newLine();

            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }
}

  /**
   * 1: wall 0: grass 2: brick
   */

  /**public int mapIndex = 0;
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
   **/



