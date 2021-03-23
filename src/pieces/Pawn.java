package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * Pawn is a subclass of the abstract class Piece. A pawn can only move one spot forward unless it is its first move, then it can move two spots forward. Additionally, a pawn can move forward diagonally when performing an enpassant move or a capture.
 * @author William McFarland
 * @author Vincent Phung
 *
 */
public class Pawn extends Piece {
    boolean firstMove = true;
    boolean enpassant = false;

    /**
     * initializes a Pawn Piece
     * @param color Color of the Pawn
     */
    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {
    	
    	Square[][] board = boardClass.board;
    	
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
        if(Math.abs(rowDistance) == 1 && Math.abs(colDistance) == 1) {
        	//check for enpassant
        	if(dest.getPiece() == null) {
        		if(boardClass.getEnpassant() != null && boardClass.getEnpassant().equals(dest)) {
        			//captured piece will be behind
        			Square captured = board[dest.getRow()-direction][dest.getCol()];
        			if(captured != null && captured.getPiece() != null && captured.getPiece() instanceof Pawn) {
        				captured.setPiece(null);
        				return true;
        			}
        			else {
        				return false;
        			}
        		}
        		else {
        			return false;
        		}
        	}
        	//make sure dest is of other color
        	return !dest.getPiece().sameColor(cur.getPiece());
        }
        //check for 2 space move on first turn
        else if(Math.abs(rowDistance) == 2 && colDistance == 0) {
        	//if not first turn then invalid
        	if(firstMove) {
        		if(!pathBlocked(board, cur, dest)) {
        			firstMove = false;
        			//mark enpassant as true for passed over space
        			boardClass.setEnpassant(board[dest.getRow()-direction][dest.getCol()]);
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

    /**
     * method that returns a new piece of the given type and color when a pawn is promoted
     * @param type Char representing the type of the desired piece after promotion
     * @param color Color of the niew Piece representing the promotion
     * @return Piece object representing the Pawn after promotion
     */
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

    @Override
    public String toString() {
        return this.getColor() + "p";
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        return null;
    }

}
