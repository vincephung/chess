package chess;

import java.util.ArrayList;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Board is a representation of the current state of the game board. 8x8 grid of
 * Squares that contain chess pieces.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */

public class Board {

    public Square[][] board = new Square[8][8];
    private Square enpassant;

    /**
     * Class constructor to create a new board instance. Initializes board with 8x8
     * squares and puts pieces on the board.
     */
    public Board() {
        // initializes pawns
        for (int col = 0; col < board[0].length; col++) {
            board[1][col] = new Square(1, col, new Pawn("w"));
            board[6][col] = new Square(6, col, new Pawn("b"));
        }

        board[0][0] = new Square(0, 0, new Rook("w"));
        board[0][1] = new Square(0, 1, new Knight("w"));
        board[0][2] = new Square(0, 2, new Bishop("w"));
        board[0][3] = new Square(0, 3, new Queen("w"));
        board[0][4] = new Square(0, 4, new King("w"));
        board[0][5] = new Square(0, 5, new Bishop("w"));
        board[0][6] = new Square(0, 6, new Knight("w"));
        board[0][7] = new Square(0, 7, new Rook("w"));

        board[7][0] = new Square(7, 0, new Rook("b"));
        board[7][1] = new Square(7, 1, new Knight("b"));
        board[7][2] = new Square(7, 2, new Bishop("b"));
        board[7][3] = new Square(7, 3, new Queen("b"));
        board[7][4] = new Square(7, 4, new King("b"));
        board[7][5] = new Square(7, 5, new Bishop("b"));
        board[7][6] = new Square(7, 6, new Knight("b"));
        board[7][7] = new Square(7, 7, new Rook("b"));

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Square(row, col, null);
            }
        }
        enpassant = null;
    }

    /**
     * Prints out all of the squares on the board.
     */
    public void printBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].piece != null) {
                    System.out.print(board[i][j] + " ");
                } else {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            System.out.print("   ");
                        } else {
                            System.out.print("## ");
                        }
                    } else {
                        if (j % 2 == 0) {
                            System.out.print("## ");
                        } else {
                            System.out.print(("   "));
                        }
                    }
                }
            }
            System.out.println(i + 1);
        }
        // print letters in last line
        System.out.println(" a  b  c  d  e  f  g  h");
        System.out.println();
    }

    /**
     * Gets the squares of a selected color.
     * 
     * @param color Color of the squares to search for.
     * @return List of squares of a specific color.
     */
    public ArrayList<Square> getSquares(String color) {
        ArrayList<Square> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square curSquare = board[i][j];
                if (curSquare.piece != null && curSquare.piece.getColor().equals(color)) {
                    list.add(curSquare);
                }
            }
        }
        return list;
    }

    public Square getEnpassant() {
        return enpassant;
    }

    public void setEnpassant(Square piece) {
        enpassant = piece;

    }
}
