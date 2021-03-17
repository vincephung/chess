package pieces;

import chess.Board;
import chess.Square;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {
    	
    	Square[][] board = boardClass.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());
        
        //if piece at dest has same color then move is illegal
        //if(cur.getPiece().sameColor(dest.getPiece())) return false;
        
        if(rowDistance == 0 || colDistance == 0) {
            return false;
        }
        
        //if not moving in diagonal can't be valid
        if(rowDistance != colDistance) {
        	return false;
        }
        
        //otherwise valid as long as no blocked path
        return !pathBlocked(board, cur, dest);
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
     
        int distance = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        
        //check path diagonal BETWEEN cur and dest(excluding)
        for(int i = 0; i < distance-1; i++) {
            //If dest square is below, decrement distance by 1 each interval
            //if dest square is above, increment distance by 1 each interval
            int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
            int colDirection = (colDistance > 0) ? 1+i : -1-i;
            
            Piece curPiece = board[row+rowDirection][col+colDirection].getPiece();
            if(curPiece != null) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        return this.getColor() + "B";
    }

}
