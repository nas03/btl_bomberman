package src.entity;
import java.util.Objects;
import src.main.GameConfig;
import src.main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Player extends Entity {


  static GameConfig gc;

  static KeyHandler keyH;

  public void setDefaultValue() {
    int x = 100;
    int y = 100;
    int speed = 4;
    direction = "right";
  }

  public Player(GameConfig gc, KeyHandler keyH) {
    this.gc = gc;
    this.keyH = keyH;
    GetBombermanImage();
    setDefaultValue();
  }


  public void GetBombermanImage() {
    try {
      up = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/up.png")));
      up1 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/up1.png")));
      up2 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/up2.png")));
      down1 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/down1.png")));
      down2 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/down2.png")));
      left = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/left.png")));
      left1 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/left1.png")));
      left2 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/left2.png")));
      right = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/right.png")));
      right1 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/right1.png")));
      right2 = read(Objects.requireNonNull(
          getClass().getResourceAsStream("/resources/BombermanSprite/right2.png")));


    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static void render(Graphics2D g2) {
    BufferedImage image = null;
    switch (direction) {
      case "up":
        if (SpriteSignal == 1) {
          image = up1;
        }
        if (SpriteSignal == 2) {
          image = up2;
        }
        break;
      case "down":
        if (SpriteSignal == 1) {
          image = down1;
        }
        if (SpriteSignal == 2) {
          image = down2;
        }
        break;
      case "right":
        if (SpriteSignal == 1) {
          image = right1;
        }
        if (SpriteSignal == 2) {
          image = right2;
        }
        break;
      case "left":
        if (SpriteSignal == 1) {
          image = left1;
        }
        if (SpriteSignal == 2) {
          image = left2;
        }
        break;
    }
    g2.drawImage(image, x, y, gc.adjustedSize, gc.adjustedSize, null);
  }

  public static void update() // Update Sprite animation
  {
    if (keyH.upPressed
        || keyH.downPressed || keyH.rightPressed || keyH.leftPressed) {
      if (keyH.upPressed) {
        direction = "up";
        y -= speed;
      } else if (keyH.downPressed) {
        direction = "down";
        y += speed;
      } else if (keyH.rightPressed) {
        direction = "right";
        x += speed;
      } else if (keyH.leftPressed) {
        direction = "left";
        x -= speed;
      }
      SpriteCounter++;

      if (SpriteCounter > 10) {
        if (SpriteSignal == 1) {
          SpriteSignal = 2;
        } else if (SpriteSignal == 2) {
          SpriteSignal = 1;
        }
        SpriteCounter = 0;
      }

    }

  }

}


