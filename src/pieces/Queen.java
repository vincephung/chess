package pieces;

import chess.Square;

public class Queen extends Piece {

    public Queen(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        if ((rowDistance != 0 && colDistance != 0) && (rowDistance != colDistance)) {
            return false;
        }

        return true;
    }

    public String toString() {
        return this.getColor() + "Q";
    }

}
