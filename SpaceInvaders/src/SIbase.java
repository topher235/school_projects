import java.applet.AudioClip;
import java.awt.*;


/**
 * This is the Space Invaders Base class.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public class SIbase extends SIship{
    private Image baseAlive;
    protected Image baseDead;
    private AudioClip sound;
    
    /**
     * Constructor for SIbase. Retrieves images for if the
     * base is alive or dead. Retrieves sound for if the
     * base shoots.
     * @param x - integer for the x position
     * @param y - integer for the y position
     */
    public SIbase(int x, int y){
        super(x, y, y, y);
        baseAlive = super.getImage("SIbase.gif");
        setShip(baseAlive);
        baseDead = super.getImage("SIbaseBlast.gif");
        sound = super.getSound("SIbaseShoot.wav");
    }
    
    
    @Override
    protected void paintMe(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(ship, x, y, null);
	}
    
    @Override
	protected int getX(){
		return x;
	}
    
    @Override
	protected int getY(){
		return y;
	}		
	
	public void playSound(){
		sound.play();
	}
	
	@Override
	public void moveXBy(int move, int max){
	    if(x + move <= max && x + move >= 0) {
            x += move;
        }
	}
}
