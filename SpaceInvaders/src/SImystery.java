import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

/**
 * This class is the Space Invaders mystery ship.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SImystery extends SIinvader {
    protected Image ship;
    protected AudioClip sound;
    protected int[] possiblePoints = {50, 100, 150, 300};
    
    /**
     * Constructor for the SImystery class. Sets the x, y position.
     * Retrieves the image for the mystery ship. Retrieves the sound
     * for when the ship is present. Sets the points and direction.
     * @param x - integer for x position
     * @param y - integer for y position
     */
    public SImystery(int x, int y){
        super(x, y);
        ship = super.getImage("SImystery.gif");
        sound = super.getSound("SImystery.wav");
        explode = super.getImage("SIinvaderBlast.gif");
        setPoints();
    }
    
    
    /**
     * Randomly determines the number of points
     * this ship will be worth. Using the possiblePoints
     * field array.
     */
    private void setPoints() {
        Random rand = new Random();
        int index = rand.nextInt(4);
        super.points = possiblePoints[index];
    }
    
    @Override
    protected int getX() {

        return x;
    }

    @Override
    protected int getY() {

        return y;
    }

    @Override
    protected void paintMe(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ship, x, y, null);
        
    }
    
    @Override
    public void moveXBy(int move, int max) {
        super.moveXBy(move, max);
    }
    
    /**
     * Plays the sound of the mystery ship.
     */
    public void playSound(){
		sound.play();
	}

    @Override
    protected void changeImage() {
        // TODO Auto-generated method stub
        ship = explode;
    }

}
