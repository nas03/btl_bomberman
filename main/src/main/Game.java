package src.main;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
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
import src.entity.*;
public class Game {

  GameConfig gc = new GameConfig();
  KeyHandler keyH = new KeyHandler();
  Player player = new Player(gc, keyH);

  Graphics2D g2 = new Graphics2D();
  public void run() {
    render();
    update();
  }

  public void update() {
    Player.update();
  }

  public void render() {
    Player.render(g2);
  }

}
