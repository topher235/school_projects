

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class ColoredSquare implements Cloneable {
    private final static Random  RANDOM = new Random();
    private final static Color[] COLORS = { 
            Color.DARK_GRAY, 
            Color.BLACK, 
            Color.BLUE, 
            Color.CYAN, 
            Color.GRAY, 
            Color.GREEN, 
            Color.LIGHT_GRAY,  
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK, 
            Color.RED, 
            Color.YELLOW }; 

    private Color     color;
    private Rectangle square;
    
    public ColoredSquare(Point point) {
        this.square = new Rectangle(point.x, point.y, 10, 10);
        this.color = COLORS[ RANDOM.nextInt( COLORS.length ) ];
    }
    public Point getLocation() {
        return square.getLocation();
    }
    public void paintMe(Graphics2D g2) {
        g2.setColor( color );
        g2.fill    ( square );
    }
    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            }
            if (getClass() == obj.getClass()) {
                ColoredSquare other = (ColoredSquare) obj;
                if (color    ==    other.color &&
                    square.equals( other.square )) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format( "[%s,%s]", color, square.toString() );
    }
    @Override
    protected ColoredSquare clone() {
        ColoredSquare clone = null;
        try {
            clone        = (ColoredSquare)super.clone();
            clone.square = (Rectangle)square.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}