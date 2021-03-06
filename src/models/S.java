/**
 * S.java:
 * Creates an S-shaped tetronimo
 */
package models;

import java.awt.*;

public class S extends Tetronimo {
    /**
     * Creates the tetronimo and puts it in the S orientation
     */
    public S(){
        super.r1.setLocation( 0, Tetronimo.SIZE );
        super.r2.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
        super.r3.setLocation( Tetronimo.SIZE, 0 );
        super.r4.setLocation( Tetronimo.SIZE * 2, 0 );

        super.add( r1 );
        super.add( r2 );
        super.add( r3 );
        super.add( r4 );

        this.ident = "S";
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
            super.r2.setLocation( 0, Tetronimo.SIZE );
            super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
            super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE * 2 );
        }
        else
        {
            super.r1.setLocation( 0, Tetronimo.SIZE );
            super.r2.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
            super.r3.setLocation( Tetronimo.SIZE, 0 );
            super.r4.setLocation( Tetronimo.SIZE * 2, 0 );
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
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE * 3;
        }
        else
        {
            return Tetronimo.SIZE * 2;
        }
    }

    /**
     * Gets the piece's width
     * @return Width (in pixels, not blocks)
     */
    @Override
    public int getWidth()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE * 2;
        }
        else
        {
            return Tetronimo.SIZE * 3;
        }
    }
}
