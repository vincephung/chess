package pieces;

import chess.Square;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());
        
        //if piece at dest has same color then move is illegal
        if(cur.getPiece().sameColor(dest.getPiece())) return false;
        
        if(rowDistance == 0 || colDistance == 0) {
            return false;
        }
        if(rowDistance == colDistance) {
        	//check for any pieces in path
        	if((cur.getRow() - dest.getRow() < 0) && (cur.getCol() - dest.getCol()<0)) {
        		int row = cur.getRow() + 1;
        		int col = cur.getCol() + 1;
        		while(row != dest.getRow() && col != dest.getCol()) {
        			if(board[row][col] != null) {
        				return false;
        			}
        			row++;
        			col++;
        		}
        	}
        	else if((cur.getRow() - dest.getRow() < 0) && (cur.getCol() - dest.getCol()>0)) {
        		int row = cur.getRow() + 1;
        		int col = cur.getCol() - 1;
        		while(row != dest.getRow() && col != dest.getCol()) {
        			if(board[row][col] != null) {
        				return false;
        			}
        			row++;
        			col--;
        		}
        	}
        	else if((cur.getRow() - dest.getRow() > 0) && (cur.getCol() - dest.getCol()<0)) {
        		int row = cur.getRow() - 1;
        		int col = cur.getCol() + 1;
        		while(row != dest.getRow() && col != dest.getCol()) {
        			if(board[row][col] != null) {
        				return false;
        			}
        			row--;
        			col++;
        		}
        	}
        	else{
        		int row = cur.getRow() - 1;
        		int col = cur.getCol() - 1;
        		while(row != dest.getRow() && col != dest.getCol()) {
        			if(board[row][col] != null) {
        				return false;
        			}
        			row--;
        			col--;
        		}
        	}
        	return true;
        }
        else {
        	return false;
        }
    }
    
    public String toString() {
        return this.getColor() + "B";
    }

}
