import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * This is the main class for Space Invaders.
 * @author Christopher Hurt & Travis Jones
 * @version 04072017
 *
 */
@SuppressWarnings("serial")
public class SI extends JFrame {
    private SIPanel panel;
    private JMenuItem pause;
    private JMenuItem resume;
    
    /**
     * Constructor for the SI Frame. It will add a JPanel to the
     * center and add a menu with menu items.
     */
    public SI() {
        super("Space Invaders");
       
        this.setLayout(new BorderLayout());
        
        panel = new SIPanel();
        add(panel, BorderLayout.CENTER);
        
        setSize(500, 400);
        this.setResizable(false);
        
        JMenuBar menu = new JMenuBar();
        
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        game.add(newGame);
        game.addSeparator();
        pause = new JMenuItem("Pause");
        pause.setEnabled(true);
        resume = new JMenuItem("Resume");
        resume.setEnabled(false);
        game.add(pause);
        game.add(resume);
        game.addSeparator();
        JMenuItem quit = new JMenuItem("Quit");
        game.add(quit);
        
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About...");
        help.add(about);
        
        
        menu.add(game);
        menu.add(Box.createGlue()); //Moves help to the right side
        menu.add(help);
        
        //Make menu options do stuff
        newGame.addActionListener(e->newGame());
        pause.addActionListener(e->pause());
        resume.addActionListener(e->resume());
        quit.addActionListener(e->quit());
        about.addActionListener(e->about());
        
        setJMenuBar(menu);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent args0) {
                quit();
            }
        });
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    /**
     * This method pauses the game. It will show a
     * JOptionPane dialog box asking the user if they
     * would like to start a new game. If yes, this
     * window is disposed and a new one is opened.
     */
    public void newGame() {
        pause();
        int result = JOptionPane.showConfirmDialog(this, "Start a new game?", "Confirm", JOptionPane.YES_NO_OPTION);
       
        if(result == JOptionPane.YES_OPTION) {
            //Start new game
            dispose();
            main(new String[] {});
        }
    }
    
    /**
     * The timer from the panel is stopped. Resume
     * button is able to be pressed and pause is not.
     */
    public void pause() {
        //Stop timer
        panel.timer.stop ();
        resume.setEnabled(true);
        pause.setEnabled(false);
    }
    
    /**
     * The timer from the panel is resumed. Resume button
     * is not able to be pressed and pause is.
     */
    public void resume() {
        //Start timer
        panel.timer.restart();
        resume.setEnabled(false);
        pause.setEnabled(true);
    }
    
    /**
     * The game is paused. A JOptionPane dialog will ask
     * the user if they would like to quit. If yes, the
     * sound is stopped and the window is disposed.
     * Otherwise, the game is resumed.
     */
    public void quit() {
        pause();
        int result = JOptionPane.showConfirmDialog(this, "Dare to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if(result == JOptionPane.YES_OPTION) {
            dispose();
        } else
            resume();
    }
    
    /**
     * The game is paused. A JOptionPane dialog will
     * inform the user of the creators and the name
     * of the game. The game is then resumed.
     */
    public void about() {
        pause();
        
        JOptionPane.showMessageDialog(this, "SpaceInvaders \n Christopher Hurt & Travis Jones", "About...", JOptionPane.INFORMATION_MESSAGE);
        
        resume();
    }
    
    public static void main(String[] args) {
        JFrame f = new SI();
        f.setVisible(true);
    }
}
