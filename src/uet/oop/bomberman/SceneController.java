package uet.oop.bomberman;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.movingEntity.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class SceneController{
    public String status = "open";
    GraphicsContext gc;
    public VBox prepare() {
        Canvas canvas = new Canvas(992, 416);
        //create menu bar
        MenuBar menuBar = new MenuBar();
        Menu game = new Menu("Game");
        Menu help = new Menu("Help");
        MenuItem howToPlay = new MenuItem("How To Play?");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem quit = new MenuItem("Quit");
        game.getItems().addAll(newGame, quit);
        help.getItems().add(howToPlay);
        menuBar.getMenus().addAll(game, help);
        howToPlay.setOnAction(event -> {
            VBox secondaryLayout = new VBox();

            String playText = "   Use key W-A-S-D to move bomberman and press ENTER to set bomb\n   You will get to the next level by Portal after eliminating all enemies\n";
            Label text = new Label(playText);
            Scene secondScene = new Scene(secondaryLayout, 400, 50);
            secondaryLayout.getChildren().add(text);
            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.setTitle("How To Play");

            newWindow.setScene(secondScene);
            newWindow.show();
        });
        quit.setOnAction(event -> {
            System.out.println("quit");
            status = "close";
        });
        newGame.setOnAction(event -> {
            System.out.println("new game");
            status = "new-game";
        });
        //tao container root
        gc = canvas.getGraphicsContext2D();
        VBox root = new VBox();
        root.getChildren().add(menuBar);
        root.getChildren().add(canvas);
        //quit.setOnAction(e -> System.out.println("Clicked"));
        return root;
    }

}
