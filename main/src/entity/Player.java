package entity;

import main.GameConfig;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Player extends entity {


        GameConfig gc;

        KeyHandler keyH;

        public void setDefaultValue(){
            int x = 100;
            int y = 100;
            int speed = 4;
            direction ="right";
        }
        public  Player(GameConfig gc, KeyHandler keyH) {
            this.gc = gc;
            this.keyH = keyH;
            GetBombermanImage();
            setDefaultValue();
        }


        public void GetBombermanImage () {
            try {
                up = read(getClass().getResourceAsStream("/res/BombermanSprite/up.png"));
                up1 = read(getClass().getResourceAsStream("/res/BombermanSprite/up1.png"));
                up2 = read(getClass().getResourceAsStream("/res/BombermanSprite/up2.png"));
                down1 = read(getClass().getResourceAsStream("/res/BombermanSprite/down1.png"));
                down2 = read(getClass().getResourceAsStream("/res/BombermanSprite/down2.png"));
                left = read(getClass().getResourceAsStream("/res/BombermanSprite/left.png"));
                left1 = read(getClass().getResourceAsStream("/res/BombermanSprite/left1.png"));
                left2 = read(getClass().getResourceAsStream("/res/BombermanSprite/left2.png"));
                right = read(getClass().getResourceAsStream("/res/BombermanSprite/right.png"));
                right1 = read(getClass().getResourceAsStream("/res/BombermanSprite/right1.png"));
                right2 = read(getClass().getResourceAsStream("/res/BombermanSprite/right2.png"));


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        public void render(Graphics2D g2){
            BufferedImage image = null;
            switch (direction) {
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
                    direction = "up";
                    y -= speed;
                }
                else if(keyH.downPressed){
                    direction ="down";
                    y += speed;
                }
                else if(keyH.rightPressed){
                    direction = "right";
                    x += speed;
                }
                else if(keyH.leftPressed){
                    direction = "left";
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


