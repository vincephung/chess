package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

public class Rook extends Piece {

    public boolean hasMoved = false;

    public Rook(String color) {
        super(color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validMove(Board boardObject, Square cur, Square dest) {

        Square[][] board = boardObject.board;

        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        // Rook can only move in one direction
        if ((rowDistance != 0 && colDistance == 0) || (rowDistance == 0 && colDistance != 0)) {
            // only valid if path unblocked
            if (!pathBlocked(board, cur, dest)) {
                hasMoved = true;
                return true;
            } else {
                return false;
            }
        } else
            return false;

    }

    public String toString() {
        return this.getColor() + "R";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();

        int pathLength;
        boolean horizontal; // direction of path

        // distance traveled changes depending on the move's direction
        // finds what direction the piece takes (horizontal/vert)
        if (rowDistance == 0) {
            pathLength = Math.abs(colDistance);
            horizontal = true;
        } else {
            pathLength = Math.abs(rowDistance);
            horizontal = false;
        }

        // check horizontal/vertical path BETWEEN cur and dest(excluding)
        for (int i = 0; i < pathLength - 1; i++) {
            Piece curPiece;
            if (horizontal) {
                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;
                curPiece = board[row][col + colDirection].getPiece();
            } else {
                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                curPiece = board[row + rowDirection][col].getPiece();
            }

            if (curPiece != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        ArrayList<Square> path = new ArrayList<>();

        // distance traveled changes depending on the move's direction
        int pathLength;
        boolean horizontal; // direction of path

        // finds what direction the piece takes (horizontal/vert)
        if (rowDistance == 0) {
            pathLength = Math.abs(colDistance);
            horizontal = true;
        } else {
            pathLength = Math.abs(rowDistance);
            horizontal = false;
        }

        // check horizontal/vertical path BETWEEN cur and dest(excluding)
        for (int i = 0; i < pathLength - 1; i++) {
            if (horizontal) {
                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;
                path.add(board[row][col + colDirection]);
            } else {
                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                path.add(board[row + rowDirection][col]);
            }
        }
        return path;
    }
}
