package models;

import wheelsunh.users.Animator;
import wheelsunh.users.Rectangle;
import wheelsunh.users.ShapeGroup;

import java.awt.Color;

/**
 * Tetronimo.java:
 * An abstract class to model the base capaabilities of a tetronimo
 *
 * @author Professor Rossi
 * @version 1.0 July 24, 2020
 *
 * @see java.awt.Color
 */
public abstract class Tetronimo extends ShapeGroup
{
    /**
     * Constant to represent the size of the tetronimo
     */
    public static final int SIZE= 20;

    public Rectangle r1;// was protected
    public Rectangle r2;
    public Rectangle r3;
    public Rectangle r4;

    protected int curRotation = 1;

    public String ident = "";// an identifier which says which type of shape the tetronimo is

    /**
     * Generates the four rectangles for the tetronino and puts them on the screen, they are at the default coordinates
     * to start
     */
    public Tetronimo()
    {
        super();
        this.r1 = new Rectangle();
        this.r1.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r1.setFrameColor( Color.BLACK );

        this.r2 = new Rectangle();
        this.r2.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r2.setFrameColor( Color.BLACK );

        this.r3 = new Rectangle();
        this.r3.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r3.setFrameColor( Color.BLACK );

        this.r4 = new Rectangle();
        this.r4.setSize( Tetronimo.SIZE, Tetronimo.SIZE );
        this.r4.setFrameColor( Color.BLACK );
    }

    /**
     * Increments the rotation of the tetronimo, other classes need to override this to provide the full functionality
     */
    public void rotate()
    {
        this.curRotation++;
    }

    /**
     * Shifts the tetronimo left one row
     */
    public void shiftLeft()
    {
        super.setLocation( super.getXLocation() - Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Shifts the tetronimo right one row
     */
    public void shiftRight()
    {
        super.setLocation( super.getXLocation() + Tetronimo.SIZE, super.getYLocation() );
    }

    /**
     * Returns whether the current rotation of the tetronimo is divisible by two (custom method)
     */
    public boolean evenRotation(){
        boolean even = false;
        if(this.curRotation % 2 == 0)
            even = true;
        return even;
    }
}