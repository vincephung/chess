package chess;

import pieces.Piece;

/**
 * Square represents an individual block inside of board.
 * Contains a row,col and piece. 
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Square {
    Piece piece;
    int row;
    int col;
    
    /**
     * Constructor to initialize the current square.
     * 
     * @param row Current row of the square.
     * @param col Current column of the square.
     * @param piece Current piece that is on the square.
     */
    public Square(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }
    
    /**
     * Getter method to get the row of the current square.
     * 
     * @return Row number of this square.
     */
    public int getRow() {
        return this.row;
    }
    
    /**
     * Getter method to get the column of the current square.
     * 
     * @return Column number of this square.
     */
    public int getCol() {
        return this.col;
    }
    
    /**
     * Getter method to get the piece on the current square.
     * 
     * @return Piece on this square.
     */
    public Piece getPiece() {
    	return piece;
    }
    
    /**
     * Setter method to change the current piece on the square.
     * 
     * @param piece New Piece on the square
     */
    public void setPiece(Piece piece) {
    	this.piece = piece;
    }
    
    /**
     *Checks if two squares are equal.
     */
    @Override
    public boolean equals(Object o) {
    	if(o instanceof Square){
    		Square temp = (Square)o;
    		return temp.getRow() == row && temp.getCol() == col;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * Prints out the piece on the square.
     */ 
    @Override
    public String toString() {
        return piece.toString();
    }
}
