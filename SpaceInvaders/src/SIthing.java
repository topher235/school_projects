import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Super class of everything drawn on the screen.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
public abstract class SIthing {
    protected int x;
    protected int y;
    protected int height;
    protected int width;
    protected boolean movingRight = true;
    
    /**
     * Constructor for SIthing class. Sets the x,y position and
     * the height, width.
     * @param x - integer for the x position
     * @param y - integer for the y position
     * @param height - integer for the height
     * @param width - integer for the width
     */
    public SIthing(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    
    /**
     * Returns the x position of the thing.
     * @return integer for the x position
     */
    protected int getX() {
        return this.x;
    }
    
    /**
     * Returns the y position of the thing.
     * @return integer for the y position
     */
    protected int getY() {
        return this.y;
    }
    
    /**
     * Returns the height of the thing.
     * @return integer for the height
     */
    protected int getHeight() {
        return this.height;
    }
    
    /**
     * Returns the width of the thing.
     * @return integer for the width
     */
    protected int getWidth() {
        return this.width;
    }
    
    /**
     * Sets the x position of the thing.
     * @param x - integer for the x position
     */
    protected void setX(int x) {
        this.x = x;
    }
    
    /**
     * Sets the y position of the thing.
     * @param y - integer for the y position
     */
    protected void setY(int y) {
        this.y = y;
    }
    
    /**
     * Sets the height of the thing.
     * @param height - integer for the height
     */
    protected void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Sets the width of the thing.
     * @param width - integer for the width
     */
    protected void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Method for painting the thing onto the screen.
     * @param g - Graphics for drawing on the screen.
     */
    protected abstract void paintMe(Graphics g);
    
    /**
     * Moves the thing in the x direction.
     * @param move - integer amount to move the thing
     * @param max - max amount the thing can move (the edge of the panel)
     */
    public void moveXBy(int move, int max){        
       x += move;
    }
    
    /**
     * Moves the thing in the y direction.
     * @param move - integer amount to move the thing.
     * @param max - max integer amount the thing can move (the edge of the panel)
     */
    public void moveYBy(double move, int max) {
        if(y + move <= max) {
            y += move;
        }
    }
    
    /**
     * Retrieves an image from a file.
     * @param name - name of the image file
     * @return Returns the image from the file
     */
    protected Image getImage(String name) {
        java.net.URL url = getClass().getResource(name);
        ImageIcon icon = new ImageIcon(url);
        return icon.getImage();
    }
    
    /**
     * Retrieves a sound from a file.
     * @param name - name of the sound file
     * @return Returns the AudioClip from the file
     */
    protected AudioClip getSound(String name){
        URL file = getClass().getResource(name);
        return Applet.newAudioClip(file);
    }

}
