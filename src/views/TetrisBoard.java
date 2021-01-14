package views;

import controllers.TetrisController;
import models.Tetronimo;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Professor Rossi
 * @version 1.0 July 24, 2020
 *
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
 */
public class TetrisBoard implements KeyListener//, Animator
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represnet the height of the board
     */
    public static final int HEIGHT = 24;

    private final TetrisController CONTROLLER;
    private Tetronimo tetronimo;
    public Rectangle[][] playingField;

    //AnimationTimer timer;
    public int[][] squares;
    private Rectangle[][] nextPieceField;
    private Tetronimo nextPiece;
    private TextBox scoreField;

    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame (so we can add this class as a key listener for the frame)
     */
    public TetrisBoard( Frame frame )
    {
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        this.buildBoard();

        this.nextPiece = this.CONTROLLER.getNextTetromino();

        this.run();
        // The first piece to fall:

        /*timer = new AnimationTimer(500,this);
        timer.start();*/
        while(!this.CONTROLLER.gameOver()){
            this.run();
        }
        //timer.stop();*/
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard()
    {
        this.playingField = new Rectangle[ WIDTH ][ HEIGHT ];

        for ( int i = 0; i < TetrisBoard.WIDTH; i++ )
        {
            for ( int j = 0; j < TetrisBoard.HEIGHT; j++ )
            {
                this.playingField[ i ][ j ] = new Rectangle();
                this.playingField[ i ][ j ].setLocation( i * 20 + 40, j * 20 );
                this.playingField[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                this.playingField[ i ][ j ].setColor( Color.WHITE );
                this.playingField[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }

        // The score field shows the current score.
        scoreField = new TextBox();
        scoreField.setLocation(250,150);
        scoreField.setWidth(120);
        scoreField.setText("0");

        // The 6x6 field of squares shows the next tetronimo to fall.
        this.nextPieceField = new Rectangle[ 6 ][ 6 ];

        for ( int i = 0; i < 6; i++ )
        {
            for ( int j = 0; j < 6; j++ )
            {
                this.nextPieceField[ i ][ j ] = new Rectangle();
                this.nextPieceField[ i ][ j ].setLocation( i * 20 + 250, j * 20 );
                this.nextPieceField[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                this.nextPieceField[ i ][ j ].setColor( Color.WHITE );
                this.nextPieceField[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }

        this.squares = new int[WIDTH][HEIGHT];
        for(int[] row: squares)
            Arrays.fill(row,0);// initialize all squares with 0 for empty
    }

    /**
     * Starts gameplay and is responsible for keeping the game going (INCOMPLETE)
     */
    public void run()
    {
        this.tetronimo = this.nextPiece;// the current falling piece
        this.tetronimo.setLocation( 40 + (5 * Tetronimo.SIZE), 0 );

        this.nextPiece = this.CONTROLLER.getNextTetromino();
        this.nextPiece.setLocation(290,40);// on nextPieceField

        while( this.CONTROLLER.tetronimoLanded( this.tetronimo ) )
        {
            this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
            Utilities.sleep( 500 );
        }

        /*
         * This next line is a placeholder for now, you need to change this code so when a piece lands
         * the right squares on the board are painted the color of the tetronimo and the teetronimo itself gets hidden
         */
        //this.tetronimo = null;

        // Fill and paint the squares covered by the tetronimo once it lands, then hide the piece.
        for(int row = 0; row < TetrisBoard.HEIGHT; row++){
            for(int col = 0; col < TetrisBoard.WIDTH; col++){
                int x = col * 20 + 40;
                int y = row * 20;
                if(((x == this.tetronimo.r1.getXLocation()) & (y == this.tetronimo.r1.getYLocation()))
                        | ((x == this.tetronimo.r2.getXLocation()) & (y == this.tetronimo.r2.getYLocation()))
                        | ((x == this.tetronimo.r3.getXLocation()) & (y == this.tetronimo.r3.getYLocation()))
                        | ((x == this.tetronimo.r4.getXLocation()) & (y == this.tetronimo.r4.getYLocation()))){
                    squares[col][row] = 1;
                    this.playingField[col][row].setFillColor(Color.RED);
                    this.playingField[col][row].setFrameColor(Color.BLACK);
                }
            }
        }
        // Hide each square making up the tetronimo:
        this.tetronimo.r1.hide();
        this.tetronimo.r2.hide();
        this.tetronimo.r3.hide();
        this.tetronimo.r4.hide();

        //this.tetronimo.hide();
        
        // Check if four rows can be cleared (a Tetris). If not, check if a single row can be cleared.
        this.CONTROLLER.clearFourRows();

        // Update the score as necessary:
        String scoreText = String.valueOf(this.CONTROLLER.score);
        scoreField.setText(scoreText);
    }

    /**
     * Getter method for the array representing the playing field, not used yet but will be needed by the controller later
     *
     * @return The playing field
     */
    public Rectangle[][] getPlayingField()
    {
        return playingField;
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user (INCOMPLETE)
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( this.tetronimo == null )
        {
            return;
        }

        switch( key )
        {
            case 38:
                if(TetrisBoard.WIDTH - tetronimo.getXLocation()/Tetronimo.SIZE < tetronimo.getHeight()){
                    //System.out.println("condition met");
                    /*// If the tetronimo is not on the right edge of the board, it can rotate.
                    if((tetronimo.getXLocation() >= 40) & (tetronimo.getXLocation() + tetronimo.getHeight() <= Tetronimo.SIZE * TetrisBoard.WIDTH + 40)) {
                        System.out.println("not on the right edge");
                        tetronimo.rotate();
                    }
                    // If the piece is on the right side, test whether the piece can rotate without going out of bounds.
                    else{
                        if(this.tetronimo.ident.equals("Square")){
                            System.out.println("square");
                            this.tetronimo.rotate();
                        }
                        else if((this.tetronimo.ident.equals("Line")) || (this.tetronimo.ident.equals("L")) || (this.tetronimo.ident.equals("J"))){
                            if(this.tetronimo.evenRotation()){
                                System.out.println("odd");
                                this.tetronimo.rotate();
                            }
                        }
                        else{
                            if(!this.tetronimo.evenRotation()){
                                System.out.println("even");
                                this.tetronimo.rotate();
                            }
                        }
                    }*/
                    int x = this.tetronimo.getXLocation();
                    while((TetrisBoard.WIDTH - (this.tetronimo.getXLocation() - 40)/20) < this.tetronimo.getHeight()/20 & spaceOnLeft()){
                        //System.out.println("can't rotate");
                        this.tetronimo.shiftLeft();
                    }
                    if(((TetrisBoard.WIDTH - (this.tetronimo.getXLocation() - 40)/Tetronimo.SIZE) >= this.tetronimo.getHeight()/20)
                            & ((this.tetronimo.getXLocation() - 40)/20 >= 0)
                            & (this.tetronimo.getYLocation()/20 + this.tetronimo.getWidth()/20 + 1 < TetrisBoard.HEIGHT)){
                        //System.out.println("can rotate");
                        this.tetronimo.rotate();
                    }
                }

                //this.tetronimo.rotate();
                break;

            case 37:
                // test if there is a filled-in square next to the piece on the left
                // if there is space, shift the piece to the left
                if( (this.tetronimo.getXLocation() - Tetronimo.SIZE >= 40) & spaceOnLeft() )
                {
                    this.tetronimo.shiftLeft();
                }
                break;

            case 39:
                if( ((this.tetronimo.getXLocation() + this.tetronimo.getWidth()) <
                        ((TetrisBoard.WIDTH * Tetronimo.SIZE) + 40)) & spaceOnRight() )
                {
                    this.tetronimo.shiftRight();
                }
                break;
        }

    }

    /**
     * This method returns true if there is space for a piece to shift to the left.
     */
    public boolean spaceOnLeft(){
        boolean spaceOnLeft = false;
        //System.out.println("check left");
        // If the space to the left of one of the rectangles comprising the piece exists in the playing field:
        if(!((this.tetronimo.r1.getXLocation() - Tetronimo.SIZE <= 40)
                & (this.tetronimo.r2.getXLocation() - Tetronimo.SIZE <= 40)
                & (this.tetronimo.r3.getXLocation() - Tetronimo.SIZE <= 40)
                & (this.tetronimo.r4.getXLocation() - Tetronimo.SIZE <= 40))){
            //System.out.println("not out of bounds on left");
            // If the space to the left of one of the rectangles is empty:
            if((squares[(this.tetronimo.r1.getXLocation() - 40)/20][this.tetronimo.r1.getYLocation()/20] == 0)
                | (squares[(this.tetronimo.r2.getXLocation() - 60)/20][this.tetronimo.r2.getYLocation()/20] == 0)
                | (squares[(this.tetronimo.r3.getXLocation() - 60)/20][this.tetronimo.r3.getYLocation()/20] == 0)
                | (squares[(this.tetronimo.r4.getXLocation() - 60)/20][this.tetronimo.r4.getYLocation()/20] == 0)) {
                //System.out.println("left is empty");
                spaceOnLeft = true;
            }
        }
        return spaceOnLeft;
    }

    /**
     * This method returns true if there is space for a piece to shift to the right.
     */
    public boolean spaceOnRight(){
        boolean spaceOnRight = false;
        //System.out.println("check right");
        // If the space to the right of one of the rectangles comprising the piece exists in the playing field:
        if((this.tetronimo.r1.getXLocation() + Tetronimo.SIZE <= TetrisBoard.WIDTH * Tetronimo.SIZE + 40)
                & (this.tetronimo.r2.getXLocation() + Tetronimo.SIZE <= TetrisBoard.WIDTH * Tetronimo.SIZE + 40)
                & (this.tetronimo.r3.getXLocation() + Tetronimo.SIZE <= TetrisBoard.WIDTH * Tetronimo.SIZE + 40)
                & (this.tetronimo.r4.getXLocation() + Tetronimo.SIZE <= TetrisBoard.WIDTH * Tetronimo.SIZE + 40)){
            //System.out.println("not out of bounds on right");
            // If the space to the right of one of the rectangles is empty:
            //System.out.println("x = " + this.tetronimo.getXLocation() + ", y = " + this.tetronimo.getYLocation());
            if((this.tetronimo.getXLocation()-40)/20 + this.tetronimo.getWidth()/20 >= TetrisBoard.WIDTH){
                return false;
            }
            if((squares[(this.tetronimo.r1.getXLocation() - 20)/20][this.tetronimo.r1.getYLocation()/20] == 0)
                    | (squares[(this.tetronimo.r2.getXLocation() - 20)/20][this.tetronimo.r2.getYLocation()/20] == 0)
                    | (squares[(this.tetronimo.r3.getXLocation() - 20)/20][this.tetronimo.r3.getYLocation()/20] == 0)
                    | (squares[(this.tetronimo.r4.getXLocation() - 20)/20][this.tetronimo.r4.getYLocation()/20] == 0)){
                //System.out.println("right is empty");
                spaceOnRight = true;
            }
        }

        return spaceOnRight;
    }

    /**
     * This method determines whether the piece can keep falling.
     * @return True if the piece can fall for one more row.
     */
    public boolean spaceBelow(){
        boolean spaceBelow = true;

        // If the piece has not reached the bottom of the board, there is space below the piece.
        if(this.tetronimo.getYLocation() + this.tetronimo.getHeight() >= Tetronimo.SIZE * TetrisBoard.HEIGHT){
            System.out.println("bottom");
            return false;
        }
        // Check that there aren't filled squares below the squares making up the falling piece.
        if(((this.tetronimo.r1.getXLocation() - 40)/20 <= TetrisBoard.WIDTH)
                & ((this.tetronimo.r2.getXLocation() - 40)/20 <= TetrisBoard.WIDTH)
                & ((this.tetronimo.r3.getXLocation() - 40)/20 <= TetrisBoard.WIDTH)
                & ((this.tetronimo.r4.getXLocation() - 40)/20 <= TetrisBoard.WIDTH)){
            /*if((this.tetronimo.getYLocation()/20 + this.tetronimo.getHeight()/20 >= TetrisBoard.HEIGHT)){
                return false;
            }*/
            System.out.println("still in field");
            if((squares[(this.tetronimo.r1.getXLocation() - 40)/20][this.tetronimo.r1.getYLocation()/20 + 1] == 1)
                    | ((squares[(this.tetronimo.r2.getXLocation() - 40)/20][this.tetronimo.r2.getYLocation()/20 + 1] == 1))
                    | ((squares[(this.tetronimo.r3.getXLocation() - 40)/20][this.tetronimo.r3.getYLocation()/20 + 1] == 1))
                    | ((squares[(this.tetronimo.r4.getXLocation() - 40)/20][this.tetronimo.r4.getYLocation()/20 + 1] == 1))){
                System.out.println("filled below");
                return false;
            }
        }
        return spaceBelow;
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //not in use
    }

    /**
     * This animate() method used by the AnimationTimer object moves the tetronimo down the board by a row.
     */
    /*@Override
    public void animate() {
        this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
    }*/

    /*public void hide(Tetronimo piece){
        Color hidden = new Color(0f,0f,0f,0f);// the rightmost 0f means that the color is transparent
        piece.hide(hidden);
    }*/
}