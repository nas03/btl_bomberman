package main;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Bomber extends Entity {

    GameConfig gc;
    KeyHandler keyH;


    public void setDefaultValue(){
        int x = 100;
        int y = 100;
        int speed = 4;
        Direction = "right";
    }

    public void getBomberimg(){
        try{
            up = read(getClass().getResourceAsStream("/BombermanSprite/up.png"));
            up1 = read(getClass().getResourceAsStream("/BombermanSprite/up1.png"));
            up2 = read(getClass().getResourceAsStream("/BombermanSprite/up2.png"));
            down1 = read(getClass().getResourceAsStream("/BombermanSprite/down1.png"));
            down2 = read(getClass().getResourceAsStream("/BombermanSprite/down2.png"));
            left = read(getClass().getResourceAsStream("/BombermanSprite/left.png"));
            left1 = read(getClass().getResourceAsStream("/BombermanSprite/left1.png"));
            left2 = read(getClass().getResourceAsStream("/BombermanSprite/left2.png"));
            right = read(getClass().getResourceAsStream("/BombermanSprite/right.png"));
            right1 = read(getClass().getResourceAsStream("/BombermanSprite/right1.png"));
            right2 = read(getClass().getResourceAsStream("/BombermanSprite/right2.png"));

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void render(Graphics2D g2){
        BufferedImage image = null;
        switch (Direction) {
            case "up":
                if(SpriteSignal == 1){
                    image = up1;
                }
                if(SpriteSignal == 2){
                    image = up2;
                }
                break;
            case "down":
                if(SpriteSignal == 1){
                    image = down1;
                }
                if(SpriteSignal == 2){
                    image = down2;
                }
                break;
            case "right":
                if(SpriteSignal == 1){
                    image = right1;
                }
                if(SpriteSignal == 2){
                    image = right2;
                }
                break;
            case "left":
                if(SpriteSignal == 1){
                    image = left1;
                }
                if(SpriteSignal == 2){
                    image = left2;
                }
                break;
        }
        g2.drawImage(image, x, y, gc.adjustedSize, gc.adjustedSize,null );
    }


    public void update() // Update Sprite animation
    {
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed == true){
            if(keyH.upPressed == true){
                Direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed){
                Direction ="down";
                y += speed;
            }
            else if(keyH.rightPressed){
                Direction = "right";
                x += speed;
            }
            else if(keyH.leftPressed){
                Direction = "left";
                x -= speed;
            }
            SpriteCounter++;

            if(SpriteCounter > 10){
                if(SpriteSignal == 1){
                    SpriteSignal = 2;
                }else if(SpriteSignal == 2){
                    SpriteSignal = 1;
                }
                SpriteCounter = 0;
            }

        }

    }


}
