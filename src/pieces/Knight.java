package pieces;

import chess.Board;
import chess.Square;

public class Knight extends Piece{

    public Knight(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {
    	Square[][] board = boardClass.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol()); 
        
        
        if((rowDistance == 1 && colDistance == 2) || (rowDistance ==2 && colDistance ==1)) {
        	//can jump over pieces in path so only worried about dest having same color
        	return !pathBlocked(board, cur, dest);
        }
        else {
        	return false;
        }
    }
    
    public String toString() {
        return this.getColor() + "N";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        //knight only blocked if dest piece is of same color
    	if(dest.getPiece() != null) {
    		return cur.getPiece().sameColor(dest.getPiece());
    	}
    	else {
    		return false;
    	}
        
    }

}
