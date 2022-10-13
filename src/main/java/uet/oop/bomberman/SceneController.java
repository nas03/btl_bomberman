package uet.oop.bomberman;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public VBox gameMenu() {
        VBox gameMenu = new VBox();
        Button playGame = new Button("playGame");
        Button howToPlay = new Button("How to Play");
        Button quit = new Button("Quit");
        quit.setLayoutX(100);
        quit.setLayoutY(150);
        gameMenu.getChildren().addAll(playGame,howToPlay,quit);
        return gameMenu;
    }

}
