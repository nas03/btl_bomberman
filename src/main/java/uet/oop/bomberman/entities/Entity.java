package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Entity {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x = 1;
    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y = 1;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

}
