package pieces;

import chess.Square;

public class Pawn extends Piece {
    boolean firstMove = true;

    public Pawn(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Square[][] board, Square cur, Square dest) {
        boolean isWhite = this.getColor().equals("w");
        // white pawns can only move up, black pawns can only move down
        int direction = (isWhite) ? 1 : -1;
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();

        /*
         * first move, a pawn can move one spot or TWO spots afterwards it can only move
         * one spot.
         */
        if (firstMove) {
            int twoSteps = (isWhite) ? 2 : -2;
            if (rowDistance != direction && rowDistance != twoSteps) {
                return false;
            }
            // pawns can only go diagonally in one step
            if (rowDistance == twoSteps && colDistance != 0) {
                return false;
            }
            firstMove = false;
        } else {
            // checks if pawn only moved one spot and in correct direction
            if (rowDistance != direction) {
                return false;
            }
        }

        // checks for diagonal move
        if (colDistance != direction && colDistance != 0) {
            return false;
        }

        return true;
    }

    public boolean enpassant() {
        return true;
    }

    public Piece promotion(char type,String color) {
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

    public String toString() {
        return this.getColor() + "p";
    }

}
