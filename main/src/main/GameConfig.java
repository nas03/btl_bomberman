package main;
import Objects.TilesManage;

import javax.swing.JPanel;
import java.awt.*;

public class GameConfig extends JPanel  {
    //Thông số của màn hình
    final int Game_FPS = 60;

    public final int originalSize = 16; // 16*16 : kích thước đối tượng + nhân vật
    public final int scale = 3;


    public final int adjustedSize = originalSize * scale;
    public final int maxScrCol = 15; // 15 brick per Column
    public final int maxScrRow = 14; // 14 brick per row
    public final int SCREEN_WIDTH = adjustedSize * maxScrCol;// 630
    public final int SCREEN_HEIGHT = adjustedSize * maxScrRow;//
    KeyHandler keyH = new KeyHandler();
    Bomber player = new Bomber(this,keyH);
    TilesManage tileM = new TilesManage(this);


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
        tileM.render(g2);
        //Player.render(g2);

    }

    //public void update(){
        //Player.update();
    }



