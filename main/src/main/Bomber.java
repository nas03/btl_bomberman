package src.main;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.w3c.dom.css.Rect;

import static src.main.Constant.*;

/**
 * class Player.
 */
public class Bomber {

  int speed = 1;
  int x;
  int y;

  Map map = new Map();

  //Motion
  public void moveUp() {
    if (y - speed < 0) {
      return;
    }
    if (map.getEntity(x, y - speed).equals("Brick") || map.getEntity(x, y - speed).equals("Wall")) {
      return;
    }
    y -= speed;

  }

  public void moveDown() {
    if (y + speed > SCREEN_HEIGHT) {
      return;
    }
    if (map.getEntity(x, y + speed).equals("Brick") || map.getEntity(x, y + speed).equals("Wall")) {
      return;
    }

    y += speed;
  }

  public void moveRight() {
    if (x + speed > SCREEN_WIDTH) {
      return;
    }
    if (map.getEntity(x + speed, y).equals("Brick") || map.getEntity(x + speed, y).equals("Wall")) {
      return;
    }

    x += speed;
  }

  public void moveLeft() {
    if (x - speed < 0) {
      return;
    }
    if (map.getEntity(x - speed, y).equals("Brick") || map.getEntity(x - speed, y).equals("Wall")) {
      return;
    }

    x -= speed;
  }


}
