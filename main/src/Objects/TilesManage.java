package Objects;

import main.GameConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TilesManage {
    GameConfig gc;
    Tiles[] objects;

    public TilesManage(GameConfig gc ){
        this.gc = gc;
        objects = new Tiles[10];
        getObjectsImage();
    }

    public void getObjectsImage(){
        try{
            objects[0] = new Tiles();
            objects[0].image = ImageIO.read(getClass().getResourceAsStream("/Objects/grass.png"));

            objects[1] = new Tiles();
            objects[1].image = ImageIO.read(getClass().getResourceAsStream("/Objects/brick.png"));

            objects[2] = new Tiles();
            objects[2].image = ImageIO.read(getClass().getResourceAsStream("/Objects/wall.png"));

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    // Định dạng của Tiles khi tạo file txt : 0 - cỏ ; 1 - brick ; 2 wall


    public void render(Graphics2D g2){
        g2.drawImage(objects[0].image,0,0,gc.adjustedSize,gc.adjustedSize,null);

    }

}

