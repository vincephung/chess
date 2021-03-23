package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * King is a subclass of the abstract class Piece. A King can move one spot in any direction in addition to being able to perform a castling
 * @author William McFarland
 * @author Vincent Phung
 *
 */
public class King extends Piece{

	public boolean hasMoved = false;
	
	/**
	 * initializes a King piece
	 * @param color Color to give King piece
	 */
    public King(String color) {
        super(color);
    }

    @Override
    public boolean validMove(Board boardObject, Square cur, Square dest) {
    	
    	Square[][] board = boardObject.board;
    	
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());
        
        String enemyColor = (cur.getPiece().getColor().equals("w")) ? "b" : "w";
        
        //check for a move of more than one spot in any direction
        if(rowDistance > 1 || colDistance > 1) {
        	//check for possible castling
        	if(rowDistance == 0 && colDistance == 2) {
        		if(!boardObject.isUnderAttack(cur, enemyColor) && isCastling(boardObject, cur, dest)) {
        			hasMoved = true;
        			return true;
        		}
        		else return false;
        	}
        	else {
        		return false;
        	}
        }
        
        //otherwise if not blocked path is valid
        if(!pathBlocked(board, cur, dest)) {
        	hasMoved = true;
        	return true;
        }
        else {
        	return false;
        }
        
    }
    
    /**
     * method to check if a particular king move is a castling
     * @param boardObject Board being used
     * @param cur Square that the piece is currently on
     * @param dest Square that the piece is trying to move to
     * @return True if the move is a valid castling, false if otherwise
     */
    public boolean isCastling(Board boardObject, Square cur, Square dest) {
    	
    	Square[][] board = boardObject.board;
    	
    	int direction = cur.getCol() - dest.getCol();
    	//check to make sure king hasn't been moved
    	if(!hasMoved) {
    		//check for rook in direction that also hasn't been moved
    		if(getColor().equals("w")) {
    			if(direction > 0) {
    				//move to left
    				if(board[0][0].getPiece() != null && board[0][0].getPiece() instanceof Rook) {
    					//make sure rook hasn't been moved
    					if(((Rook)board[0][0].getPiece()).hasMoved){
    						return false;
    					}
    					else {
    						//otherwise check for anything inbetween
    						for(int i = 3; i > 0; i--) {
    							if(board[0][i].getPiece() != null) return false;
    						}
    						
    						if(boardObject.isUnderAttack(new Square(0, 3, null), "b") || boardObject.isUnderAttack(new Square(0, 2, null), "b")) {
    							return false;
    						}

    						//if there's nothing in between perform the castling
    						Rook temp = (Rook)board[0][0].getPiece();
    						temp.hasMoved = true;
    						board[0][3].setPiece(temp);
    						board[0][0].setPiece(null);
    						
    						return true;
    						
    					}
    				}
    				else {
    					return false;
    				}
    				
    			}
    			else {
    				//move to right
    				if(board[0][7].getPiece() != null && board[0][7].getPiece() instanceof Rook) {
    					//make sure rook hasn't been moved
    					if(((Rook)board[0][7].getPiece()).hasMoved){
    						return false;
    					}
    					else {
    						//otherwise check for anything inbetween

    						for(int i = 5; i < 7; i++) {
    							if(board[0][i].getPiece() != null) return false;
    						}
    						
    						if(boardObject.isUnderAttack(new Square(0, 5, null), "b") || boardObject.isUnderAttack(new Square(0, 6, null), "b")) {
    							return false;
    						}

    						//if there's nothing in between perform the castling
    						Rook temp = (Rook)board[0][7].getPiece();
    						temp.hasMoved = true;
    						board[0][5].setPiece(temp);
    						board[0][7].setPiece(null);
    						
    						return true;
    						
    					}
    				}
    				else {
    					return false;
    				}
    			}
    		}
    		else {
    			if(direction > 0) {
    				//move to left
    				if(board[7][0].getPiece() != null && board[7][0].getPiece() instanceof Rook) {
    					//make sure rook hasn't been moved
    					if(((Rook)board[7][0].getPiece()).hasMoved){
    						return false;
    					}
    					else {
    						//otherwise check for anything inbetween
    						for(int i = 3; i > 0; i--) {
    							if(board[7][i].getPiece() != null) return false;
    						}
    						
    						if(boardObject.isUnderAttack(new Square(7, 3, null), "w") || boardObject.isUnderAttack(new Square(7, 2, null), "w")) {
    							return false;
    						}
    						
    						//if there's nothing in between perform the castling
    						Rook temp = (Rook)board[7][0].getPiece();
    						temp.hasMoved = true;
    						board[7][3].setPiece(temp);
    						board[7][0].setPiece(null);
    						
    						return true;
    						
    					}
    				}
    				else {
    					return false;
    				}
    				
    			}
    			else {
    				//move to right
    				if(board[7][7].getPiece() != null && board[7][7].getPiece() instanceof Rook) {
    					//make sure rook hasn't been moved
    					if(((Rook)board[7][7].getPiece()).hasMoved){
    						return false;
    					}
    					else {
    						//otherwise check for anything inbetween
    						for(int i = 5; i < 7; i++) {
    							if(board[7][i].getPiece() != null) return false;
    						}
    						
    						if(boardObject.isUnderAttack(new Square(7, 5, null), "w") || boardObject.isUnderAttack(new Square(7, 6, null), "w")) {
    							return false;
    						}

    						//if there's nothing in between perform the castling
    						Rook temp = (Rook)board[7][7].getPiece();
    						temp.hasMoved = true;
    						board[7][5].setPiece(temp);
    						board[7][7].setPiece(null);
    						
    						return true;
    						
    					}
    				}
    				else {
    					return false;
    				}
    			}
    			
    		}
    		
    	}

    	return false;
        
    }
    
    @Override
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
