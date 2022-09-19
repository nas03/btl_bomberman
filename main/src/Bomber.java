import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber {

  int x;
  int y;

  //Motion
  void moveUp() {
    y -= 1;
  }

  void moveDown() {
    y += 1;
  }

  void moveRight() {
    x += 1;
  }

  void moveLeft() {
    x -= 1;
  }

  void setBomb() {
    Bomb bomb = new Bomb(x, y);
  }
}
