package pieces;

import chess.Board;
import chess.Square;

public class Rook extends Piece{

    public Rook(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {
    	
    	Square[][] board = boardClass.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());        
        
        //can't land on piece of same color
       // if(cur.getPiece().sameColor(dest.getPiece())) return false;
        
        //Rook can only move in one direction
        if((rowDistance != 0 && colDistance ==0) || (rowDistance == 0 && colDistance != 0)){
        	//only valid if path unblocked
        	return !pathBlocked(board, cur, dest);
        }
        else return false;
        
    }

    public String toString() {
        return this.getColor() + "R";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        
        //distance traveled changes depending on the move's direction
        int distance = (rowDistance != 0) ? rowDistance : colDistance;
        boolean horizontal; //direction of path
        
        //finds what direction the piece takes (horizontal/vert)
        if(colDistance != 0) {
            distance = colDistance;
            horizontal = true;
        }else {
            distance = rowDistance;
            horizontal = false; 
        }
        Math.abs(distance);
        
        //check horizontal/vertical path BETWEEN cur and dest(excluding)
        for(int i = 0; i < distance-1; i++) {
            Piece curPiece;
            if(horizontal) {
                int colDirection = (colDistance > 0) ? 1+i : -1-i;
                curPiece = board[row][col+colDirection].getPiece();
            }else {
                int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
                curPiece = board[row + rowDirection][col].getPiece();
            }
            
            if(curPiece != null) {
                return true;
            }
        }
        
        return false;
    }
}
