package pieces;

import chess.Square;

public class Knight extends Piece{

    public Knight(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol()); 
        
        
        return ((rowDistance == 1 && colDistance == 2) || (rowDistance ==2 && colDistance ==1));
    }
    
    public String toString() {
        return this.getColor() + "N";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        // TODO Auto-generated method stub
        return false;
    }

}
