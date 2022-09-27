package src.main;
import src.entity.*;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class GameConfig extends JPanel  {
    //Thông số của màn hình
    final int Game_FPS = 60;

    public final int originalSize = 16; // 16*16 : kích thước đối tượng + nhân vật
    public final int scale = 3;


    public final int adjustedSize = originalSize * scale;
    final int maxScrCol = 15; // 15 brick per Column
    final int maxScrRow = 14; // 14 brick per row
    final int SCREEN_WIDTH = adjustedSize * maxScrCol;// 630
    final int SCREEN_HEIGHT = adjustedSize * maxScrRow;//
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this,keyH);


    public GameConfig() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // All the drawing from this components will be done in an offscreen painting buffer (Improve game's rendering performance)
        this.addKeyListener(keyH);
        this.setFocusable(true);// Game nhận input từ bàn phím
    }


    public void paintComponents(Graphics g){
        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D)g;
        Player.render(g2);
    }

    public void update(){
        Player.update();
    }



}