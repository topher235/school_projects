import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


/**
 * This is an extension of SIthing. The Missile class.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SImissile extends SIthing {
    protected Rectangle2D.Double rect = null;
    
    /**
     * Constructor for the SImissile. Creates a rectangle with
     * the x, y position given and the height, width given.
     * @param x - integer x position
     * @param y - integer y position
     * @param height - integer for height of a rectangle
     * @param width - integer for width of a rectangle
     */
    public SImissile(int x, int y, int height, int width) {
        super(x, y, height, width);
        rect = new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    protected void paintMe(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fill(rect);
        g2.draw(rect);
    }
    
    @Override
    public void moveYBy(double move, int max) {
        super.moveYBy(move, max);
        rect = new Rectangle2D.Double(x, y, width, height);
    }

}
