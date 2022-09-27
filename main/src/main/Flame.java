package src.main;

import javafx.scene.shape.DrawMode;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static javax.imageio.ImageIO.read;

public class Flame extends Entity {
    GameConfig gc;
    Graphics2D g2;

    public Flame(GameConfig gc){
       getFlameimg();
       render(g2);

    }
    public void getFlameimg(){
        try{
            ex_h = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal.png"));
            ex_h1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal1.png"));
            ex_h2 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal2.png"));
            ex_h_left_last = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_left_last.png"));
            ex_h_left_last1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_left_last1.png"));
            ex_h_left_last2 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_left_last2.png"));
            ex_h_right_last = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_right_last.png"));
            ex_h_right_last1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_right_last1.png"));
            ex_h_right_last2= read(getClass().getResourceAsStream("/resources/Explosion/explosion_horizontal_rightt_last2.png"));
            ex_v = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical.png"));
            ex_v1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical1.png"));
            ex_v2 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical2.png"));
            ex_v_down_last = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_down_last.png"));
            ex_v_down_last1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_down_last1.png"));
            ex_v_down_last2 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_down_last2.png"));
            ex_v_top_last = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_top_last.png"));
            ex_v_top_last1 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_top_last1.png"));
            ex_v_top_last2 = read(getClass().getResourceAsStream("/resources/Explosion/explosion_vertical_top_last2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics2D g2){

    }
}
