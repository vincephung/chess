package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * Knight is a subclass of the abstract class Piece. A knight can move three spots
 * where two of the spots are horizontal in the same direction and the other spot is vertical
`* in any direction and vice versa.
 *
 * @author William McFarland
 * @author Vincent Phung
 *
 */

public class Knight extends Piece{

	/**
	 * initializes a Knight piece
	 * 
	 * @param color Color of the Knight
	 */
    public Knight(String color) {
        super(color);
    }

    @Override
    public boolean validMove(Board boardObject, Square cur, Square dest) {
    	Square[][] board = boardObject.board;
    	
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
    
    @Override
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

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        
        return null;
    }

}
