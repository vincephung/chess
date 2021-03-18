package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

public abstract class Piece {
    private String color;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean sameColor(Piece dest) {
    	return color.equals(dest.getColor());
    }

    public abstract boolean pathBlocked(Square[][] board, Square cur, Square dest);
    public abstract boolean validMove(Board boardClass, Square cur, Square dest);
    public abstract ArrayList<Square> getAtkPath(Square[][]board, Square cur, Square dest);
}
