package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

public class King extends Piece{

	public boolean hasMoved = false;
	
    public King(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {
    	
    	Square[][] board = boardClass.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());
        
        //check for a move of more than one spot in any direction
        if(rowDistance > 1 || colDistance > 1) return false;
        
        //otherwise if not blocked path is valid
        return !pathBlocked(board, cur, dest);
        
    }
    
    public void isCastling(Square[][] board, Square cur, Square dest) {
        
    }
    
    public String toString() {
        return this.getColor() + "K";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        //kings path only blocked if dest is of other color
    	if(dest.getPiece() != null) {
    		return cur.getPiece().sameColor(dest.getPiece());
    	}
    	else {
    		return false;
    	}
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        
        return null;
    }



}
