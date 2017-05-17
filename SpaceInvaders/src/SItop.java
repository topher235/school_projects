import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * Class for the top row of invaders.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SItop extends SIinvader {
    protected Image top;
    protected Image top0;
    protected Image top1;
    
    /**
     * Constructor for the SItop class. Sets the x,y position.
     * Retrieves the two images for the top row of invaders.
     * Sets the current image to the first. Sets the amount
     * of points to 30.
     * @param x - integer for the x position
     * @param y - integer for the y position
     */
    public SItop(int x, int y){
        super(x, y);
        top0 = super.getImage("SItop0.gif");
        setShip(top0);
        top1 = super.getImage("SItop1.gif");
        super.points = 30;
    }
    
    @Override
    public void setShip(Image ship) {
    	super.setShip(ship);
    }
    
    @Override
    public int getX(){
        return x;
    }
    
    @Override
    public int getY(){
        return y;
    }
    
    @Override
    public void moveXBy(int move, int max){
        super.moveXBy(move, max);
        x = super.x;
    }
    
    /**
     * Moves the top row of invaders.
     * @param move - integer amount to move by
     */
    public void moveYBy(int move) {
        y += 0;
    }
    
    @Override
    public void changeImage() {
    	if(ship!=null){
    		if(ship.equals(top0)) {
    			ship = top1;
    		}
    		else if(ship.equals(top1)) {
    			ship = top0;
    		}
    	}
    }
    
    @Override
    protected void paintMe(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(super.hit){
        	Rectangle2D.Double rect = new Rectangle2D.Double(x, y, 15, 15);
        	g2.setColor(Color.BLACK);
        	g2.fill(rect);
        	g2.draw(rect);
        }
        g2.drawImage(ship, super.x, super.y, null);
    }
}
