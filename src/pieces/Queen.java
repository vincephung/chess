package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

public class Queen extends Piece {

    public Queen(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardObject, Square cur, Square dest) {
    	
    	Square[][] board = boardObject.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        if ((rowDistance != 0 && colDistance != 0) && (rowDistance != colDistance)) {
            return false;
        }
        else {
        	//if path isn't blocked move is valid
        	return !pathBlocked(board, cur, dest);
        }

    }

    public String toString() {
        return this.getColor() + "Q";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        int diagonalPathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        
        if(Math.abs(rowDistance) == Math.abs(colDistance)) {
            //check path diagonal BETWEEN cur and dest(excluding)
            for(int i = 0; i < diagonalPathLength-1; i++) {
                //If dest square is below, decrement distance by 1 each interval
                //if dest square is above, increment distance by 1 each interval
                int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
                int colDirection = (colDistance > 0) ? 1+i : -1-i;
                
                Piece curPiece = board[row+rowDirection][col+colDirection].getPiece();
                if(curPiece != null) {
                    return true;
                }
            }
        }
        else if(rowDistance == 0) {
            int pathLength = colDistance;
            for(int i = 0; i < pathLength-1; i++) {
                Piece curPiece;
                
                int colDirection = (colDistance > 0) ? 1+i : -1-i;
                curPiece = board[row][col+colDirection].getPiece();
                
                if(curPiece != null) {
                    return true;
                }
            }
        }
        else {
            int pathLength = rowDistance;
            for(int i = 0; i < pathLength-1; i++) {
                Piece curPiece;
                
                int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
                curPiece = board[row + rowDirection][col].getPiece();
                
                
                if(curPiece != null) {
                    return true;
                }
            }
        }

    	return false;
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        int pathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        ArrayList<Square> path = new ArrayList<>();

        if(Math.abs(rowDistance) == Math.abs(colDistance)) {
            //check path diagonal BETWEEN cur and dest(excluding)
            for(int i = 0; i < pathLength-1; i++) {
                //If dest square is below, decrement distance by 1 each interval
                //if dest square is above, increment distance by 1 each interval
                int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
                int colDirection = (colDistance > 0) ? 1+i : -1-i;
                
                path.add(board[row+rowDirection][col+colDirection]);
            }
        }
        else if(rowDistance == 0) {
            for(int i = 0; i < pathLength-1; i++) {
                int colDirection = (colDistance > 0) ? 1+i : -1-i;
                path.add(board[row][col+colDirection]);
            }
        }
        else {
            for(int i = 0; i < pathLength-1; i++) {                
                int rowDirection = (rowDistance > 0) ? 1+i : -1-i;
                path.add(board[row + rowDirection][col]);
            }
        }

        return path;
    }

}
