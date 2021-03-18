package pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Square;

/**
 * Piece is an abstract class to represent a chess piece object. Contains
 * functions that every piece contains.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public abstract class Piece {
    private String color;

    /**
     * Constructor that initializes a piece object.
     * 
     * @param color Color of the piece.
     */
    public Piece(String color) {
        this.color = color;
    }

    /**
     * Gets the color of the current piece.
     * 
     * @return Color of the current piece.
     */
    public String getColor() {
        return color;
    }

    /**
     * Checks if another piece is the same color as the current piece.
     * 
     * @param dest Other piece object to be compared to.
     * @return True if both pieces are the same color.
     */
    public boolean sameColor(Piece dest) {
        return color.equals(dest.getColor());
    }

    /**
     * Determines whether the piece on the current square can reach the destination
     * square without any pieces in between its path.
     * 
     * @param board Contains the state of all of the squares/pieces on the board.
     * @param cur   The currently selected square.
     * @param dest  The destination square that the user wants to move to.
     * @return True if there are no pieces in between the path of cur and dest.
     */
    public abstract boolean pathBlocked(Square[][] board, Square cur, Square dest);

    /**
     * Determines whether the current type of piece can move from cur to dest based
     * on the chess rules for each piece.
     * 
     * @param boardClass An instance of the board class.
     * @param cur        The currently selected square.
     * @param dest       The destination square that the user wants to move to.
     * @return True if the piece on the current square can successfully move to the
     *         dest square.
     */
    public abstract boolean validMove(Board boardClass, Square cur, Square dest);

    /**
     * Creates and returns a list that contains the squares that form a path from
     * the current square to the destination square based on the type of piece on
     * the current square.
     * 
     * @param board Contains the state of all of the squares/pieces on the board.
     * @param cur   The currently selected square.
     * @param dest  The destination square that the user wants to move to.
     * @return A list of squares that form a path from cur to dest.
     */
    public abstract ArrayList<Square> getAtkPath(Square[][] board, Square cur, Square dest);
}
