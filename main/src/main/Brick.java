package src.main;

import java.awt.*;
import java.io.IOException;
import static javax.imageio.ImageIO.read;
public class Brick extends Entity {
    GameConfig gc;
    Graphics2D g2;
    public Brick(GameConfig gc){
        getBrickImg();
        render(g2);

    }
  Brick(int x, int y) {

  }

  void getBrickImg(){
        try{
            brick = read(getClass().getResourceAsStream("/resources/brick/brick.png"));
            brick_ex = read(getClass().getResourceAsStream("/resources/brick/brick_exploded.png"));
            brick_ex1 = read(getClass().getResourceAsStream("/resources/brick/brick_exploded1.png"));
            brick_ex2 = read(getClass().getResourceAsStream("/resources/brick/brick_exploded2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
  }

  void render(Graphics2D g2){


  }



}
