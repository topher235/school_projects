import java.awt.*;
/**
 * This is the super class of all invader classes.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public abstract class SIinvader extends SIship {
    protected int points;
    protected Image explode;
    protected boolean canShoot = false;
    
    /**
     * Constructor for the SIinvader class.
     * Retrieves the image for when the invader
     * explodes. Sets the x and y position.
     * @param x - integer for the x position
     * @param y - integer for the y position
     */
    public SIinvader(int x, int y) {
        super(x, y, 0, 0);
        explode = super.getImage("SIinvaderBlast.gif");
    }
    
    /**
     * Used to change the image of the invader.
     */
    protected abstract void changeImage();
    
    /**
     * Gets the x position of the invader.
     * @return integer x position of the invader
     */
    protected abstract int getX();
    
    /**
     * Gets the y position of the invader.
     * @return integer y position of the invader.
     */
    protected abstract int getY();
    
    /**
     * Returns the image of the invader when it explodes.
     * @return Image of invader explosion
     */
    public Image getExplode(){
    	return explode;
    }
    
    @Override
    public void explosion() {
    	super.explosion();
    	setShip(explode);
    }
    
    @Override
    public void moveXBy(int move, int max){
        super.moveXBy(move, max);
    }
}
