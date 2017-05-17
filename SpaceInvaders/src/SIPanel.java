import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Random;

/**
 * Space Invaders Panel class.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
@SuppressWarnings("serial")
public class SIPanel extends JPanel {
    protected static final int MAX_INVADERS = 50;
    
    private boolean left;
    private boolean right;
    private Integer score;
    
    private SIbase base;
    private SImissile missile;
    protected int invadersHit;
    
    private SImystery mystery;
    protected int side;

    private SIinvader[][] invaders = new SIinvader[5][10];
    private SImissile[] eWeapons = new SImissile[3];
    protected boolean movingRight = true;
    protected int indexOfRightmostInvader = 9;
    protected int indexOfLeftmostInvader = 0;
    protected int indexOfTopInvader = 0;
    private int invaderLevel = 0;
    
    protected Timer timer;
    private int ticker = 0;
    private int invaderPace = 0;
    private int invaderPulse = 40;
    
    /**
     * Constructor for the SIPanel class. Sets the score of the game
     * to 0. Left and right, used for key presses, are false. Initializes
     * a new base. Sets a new wave of enemy invaders. Sets the background
     * color to black. Plays the music for the game on loop. Initializes
     * a timer to control events in the game. Adds key listeners, which
     * controls the base.
     */
    public SIPanel() {
        score = 0;
        left = right = false;
        base = new SIbase(250, 330);
        setInvaders(invaders);
        setBackground(Color.BLACK);
        
        timer = new Timer(10, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			    //Advance enemy pace
			    invaderPace++;
			    ticker++;
			    
			    //Move base if key pressed
				if (left) base.moveXBy(-5, 470);
				if(right) base.moveXBy(5, 470);
				//Move base missile if it exists
				if(missile!=null) missile.moveYBy(-5, 330);
				//Move mystery ship if it exists
				if(mystery!=null & ticker == 2){
				    
					ticker = 0;
					if(side == 0){
						mystery.moveXBy(3, 470);
						if(mystery.getX()>=470){
							mystery = null;
						}
					}else{
						mystery.moveXBy(-3, 470);
						if(mystery.getX()<=0){
							mystery = null;
						}
					}
				}
				if(ticker>2){
					ticker = 0;
				}
				
				//See if a mystery ship is created
				if(mystery == null){
					Random r = new Random();
					if(r.nextInt(1000)> 996){
						if(r.nextInt(2) == 0){
							mystery = new SImystery(0,50);
							side = 0;
						}else{
							mystery = new SImystery(470,50);
							side = 1;
						}
						
					}
				}
				
				
				//If a missile can be shot, then it is
				Random rand = new Random();
				if(rand.nextInt(10) < 8 && invaderPace%invaderPulse==0) {
				    boolean shot = false;
				    for(int i = 0; i < 3; i++) {
				        if(eWeapons[i] == null && !shot) {
				            enemyShoot(i);
				            shot = true;
				        }
				    }
				}
				//Move enemy missiles if they exist
				for(int i = 0; i < 3; i++) {
				    if(eWeapons[i] != null) {
				        eWeapons[i].moveYBy(2.5, 330);
				    }
				}
				
				repaint();
			}
			
		});
		timer.start();
		
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
    				case KeyEvent.VK_RIGHT : right = true; break;
    				case KeyEvent.VK_LEFT : left = true; break;
    				case KeyEvent.VK_SPACE : {
    				    if(missile == null) {
    				        base.playSound();
    				        missile = new SImissile(base.getX()+14, base.getY(), 10, 2);
    				    }
    				    break;
    				}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e){
				switch(e.getKeyCode()) {
    				case KeyEvent.VK_RIGHT : right = false; break;
    				case KeyEvent.VK_LEFT : left = false; break;
				}
			}
		});
		setFocusable(true);
		
	}
    
    //Creates new missile at a randomly chosen column
    //if an invader exists at that column
    /**
     * Creates a new missile at a randomly chosen column
     * if an invader exists at the column. Else, it is called
     * again until a shot is fired.
     * @param pos - integer position of the enemy missile array
     */
    protected void enemyShoot(int pos) {
        boolean successfulFire = false;
        Random rand = new Random();
        int col = rand.nextInt(10);
        for(int i = 0; i < 5; i++) {
            SIinvader inv = invaders[i][col];
            if(inv != null && inv.canShoot) {
                successfulFire = true;
                eWeapons[pos] = new SImissile(inv.x+14, inv.y, 10, 2);
            }
        }
        if(!successfulFire && checkNewWave()) enemyShoot(pos);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		base.paintMe(g2);
		
		writeScore(g2);
		
		//Draw the missile if there is one to be drawn
		if(missile != null) {
		    missile.paintMe(g2);
		    if(missile.y-5 <= 0) missile = null;
		    checkForCollision();
		}
		
		//Draw invaders every pulse
		if(mystery!=null){
			mystery.paintMe(g2);
			if(mystery.ship.equals(mystery.getExplode())) mystery = null;
		}
		for(int i = 0; i < 5; i++)
            for(int j = 0; j < 10; j++)
                if(invaders[i][j] != null)
                    invaders[i][j].paintMe(g2);
		
		//Change the image of invaders every invader pulse
		if(invaderPace % invaderPulse == 0) {
		    invaderPace = 0;
		    changeInvaderImage();
		    moveInvaders();
		    if(invaderLevel == 11) gameOver(g2);
		}
		
		//Draw enemy missiles
		for(int i = 0; i < 3; i++) {
		    if(eWeapons[i] != null) {
		        eWeapons[i].paintMe(g2);
		        checkForEnemyMissileCollision(g2);
		        if(eWeapons[i].y+2.5 >= 330) {
                    eWeapons[i] = null;
                }
		    }
		}
		
		
    }
    
    /**
     * This method checks to see if an enemy invader's
     * missile collides with the base. If it does, the
     * game is over. Else, continue playing.
     * @param g2 - Graphics2D to paint in gameOver().
     */
    private void checkForEnemyMissileCollision(Graphics2D g2) {
        for(int i = 0; i < 3; i++) {
            SImissile m = eWeapons[i];
            if(m != null) {
                if(m.y >= base.y-2 &&(m.x >= base.x && m.x < base.x + 28)) {
                    gameOver(g2);
                }
            }
        }
    }

    /**
     * Draws the score in the upper right corner
     * of the screen.
     * @param g2 - Graphics2D to draw on the screen.
     */
    private void writeScore(Graphics2D g2) {
        String msg = "Score: " + score.toString();
        Font f = new Font("Comic Sans MS", Font.BOLD, 12);
        g2.setColor(Color.GREEN);
        g2.setFont(f);
        g2.drawString(msg, 425-(msg.length()+10), 25);
    }
    
    /**
     * Moves every invader right, if they are moving right.
     * Else, they are moved left (negative x-direction). If
     * the invaders reach the edge of the screen, they will
     * move down.
     */
    private void moveInvaders() {
        boolean moveDown = false;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(invaders[i][j] != null) {
                    if(invaders[i][j].getX() + 5 > 470) {
                        movingRight = false;
                        moveDown = true;
                    }
                    if(invaders[i][j].getX() - 5 < 0) {
                        movingRight = true;
                        moveDown = true;
                    }
                
                }
            }
        }
        
        if(moveDown) moveInvadersDown();
        
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(invaders[i][j] != null) {
                    if(movingRight) {
                        invaders[i][j].moveXBy(5, 470);
                    } else {
                        invaders[i][j].moveXBy(-5, 470);
                    }
                    if(invaders[i][j].hit) invaders[i][j] = null;
                }
            }
        }        
    }
    
    /**
     * Moves the invaders down 12 pixels in the y-direction.
     * Speeds up the game by 80%. Increments the invader level,
     * as the game ends when the level reaches 11.
     */
    private void moveInvadersDown() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(invaders[i][j] != null) 
                    invaders[i][j].moveYBy(12, 330);
            }
        }
        invaderPulse *= 0.8;
        invaderLevel++;
    }
    
    /**
     * Changes the image of each invader.
     */
    private void changeInvaderImage() {
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 10; j++)
                if(invaders[i][j] != null)
                    invaders[i][j].changeImage();
    }
    
    /**
     * Initializes a new wave of invaders into a 2D array.
     * @param invaders - 2D array of invaders for initializing
     */
    private void setInvaders(SIinvader[][] invaders) {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 10; j++) {
                if(i == 0) {
                    invaders[i][j] = new SItop((j*35)+75,(i*25)+80);
                } else if(i == 1 || i == 2) {
                    invaders[i][j] = new SImiddle((j*35)+75, (i*25)+80);
                } else {
                    invaders[i][j] = new SIbottom((j*35)+75, (i*25)+80);
                }
            }
        }
    }

    /**
     * Retrieves a sound file from the given name.
     * @param name - Name of the given file
     * @return Returns an AudioClip of the sound from the file.
     */
    protected AudioClip getSound(String name){
        URL file = getClass().getResource(name);
        return Applet.newAudioClip(file);
    }
    
    /**
     * Checks if the base missile collides with any invaders.
     * If it does, the following happens:
     * 1.Enemy invader explodes, and sets hit to true.
     * 2.Base missile is now null, and another can be fired.
     * 3.The amount of invaders hit is incremented.
     * 4.The score is increased by the invader's points.
     * 5.Check to see if a new wave of invaders should be created.
     * 6.The invader above the one hit can now shoot missiles.
     */
    private void checkForCollision(){
        for(int i = 0; i<5; i++){
            for(int j = 0; j<10; j++){
                if(invaders[i][j] != null && missile != null) {
                    if(missile.getY() >= invaders[i][j].getY()-10 && missile.getY() <= invaders[i][j].getY()){
                         if(missile.getX() >= invaders[i][j].getX() && missile.getX() <invaders[i][j].getX() + 28){
           					invaders[i][j].explosion();
          					invaders[i][j].hit = true;
           					missile = null;
           					invadersHit++;
           					score += invaders[i][j].points;
           					checkNewWave();
           					if(i-1 >= 0 && invaders[i-1][j] != null) {
           					    invaders[i-1][j].canShoot = true;
           					}
           	 			}
                    }
                }
            }
        }
        if(mystery!=null && missile!=null){
        	if(missile.getY() >= mystery.getY()-10 && missile.getY()<=mystery.getY()){
        		if(missile.getX() >= mystery.getX() && missile.getX() < mystery.getX() + 28){
        			mystery.explosion();
        			mystery.changeImage();
        			score += mystery.points;
        			missile = null;
        		}
        	}
        }
    }
    
    /**
     * Checks to see if a new wave of invaders should
     * be created. A new wave is created if there are
     * no missiles on the board, no mystery ship, and
     * all invaders are gone.
     * @return Returns true if a new wave has been created.
     */
   private boolean checkNewWave() {
       SImissile m1 = eWeapons[0];
       SImissile m2 = eWeapons[1];
       SImissile m3 = eWeapons[2];
       if(invadersHit == MAX_INVADERS && mystery == null && m1 == null && m2 == null && m3 == null) {
    	   invadersHit = 0;
           createNewWave();
           return true;
       }
       return false;
   }
   
   /**
    * Creates a new wave of invaders. Initializes a new
    * wave of invaders. Resets the pace and pulse of the
    * invaders.
    */
   private void createNewWave() {
       setInvaders(invaders);
       invaderPace = 0;
       invaderPulse = 40;
   }
    
   /**
    * Draws "GAME OVER" on the screen, in the center.
    * Stops the timer. Changes the image of the base
    * to an exploded one. Paints the new base.
    * @param g2 - Graphics2D used to draw the base and the text.
    */
    private void gameOver(Graphics2D g2) {
        timer.stop();
        base.setShip(base.baseDead);
        base.paintMe(g2);
        
        String msg = "GAME OVER";
        Font f = new Font("Comic Sans MS", Font.BOLD, 32);
        
        g2.setFont(f);
        g2.setColor(Color.GREEN);
        g2.drawString(msg, 160, 165);
    }
    
}
