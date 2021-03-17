package pieces;

import chess.Square;

public class King extends Piece{

    public King(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {        
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        if(rowDistance == 0 && colDistance == 0) return false;
        
        //king can only move a single spot in any direction
        if((rowDistance == 1 || rowDistance == 0) && (colDistance == 1 || colDistance == 0)) {
        	//check to make sure there isn't a piece of same color at dest
        	if(dest.getPiece() != null) {
        		return dest.getPiece().sameColor(cur.getPiece());
        	}
        	else {
        		return true;
        	}
        }
        else {
        	return false;
        }
    }
    
    public void cast() {
        
    }
    
    public String toString() {
        return this.getColor() + "K";
    }



}
