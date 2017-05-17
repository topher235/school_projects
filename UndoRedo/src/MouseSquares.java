

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * This program will add squares to the screen
 * where the mouse is clicked.
 * @author Christopher Hurt
 * @version 04222017
 *
 */
@SuppressWarnings("serial")
public class MouseSquares extends JFrame {
    private Stack<ColoredSquare> squareList = new Stack<ColoredSquare>();
    private Stack<Command> undoList = new Stack<Command>();
    private Stack<Command> redoList = new Stack<Command>();
    private JMenuItem undo;
    private JMenuItem redo;
    
    /**
     * This class implements the Command class and will
     * add and remove colored squares from a stack.
     * @author Christopher Hurt
     * @version 04222017
     *
     */
    private class Add implements Command {
        private ColoredSquare s;
        
        /**
         * Constructor for Add.
         * @param s - Colored Square to be added.
         */
        public Add(ColoredSquare s) {
            this.s = s;
        }
        
        /**
         * Pushes the Colored square onto the painted stack.
         * @return this Command
         */
        @Override
        public Command execute() {
            squareList.push(s);
            return this;
        }

        /**
         * Pops the top colored square off the painted stack.
         * @return this Command
         */
        @Override
        public Command undo() {
            squareList.pop();
            return this;
        }
        
    }
    
    /**
     * This class extends JPanel and will be used for
     * drawing colored squares and mouse events.
     * @author Christopher Hurt
     * @version 04222017
     *
     */
    public class MouseSquaresPanel extends JPanel {
        private ColoredSquare rectangle = null;
        
        {
            addMouseListener(new MouseAdapter() {
                /**
                 * Creates a new rectangle. Adds the rectangle to
                 * the painted stack. Sets undo to enabled and redo
                 * to not enabled.
                 */
                public void mouseClicked(MouseEvent e) {
                    rectangle = new ColoredSquare(new Point(e.getX(), e.getY()));
                    add(rectangle);
                    repaint();
                    undo.setEnabled(true);
                    redo.setEnabled(false);
                }
             });
        }
         
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            for(ColoredSquare s : squareList)
                s.paintMe(g2);
        }
        
        /**
         * Pops a Command from the undo stack and pushes that
         * Command to the redo stack. The undo command will be done.
         * If the undo stack is empty
         * the undo button will be set to not enabled. The redo
         * button will always be set to enabled.
         */
        public void undo() {
            Command cmd = undoList.pop();
            redoList.push(cmd.undo());
            repaint();
            
            if(undoList.isEmpty())
                undo.setEnabled(false);
            redo.setEnabled(true);
        }
        
        /**
         * Pops a Command from the redo stack and pushes that
         * Command to the undo stack. The Command will be executed.
         * If the redo stack is empty, the redo button will be set
         * to enabled. The undo button will always be enabled.
         */
        public void redo() {
            Command cmd = redoList.pop();
            undoList.push(cmd.execute());
            repaint();
            
            if(redoList.isEmpty())
                redo.setEnabled(false);
            undo.setEnabled(true);
        }
        
        /**
         * Executes the Command. Pushes that Command onto
         * the undo stack. Clears the redo stack.
         * @param cmd - Command to be executed.
         */
        private void execute(Command cmd) {
            undoList.push(cmd.execute());
            redoList.clear();
        }
        
        /**
         * Colored Square will be added to the painted
         * stack.
         * @param s - Colored Square to be added.
         */
        public void add(ColoredSquare s) {
            Command cmd = new Add(s);
            execute(cmd);
        }
    }
    
    private MouseSquares() {
        super("MouseSquares");
        setSize(500, 500);
        
        JPanel panel = new MouseSquaresPanel();
        add(panel);
        
        //Initialize the menu
        JMenuBar menu = new JMenuBar();
        JMenu program = new JMenu("Program");
        JMenu edit    = new JMenu("Edit");
        JMenuItem exit = new JMenuItem("Exit");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        redo.setEnabled(false);
        undo.setEnabled(false);
        //Add menu items to menu
        menu.add(program);
        menu.add(edit);
        program.add(exit);
        edit.add(undo);
        edit.add(redo);
        //Make menu items do stuff
        exit.addActionListener(e->exit());
        undo.addActionListener(e->((MouseSquaresPanel) panel).undo());
        redo.addActionListener(e->((MouseSquaresPanel) panel).redo());
        
        setJMenuBar(menu);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    /**
     * Returns the stack of painted squares.
     * @return Stack of painted Squares.
     */
    public Stack<ColoredSquare> getSquares() {
        return squareList;
    }
    /**
     * Returns the stack of Commands in the undo stack.
     * @return Stack of Commands in the undo stack.
     */
    public Stack<Command> getUndo() {
        return undoList;
    }
    /**
     * Returns the stack of Commands in the redo stack.
     * @return Stack of Commands in the redo stack.
     */
    public Stack<Command> getRedo() {
        return redoList;
    }
    
    public static void main(String[] args) {
        JFrame f = new MouseSquares();
        f.setVisible( true );
        
    }
    
    /**
     * Disposes the screen when the exit button is clicked.
     */
    private void exit() {
        dispose();
    }
    
}