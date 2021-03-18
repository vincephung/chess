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
        if(rowDistance > 1 || colDistance > 1) {
        	//check for possible castling
        	if(rowDistance == 0 && colDistance == 2) {
        		if(isCastling(boardClass.board, cur, dest)) {
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
    
    public boolean isCastling(Square[][] board, Square cur, Square dest) {
    	
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
