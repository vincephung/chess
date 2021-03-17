package chess;

import pieces.Piece;

public class Square {
    Piece piece;
    int row;
    int col;
    
    public Square(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getCol() {
        return this.col;
    }
    
    public Piece getPiece() {
    	return piece;
    }
    
    public String toString() {
        return piece.toString();
    }
}
