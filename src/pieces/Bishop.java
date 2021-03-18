package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * Bishop is a subclass of the abstract class Piece. A bishop can only move in a
 * diagonal direction.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Bishop extends Piece {

    /**
     * Initializes a bishop piece.
     * 
     * @param color Color to give the bishop piece.
     */
    public Bishop(String color) {
        super(color);
    }

    /**
     * Returns true if the piece on current square moves in a diagonal direction.
     */
    @Override
    public boolean validMove(Board boardClass, Square cur, Square dest) {

        Square[][] board = boardClass.board;

        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        if (rowDistance == 0 || colDistance == 0) {
            return false;
        }

        // if not moving in diagonal can't be valid
        if (rowDistance != colDistance) {
            return false;
        }

        // otherwise valid as long as no blocked path
        return !pathBlocked(board, cur, dest);
    }


    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {

        int pathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();

        // check path diagonal BETWEEN cur and dest(excluding)
        for (int i = 0; i < pathLength - 1; i++) {
            // If dest square is below, decrement distance by 1 each interval
            // if dest square is above, increment distance by 1 each interval
            int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
            int colDirection = (colDistance > 0) ? 1 + i : -1 - i;

            Piece curPiece = board[row + rowDirection][col + colDirection].getPiece();
            if (curPiece != null) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return this.getColor() + "B";
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        int pathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        ArrayList<Square> path = new ArrayList<>();

        // check path diagonal BETWEEN cur and dest(excluding)
        for (int i = 0; i < pathLength - 1; i++) {
            // If dest square is below, decrement distance by 1 each interval
            // if dest square is above, increment distance by 1 each interval
            int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
            int colDirection = (colDistance > 0) ? 1 + i : -1 - i;

            path.add(board[row + rowDirection][col + colDirection]);
        }
        return path;
    }

}
