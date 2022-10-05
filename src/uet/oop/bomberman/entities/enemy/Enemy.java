package uet.oop.bomberman.entities.enemy;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;

import java.awt.*;

public class Enemy extends Entity {
    public Enemy(int x, int y, Image img) {
        super(x,y,img);
    }
    @Override
    public void update(){}
}
