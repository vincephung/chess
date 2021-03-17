package chess;

import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;

public class Game {

    public boolean movePiece(Square[][] board, Square curSquare, Square destSquare, char promotionType) {
        boolean validMove = curSquare.piece.validMove(board, curSquare, destSquare);
        // System.out.println("Current piece is " + curSquare);
        // System.out.println("Valid move: " + validMove);
        if (!validMove) {
            return false;
        }

        int curCol = curSquare.getCol();
        int curRow = curSquare.getRow();
        int destCol = destSquare.getCol();
        int destRow = destSquare.getRow();

        // cannot move to the same spot
        if (curCol == destCol && curRow == destRow) {
            return false;
        }

        Piece curPiece = curSquare.piece;

        //move current piece to new square
        destSquare.piece = curSquare.piece;
        curSquare.piece = null;
        
        // handle promotion
        if (curPiece instanceof Pawn) {
            promotion(board, (Pawn) curPiece, destSquare, promotionType);
        }
        return true;
    }

    private void promotion(Square[][] board, Pawn curPawn, Square destSquare, char newType) {
        int destCol = destSquare.getCol();
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
