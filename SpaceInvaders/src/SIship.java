import java.applet.AudioClip;
import java.awt.Image;

/**
 * This is an extension of SIthing. It is a super class for invaders and the base.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public abstract class SIship extends SIthing {
    protected boolean hit;
    protected AudioClip boom;
    protected Image explode;
    protected Image ship;
    
    /**
     * Constructor for the SIship. Sets the x, y position and
     * the height, width. Retrieves the sound for if the ship gets hit.
     * @param x - integer for the x position
     * @param y - integer for the y position
     * @param height - integer for the height
     * @param width - integer for the width
     */
    public SIship(int x, int y, int height, int width) {
        super(x, y, height, width);
        boom = super.getSound("SIshipHit.wav");
    }
    
    /**
     * Sets the image of the ship to the given image.
     * @param ship - Image of the ship that will be drawn.
     */
    protected void setShip(Image ship) {
    	this.ship = ship;
    }
    
    /**
     * Plays the sound of explosion if the ship gets hit.
     */
    protected void explosion() {
        boom.play();
        
    }
    
    @Override
    public void moveXBy(int move, int max){
        super.moveXBy(move, max);
    }
}
