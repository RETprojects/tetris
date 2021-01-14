/**
 * Square.java:
 * Creates a square-shaped tetronimo
 */
package models;

import java.awt.*;

public class Square extends Tetronimo {
    /**
     * Creates the tetronimo
     */
    public Square(){
        super.r1.setLocation(0,0);
        super.r2.setLocation(0,Tetronimo.SIZE);
        super.r3.setLocation(Tetronimo.SIZE,0);
        super.r4.setLocation(Tetronimo.SIZE,Tetronimo.SIZE);

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );

        this.ident = "Square";
    }

    /**
     * Rotates the piece
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();
        super.setLocation( 0, 0 );

        if( super.curRotation % 2 == 0 )
        {
            super.r1.setLocation( 0, 0 );
            super.r2.setLocation( Tetronimo.SIZE, 0 );
            super.r3.setLocation( 0, Tetronimo.SIZE );
            super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        }
        else
        {
            super.r1.setLocation( 0, 0 );
            super.r2.setLocation( 0, Tetronimo.SIZE );
            super.r3.setLocation( Tetronimo.SIZE, 0 );
            super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        }

        super.setLocation( curLoc );
    }

    /**
     * Gets the piece's height
     * @return Height (in pixels, not blocks)
     */
    @Override
    public int getHeight()
    {
        return Tetronimo.SIZE * 2;
    }

    /**
     * Gets the piece's width
     * @return Width (in pixels, not blocks)
     */
    @Override
    public int getWidth()
    {
        return Tetronimo.SIZE * 2;
    }
}
