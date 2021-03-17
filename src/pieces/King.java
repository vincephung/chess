package pieces;

import chess.Square;

public class King extends Piece{

    public King(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {        
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());
        if(rowDistance > 1 || colDistance > 1) {
            return false;
        }
        //king can only move one spot.        
        return true;
    }
    
    public void cast() {
        
    }
    
    public String toString() {
        return this.getColor() + "K";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        // TODO Auto-generated method stub
        return false;
    }



}
