import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Bottom row of invaders.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SIbottom extends SIinvader{    
    protected Image bot0;
    protected Image bot1;
    protected AudioClip sound;
    
    /**
     * Constructor for the SIbottom class (the bottom row of invaders).
     * Sets the x and y position. Retrieves the two images for the
     * bottom invader. Sets the point total to 10.
     * @param x - integer for the x position
     * @param y - integer for the y position
     */
    public SIbottom(int x, int y){
        super(x, y);
        canShoot = true;
        bot0 = super.getImage("SIbottom0.gif");
        setShip(bot0);
        bot1 = super.getImage("SIbottom1.gif");
        super.points = 10;
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
    protected int getY() {
        return y;
    }
    
    public void moveXBy(int move, int max){
        super.moveXBy(move, max);
    }

    @Override
    protected void paintMe(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(ship, super.x, super.y, null);        
    }
    
    @Override
    protected void changeImage() {
        if(ship == bot0) ship = bot1;
        else if(ship == bot1) ship = bot0;
    }

}
