package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * Queen is a subclass of the abstract class Piece. A Queen can move in any direction
 * @author William McFarland
 * @author Vincent Phung
 *
 */
public class Queen extends Piece {

	/**
	 * Constructor that initializes a Queen object
	 * @param color Color of the Queen
	 */
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean validMove(Board boardObject, Square cur, Square dest) {

        Square[][] board = boardObject.board;

        int rowDistance = Math.abs(cur.getRow() - dest.getRow());
        int colDistance = Math.abs(cur.getCol() - dest.getCol());

        if ((rowDistance != 0 && colDistance != 0) && (rowDistance != colDistance)) {
            return false;
        } else {
            // if path isn't blocked move is valid
            return !pathBlocked(board, cur, dest);
        }

    }

    @Override
    public String toString() {
        return this.getColor() + "Q";
    }

    @Override
    public boolean pathBlocked(Square[][] board, Square cur, Square dest) {
        int diagonalPathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();

        if (Math.abs(rowDistance) == Math.abs(colDistance)) {
            // check path diagonal BETWEEN cur and dest(excluding)
            for (int i = 0; i < diagonalPathLength - 1; i++) {
                // If dest square is below, decrement distance by 1 each interval
                // if dest square is above, increment distance by 1 each interval
                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;

                Piece curPiece = board[row + rowDirection][col + colDirection].getPiece();
                if (curPiece != null) {
                    return true;
                }
            }
        } else if (rowDistance == 0) {
            int pathLength = Math.abs(colDistance);
            for (int i = 0; i < pathLength - 1; i++) {
                Piece curPiece;

                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;
                curPiece = board[row][col + colDirection].getPiece();

                if (curPiece != null) {
                    return true;
                }
            }
        } else {
            int pathLength = Math.abs(rowDistance);
            for (int i = 0; i < pathLength - 1; i++) {
                Piece curPiece;

                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                curPiece = board[row + rowDirection][col].getPiece();

                if (curPiece != null) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest) {
        int pathLength = Math.abs(dest.getRow() - cur.getRow());
        int rowDistance = dest.getRow() - cur.getRow();
        int colDistance = dest.getCol() - cur.getCol();
        int row = cur.getRow();
        int col = cur.getCol();
        ArrayList<Square> path = new ArrayList<>();

        if (Math.abs(rowDistance) == Math.abs(colDistance)) {
            // check path diagonal BETWEEN cur and dest(excluding)
            for (int i = 0; i < pathLength - 1; i++) {
                // If dest square is below, decrement distance by 1 each interval
                // if dest square is above, increment distance by 1 each interval
                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;

                path.add(board[row + rowDirection][col + colDirection]);
            }
        } else if (rowDistance == 0) {
            pathLength = Math.abs(colDistance);
            for (int i = 0; i < pathLength - 1; i++) {
                int colDirection = (colDistance > 0) ? 1 + i : -1 - i;
                path.add(board[row][col + colDirection]);
            }
        } else {
            pathLength = Math.abs(rowDistance);
            for (int i = 0; i < pathLength - 1; i++) {
                int rowDirection = (rowDistance > 0) ? 1 + i : -1 - i;
                path.add(board[row + rowDirection][col]);
            }
        }

        return path;
    }

}
