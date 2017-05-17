import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Class for the middle rows of invaders.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SImiddle extends SIinvader {
    protected Image mid;
    protected Image mid0;
    protected Image mid1;

    /**
     * Constructor for the SImiddle class (the middle row of invaders).
     * Sets the x and y position of the invader. Retrieves the image
     * of the two middle invaders. Sets the first image. Sets the
     * amount of points to 20.
     * @param x
     * @param y
     */
    public SImiddle(int x, int y){
        super(x, y);
        mid0 = super.getImage("SImiddle0.gif");
        setShip(mid0);
        mid1 = super.getImage("SImiddle1.gif");
        super.points = 20;
    }
    
    @Override
    protected void setShip(Image ship) {
    	super.setShip(ship);
    }
    
    @Override
    protected int getX() {
        return x;
    }
    
    @Override
    public void moveXBy(int move, int max){
        super.moveXBy(move, max);
    }

    @Override
    protected int getY() {
        return y;
    }

    @Override
    protected void paintMe(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(ship, super.x, super.y, null);
    }

    @Override
    protected void changeImage() {
        if(ship == mid0) ship = mid1;
        else if(ship == mid1) ship = mid0;
    }
}
