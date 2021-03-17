package pieces;

import chess.Square;

public class Pawn extends Piece {
    boolean firstMove = true;

    public Pawn(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        boolean isWhite = this.getColor().equals("w");
        // white pawns can only move up, black pawns can only move down
        int direction = (isWhite) ? 1 : -1;
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();

        /*
         * first move, a pawn can move one spot or TWO spots afterwards it can only move
         * one spot.
         */
        //pawn moving in wrong direction
        if(rowDistance / direction < 0) return false;
        
        //check for diagonal first
        if(Math.abs(rowDistance) == 1 && Math.abs(colDistance) == 1 && dest.getPiece() != null) {
        	//make sure dest is of other color
        	return !dest.getPiece().sameColor(cur.getPiece());
        }
        //check for 2 space move on first turn
        else if(Math.abs(rowDistance) == 2 && colDistance == 0) {
        	//if not first turn then invalid
        	if(firstMove) {
        		if(!pathBlocked(board, cur, dest)) {
        			firstMove = false;
        			return true;
        		}
        		else return false;
        	}
        	else {
        		return false;
        	}
        }
        //check for 1 space move
        else if(Math.abs(rowDistance) == 1 && colDistance == 0) {
        	if(!pathBlocked(board, cur, dest)) {
        		firstMove = false;
        		return true;
        	}
        	else {
        		return false;
        	}
        }
        //otherwise invalid
        else {
        	return false;
        }
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {     
        //assume that validMove was checked already
        //this ONLY strictly checks if the path is blocked
        int rowDistance = dest.getRow() - cur.getRow();
        int direction = rowDistance / Math.abs(rowDistance);
        
        if(Math.abs(rowDistance) == 2) {
        	//need to check space before dest and dest
        	if(board[cur.getRow() + direction][cur.getCol()].getPiece() != null) return true;
        }
        
        //check dest
        if(dest.getPiece() != null) return true;
        
        return false;
    }

    public boolean enpassant() {
        return true;
    }

    public Piece promotion(char type, String color) {
        Piece newPiece = null;
        switch (Character.toUpperCase(type)) {
        case 'B':
            newPiece = new Bishop(color);
            break;
        case 'N':
            newPiece = new Knight(color);
            break;
        case 'Q':
            newPiece = new Queen(color);
            break;
        case 'R':
            newPiece = new Rook(color);
            break;
        default:
            newPiece = new Queen(color);
            break;
        }
        return newPiece;
    }

    public String toString() {
        return this.getColor() + "p";
    }

}
