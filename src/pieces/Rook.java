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
        
        //Rook can only move in one direction
        if(rowDistance != 0 && colDistance !=0) {
            return false;
        }
        
        return true;
    }

    public String toString() {
        return this.getColor() + "R";
    }
}
