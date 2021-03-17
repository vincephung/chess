package pieces;

import chess.Square;

public class Rook extends Piece{

    public Rook(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());        
        
        //have to make some move
        if(rowDistance == 0 && colDistance == 0) return false;
        //can't land on piece of same color
        if(cur.getPiece().sameColor(dest.getPiece())) return false;
        //Rook can only move in one direction
        if((rowDistance != 0 && colDistance ==0) || (rowDistance == 0 && colDistance != 0)){
        	if(rowDistance == 0) {
        		if(cur.getCol()-dest.getCol() > 0) {
        			for(int i=cur.getCol(); i>dest.getCol(); i--) {
        				if(board[cur.getRow()][i] != null) {
        					return false;
        				}
        			}
        		}
        		else {
        			for(int i=cur.getCol(); i<dest.getCol(); i++) {
        				if(board[cur.getRow()][i] != null) {
        					return false;
        				}
        			}
        		}
        	}
        	else {
        		if(cur.getRow()-dest.getRow() > 0) {
        			for(int i=cur.getRow(); i>dest.getRow(); i--) {
        				if(board[i][cur.getCol()] != null) {
        					return false;
        				}
        			}
        		}
        		else {
        			for(int i=cur.getRow(); i<dest.getRow(); i++) {
        				if(board[i][cur.getCol()] != null) {
        					return false;
        				}
        			}
        		}
        	}
        	return true;
        }
        else return false;
        
    }

    public String toString() {
        return this.getColor() + "R";
    }
}
