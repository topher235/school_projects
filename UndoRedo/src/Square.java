import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Square {
        private Color color;
        private Rectangle2D.Double rectangle;
        
        public Square(int x, int y) {
            rectangle = new Rectangle2D.Double(x+25, y+25, 10, 10);
            Random random = new Random();
            color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
        
        public void paintMe(Graphics2D g2) {
            g2.setColor(color);
            g2.fill(rectangle);
        }
    }