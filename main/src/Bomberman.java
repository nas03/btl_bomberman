package src;
import src.main.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Bomberman extends Application {

    private Canvas canvas;

    private GraphicsContext gc;

    public static void main(String[] args) {
        Application.launch(Bomberman.class);

    }

  @Override
  public void start(Stage stage) {
      canvas = new Canvas(720, 1080);
      gc = canvas.getGraphicsContext2D();

      // Tao root container
      Group root = new Group();
      root.getChildren().add(canvas);

      // Tao scene
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();

      AnimationTimer timer = new AnimationTimer() {
          @Override
          public void handle(long l) {

              render();
              update();
          }
      };
      timer.start();
      Map map = new Map();
      createMap();
      /*Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
      entities.add(bomberman);*/

  }
}
