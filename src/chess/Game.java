package chess;

import pieces.Pawn;
import pieces.Piece;

public class Game {

    public boolean movePiece(Square[][] board, Square curSquare, Square destSquare, char promotionType,
            boolean whiteTurn) {

        int curCol = curSquare.getCol();
        int curRow = curSquare.getRow();
        int destCol = destSquare.getCol();
        int destRow = destSquare.getRow();
        Piece curPiece = curSquare.getPiece();
        Piece destPiece = destSquare.getPiece();

        // cannot move piece to the same spot
        if (curCol == destCol && curRow == destRow) {
            return false;
        }

        // check boundaries
        if (curCol < 0 || curRow < 0 || curCol > 7 || curRow > 7 || destCol < 0 || destRow < 0 || destCol > 7
                || destRow > 7) {
            return false;
        }

        // check if this piece can make this type of move.
        boolean validMove = curPiece.validMove(board, curSquare, destSquare);
        boolean pathBlocked = curPiece.pathBlocked(board, curSquare, destSquare);
        
        if (!validMove || pathBlocked) {
            return false;
        }

        // player can only move their own colored pieces, ex: white moves white
        if (whiteTurn && curPiece.getColor().equals("b")) {
            return false;
        } else if (!whiteTurn && curPiece.getColor().equals("w")) {
            return false;
        }

        // Pieces cannot attack the same colored piece
        if (destPiece != null && curPiece.getColor().equals(destPiece.getColor())) {
            return false;
        }

        // move current piece to new square
        destSquare.piece = curSquare.piece;
        curSquare.piece = null;

        // handle promotion
        if (curPiece instanceof Pawn) {
            promotion((Pawn) curPiece, destSquare, promotionType);
        }
        return true;
    }

    private void promotion(Pawn curPawn, Square destSquare, char newType) {
        int destRow = destSquare.getRow();

        if (curPawn.getColor().equals("w") && destRow != 7) {
            return;
        }

        if (curPawn.getColor().equals("b") && destRow != 0) {
            return;
        }

        destSquare.piece = curPawn.promotion(newType, curPawn.getColor());
    }
}
