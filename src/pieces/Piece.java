package pieces;

import chess.Square;

public abstract class Piece {
    private String color;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    /*
     * public String toString() { return color; }
     */
    public abstract boolean pathBlocked(Square[][] board, Square cur, Square dest);
    public abstract boolean validMove(Square[][] board, Square cur, Square dest);
}
