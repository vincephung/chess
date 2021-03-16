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
        
        if(rowDistance == 0 || colDistance == 0) {
            return false;
        }
        //bishops can only go diagonally
        //so the row and col distances must be the same
        return rowDistance == colDistance;
    }
    
    public String toString() {
        return this.getColor() + "B";
    }

}
