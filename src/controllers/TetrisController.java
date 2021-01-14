package controllers;

import models.*;
import views.TetrisBoard;

import java.awt.*;
import java.util.Random;

/**
 * TetrisController.java:
 * Class to hold all of the game logic for tetris
 *
 * @author Professor Rossi
 * @version 1.0 July 24, 2020
 */
public class TetrisController
{
    private final TetrisBoard TETRIS_BOARD;

    public int score;

    /**
     * Constructor to take in a tetris board so the controller and the board can communciate
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController( TetrisBoard tetrisBoard )
    {
        this.TETRIS_BOARD = tetrisBoard;
        this.score = 0;
    }

    /**
     * Randomly chooses the next tetronimo and returns it (INCOMPLETE)
     *
     * @return The next tetronimo to be played
     */
    public Tetronimo getNextTetromino()
    {
        Tetronimo tetronimo;

        // Randomly select the next tetronimo:
        Random gen = new Random();
        int select = gen.nextInt(7);// returns a random integer from 0 to 6
        //select = 0;//test

        if(select == 0)
            tetronimo = new StraightLine();
        else if(select == 1)
            tetronimo = new Square();
        else if(select == 2)
            tetronimo = new T();
        else if(select == 3)
            tetronimo = new J();
        else if(select == 4)
            tetronimo = new L();
        else if(select == 5)
            tetronimo = new S();
        else
            tetronimo = new Z();
        tetronimo.setLocation( 40 + (5 * Tetronimo.SIZE), 0 );

        return tetronimo;
    }

    /**
     * Method to determine if the tetronimo has landed (COMPLETE)
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public boolean tetronimoLanded( Tetronimo tetronimo )
    {
        //int nextY = tetronimo.getYLocation() + tetronimo.getHeight() + Tetronimo.SIZE;
        //return nextY <= 480;
        return TETRIS_BOARD.spaceBelow();
    }

    /*public int rowFilled(){


        return rowToClear;
    }*/

    //public boolean fourRowsFilled(){
    //}

    /**
     * When one row is cleared, the score increases by 100 points.
     * This method determines whether a row has been filled, then clears any row that is filled.
     */
    public void clearRow(){
        boolean filled = true;
        int rowToClear = 24;
        for(int i = 0; i < TetrisBoard.HEIGHT; i++){
            filled = true;
            for(int j = 0; j < TetrisBoard.WIDTH; j++){
                if(TETRIS_BOARD.squares[j][i] == 0){
                    filled = false;
                    break;
                }
            }
            if(filled){
                rowToClear = i;
            }
        }

        // When a row is cleared, update the board's squares array by shifting each value one row down:
        if(rowToClear < TetrisBoard.HEIGHT){
            /*for(int i = rowToClear; i > 0; i--){
                for(int j = 0; j < TetrisBoard.WIDTH; j++){
                    TETRIS_BOARD.squares[j][i] = TETRIS_BOARD.squares[j][i-1];
                }
            }
            for(int i = 0; i < TetrisBoard.WIDTH; i++)
                TETRIS_BOARD.squares[i][0] = 0;*/

            // Update the playing field by shifting the colors one row down:
            //wheelsunh.users.Rectangle[][] playingField = TETRIS_BOARD.getPlayingField();// check this
            for(int i = rowToClear; i > 0; i--){
                for(int j = 0; j < TetrisBoard.WIDTH; j++){
                    TETRIS_BOARD.playingField[j][i].setFrameColor(Color.BLACK);
                    if(TETRIS_BOARD.squares[j][i-1] == 1){
                        TETRIS_BOARD.playingField[j][i].setFillColor(Color.RED);
                        TETRIS_BOARD.squares[j][i] = 1;
                    }
                    else{
                        TETRIS_BOARD.playingField[j][i].setFillColor(Color.WHITE);
                        TETRIS_BOARD.squares[j][i] = 0;
                    }
                }
            }
            for(int i = 0; i < TetrisBoard.WIDTH; i++)
                TETRIS_BOARD.playingField[i][0].setColor(Color.WHITE);

            // Increase the score by 100 points:
            score += 100;
        }
    }

    /**
     * When four rows are cleared at once (a Tetris), the score increases by 800 points.
     * This function tests whether four rows can be cleared at once.
     * If so, the four rows are cleared, and the rows above are moved down to replace them.
     * If not, the function calls clearRow() to test if a single row can be cleared instead.(INCOMPLETE)
     */
    public void clearFourRows() {
        boolean filled = true;
        //int row = 0;
        int bottomRowToClear = TetrisBoard.HEIGHT;
        for(int row = 0; row < TetrisBoard.HEIGHT - 4; row++){
            filled = true;
            for(int j = row; j <= row + 3; j++){// check the selected row and the three rows above it
                for(int k = 0; k < TetrisBoard.WIDTH; k++){
                    if(TETRIS_BOARD.squares[k][j] == 0){// if one of the rows is not completely filled
                        filled = false;
                        break;
                    }
                }
            }
            if(filled){
                bottomRowToClear = row;// This row and the three rows above it should be cleared.
            }
        }
        // If four consecutive rows are full:
        if ((bottomRowToClear > 2) & (bottomRowToClear <= 23)) {
            // Update the board's squares array by shifting each value four rows down:
            for(int i = 3; i < TetrisBoard.HEIGHT - 1; i++){
                for(int j = 0; j < TetrisBoard.WIDTH; j++){
                    TETRIS_BOARD.squares[j][i] = TETRIS_BOARD.squares[j][i+4];
                }
            }
            for(int i = 0; i < TetrisBoard.WIDTH; i++){
                for(int j = 0; j < 4; j++) {
                    TETRIS_BOARD.squares[i][j] = 0;
                }
            }

            // Update the playing field by shifting the colors four rows down:
            //wheelsunh.users.Rectangle[][] playingField = TETRIS_BOARD.getPlayingField();
            for(int i = TetrisBoard.HEIGHT - 1; i > 3; i--){
                for(int j = 0; j < TetrisBoard.WIDTH; j++){
                    if(TETRIS_BOARD.squares[j][i-4] == 1)
                        TETRIS_BOARD.playingField[j][i].setColor(Color.RED);
                    else
                        TETRIS_BOARD.playingField[j][i].setColor(Color.WHITE);
                }
            }
            for(int i = 0; i < TetrisBoard.WIDTH; i++)
                TETRIS_BOARD.playingField[i][0].setColor(Color.WHITE);

            // Increase the score by 800 points:
            score += 800;
        }
        else{
            clearRow();
        }
    }

    /**
     * This describes a game over scenario in which the pieces build up to the top of the field.
     */
    public boolean gameOver(){
        boolean reachTop = false;
        for(int i = 0; i < TetrisBoard.WIDTH; i++){
            if(TETRIS_BOARD.squares[i][0] == 1) {
                reachTop = true;
                break;
            }
        }
        return reachTop;
    }
}
