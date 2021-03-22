package chess;

import java.util.ArrayList;
import java.util.Scanner;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;

/**
 * Game represents an instance of a chess game Contains method that controls the
 * game, keeps track of turns, valid moves, checks etc.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Game {
    Board boardObject;
    Square[][] board;
    boolean whiteTurn;
    boolean drawOffer;
    boolean gameOver;
    Square prevEnpassant;

    /**
     * Class constructor to create a new game instance. Initializes board and the
     * game settings such as turn.
     */
    public Game() {
        boardObject = new Board();
        board = boardObject.board;
        whiteTurn = true;
        drawOffer = false;
        gameOver = false;
        prevEnpassant = null;
    }

    /**
     * Method to start a game. Takes in user input until the game is over and
     * displays the chess board.
     */
    public void startGame() {
        boardObject.printBoard();

        // take in user input
        Scanner sc = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            if (whiteTurn) {
                System.out.print("White's move: ");
            } else {
                System.out.print("Black's move: ");
            }

            // handle user input
            String str = sc.nextLine();
            String[] input = str.split(" ");
            char promotionType = ' ';

            // handle resign
            if (input.length == 1 && input[0].equals("resign")) {
                if (whiteTurn) {
                    System.out.println("Black wins");
                } else {
                    System.out.println("White wins");
                }
                break;
            }

            // other user accepts draw and ends game
            if (drawOffer && input[0].equals("draw")) {
                // System.out.println("draw");
                break;
            }
            // Third input can either be draw or promotion type
            if (input.length == 3) {
                // handle draw
                if (input[2].equals("draw?")) {
                    drawOffer = true;
                } else {
                    promotionType = input[2].charAt(0);
                }
            }

            // convert user input into integers
            int curCol = input[0].charAt(0) - 'a';
            int curRow = input[0].charAt(1) - '0';
            int destCol = input[1].charAt(0) - 'a';
            int destRow = input[1].charAt(1) - '0';

            Square curSquare = board[curRow - 1][curCol];
            Square destSquare = board[destRow - 1][destCol];
            Piece curPiece = curSquare.piece;

            if(curPiece == null) {
            	System.out.println("Illegal move, try again");
                continue;
            }
            // player can only move their own colored pieces, ex: white moves white
            String enemyColor = (curSquare.piece.getColor().equals("w")) ? "b" : "w";
            if (whiteTurn && curPiece.getColor().equals("b")) {
            	System.out.println("Illegal move, try again");
                continue;
            } else if (!whiteTurn && curPiece.getColor().equals("w")) {
            	System.out.println("Illegal move, try again");
                continue;
            }

            // checks if the move is valid / does not put king in check
            if (!simulateMove(board, curSquare, destSquare)) {
                System.out.println("Illegal move, try again");
                continue;
            }

            // If move was valid, move the piece
            movePiece(board, curSquare, destSquare);

            // handle possible enpassant
            if (boardObject.getEnpassant() != null) {
                if (prevEnpassant != null && prevEnpassant.equals(boardObject.getEnpassant())) {
                    boardObject.setEnpassant(null);
                    prevEnpassant = null;
                } else {
                    prevEnpassant = boardObject.getEnpassant();
                }
            }

            // handle promotion
            if (curPiece instanceof Pawn) {
                promotion((Pawn) curPiece, destSquare, promotionType);
            }

            System.out.println();
            boardObject.printBoard();

            // check and checkmate calls
            Square enemyKing = kingLocation(board, enemyColor);
            if (isCheck(board, enemyKing)) {
                if (isCheckmate(board, enemyKing)) {
                    gameOver = true;
                    System.out.println("Checkmate");
                    if (whiteTurn) {
                        System.out.println("White wins");
                    } else {
                        System.out.println("Black wins");
                    }
                    break;
                }
                System.out.println("Check");
            }

            whiteTurn = !whiteTurn;

        }
        sc.close();
    }

    /**
     * Determines whether the piece on the currently selected square can move to the
     * destination square successfully. Does not check if the move puts their King
     * in check.
     * 
     * @param board      Contains the state of all of the squares/pieces on the
     *                   board.
     * @param curSquare  The currently selected square.
     * @param destSquare The destination square that the user wants to move to.
     * @return true if the piece on curSquare can successfully move to destSquare.
     */
    public boolean canMove(Square[][] board, Square curSquare, Square destSquare) {
        int curCol = curSquare.getCol();
        int curRow = curSquare.getRow();
        int destCol = destSquare.getCol();
        int destRow = destSquare.getRow();
        Piece curPiece = curSquare.getPiece();
        Piece destPiece = destSquare.getPiece();

        // current square needs contain a piece.
        if (curPiece == null) {
            return false;
        }

        // cannot move piece to the same spot
        if (curCol == destCol && curRow == destRow) {
            return false;
        }

        // check boundaries
        if (curCol < 0 || curRow < 0 || curCol > 7 || curRow > 7 || destCol < 0 || destRow < 0 || destCol > 7
                || destRow > 7) {
            return false;
        }

        // check to make sure dest isn't of same color
        if (destPiece != null && curPiece.sameColor(destPiece)) {
            return false;
        }

        // check if this piece can make this type of move.
        if (!curPiece.validMove(boardObject, curSquare, destSquare)) {
            return false;
        }

        return true;
    }

    /**
     * Moves the piece on the currently selected square to the destination square
     * 
     * @param board      Contains the state of all of the squares/pieces on the
     *                   board.
     * @param curSquare  The currently selected square.
     * @param destSquare The destination square that the user wants to move to.
     */
    public void movePiece(Square[][] board, Square curSquare, Square destSquare) {
        destSquare.piece = curSquare.piece;
        curSquare.piece = null;
    }

    /**
     * Searches for a certain colored King's position on the board
     * 
     * @param board Contains the state of all of the squares/pieces on the board.
     * @param color The color of the King to search for.
     * @return The square that the king is located at.
     */
    private Square kingLocation(Square[][] board, String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square curSquare = board[i][j];
                Piece curPiece = curSquare.piece;
                if ((curPiece != null) && (curPiece instanceof King) && curPiece.getColor().equals(color)) {
                    return curSquare;
                }
            }
        }
        return null;
    }

    /**
     * When a pawn reaches the last row of the opposite board it is exchanged for a
     * different piece of the same color.
     * 
     * @param curPawn    The currently selected pawn to promote.
     * @param destSquare The square that the pawn is moving to.
     * @param newType    The type of piece that the pawn is being promoted to.
     */
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

    /**
     * Determines if the selected square can be attacked by an enemy piece
     * 
     * @param board     Contains the state of all of the squares/pieces on the
     *                  board.
     * @param curSquare The currently selected square to check.
     * @return True if the currently selected square can be attacked by an enemy.
     */
    public boolean isCheck(Square[][] board, Square curSquare) {
        String enemyColor = (curSquare.piece.getColor().equals("w")) ? "b" : "w";
        ArrayList<Square> enemySquares = boardObject.getSquares(enemyColor);
        for (Square attacker : enemySquares) {
            if (canMove(board, attacker, curSquare)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if there are no possible moves that can get a king out of check.
     * There are possibilities to get out of check: 1.King moves out of check , 2.
     * Another piece blocks the attacker, 3. The attacking piece is captured.
     * 
     * @param board      Contains the state of all of the squares/pieces on the
     *                   board.
     * @param kingSquare The square that the king is located on.
     * @return True if all checks fail then there are no possible moves that can get
     *         a king out of check.
     */
    public boolean isCheckmate(Square[][] board, Square kingSquare) {
        int curRow = kingSquare.getRow();
        int curCol = kingSquare.getCol();
        int top = curRow + 1;
        int bottom = curRow - 1;
        int right = curCol + 1;
        int left = curCol - 1;

        // boundary checks for each direction
        boolean validTop = (top <= 7) ? true : false;
        boolean validBot = (bottom >= 0) ? true : false;
        boolean validRight = (right <= 7) ? true : false;
        boolean validLeft = (left >= 0) ? true : false;
        boolean validRow = (curRow <= 7 && curRow >= 0) ? true : false;
        boolean validCol = (curCol <= 7 && curCol >= 0) ? true : false;

        // attempts to move king in every direction to get out of check
        if (((validTop && validLeft) && simulateMove(board, kingSquare, board[top][left]))
                || ((validTop && validCol) && simulateMove(board, kingSquare, board[top][curCol]))
                || ((validTop && validRight) && simulateMove(board, kingSquare, board[top][right]))
                || ((validRow && validRight) && simulateMove(board, kingSquare, board[curRow][right]))
                || ((validBot && validRight) && simulateMove(board, kingSquare, board[bottom][right]))
                || ((validBot && validCol) && simulateMove(board, kingSquare, board[bottom][curCol]))
                || ((validBot && validLeft) && simulateMove(board, kingSquare, board[bottom][left]))
                || ((validRow && validLeft) && simulateMove(board, kingSquare, board[curRow][left]))) {
            return false;
        }

        Piece curPiece = kingSquare.piece;
        ArrayList<Square> playerSquares = boardObject.getSquares(curPiece.getColor());
        ArrayList<Square> attackers = getKingAttackers(board, kingSquare);

        // Attempt to block attacker's path with another piece.
        // Only one attacker can be blocked.
        if (attackers.size() == 1) {
            Square atkSq = attackers.get(0);
            Piece atkPiece = atkSq.piece;
            ArrayList<Square> attackPath = atkPiece.getAtkPath(board, atkSq, kingSquare);

            for (Square curSq : playerSquares) {
                for (Square pathSq : attackPath) {
                    if (simulateMove(board, curSq, pathSq))
                        return false;
                }
            }

        }

        // Attempt to capture the potential attacker.
        // Only one attacker can be captured.
        if (attackers.size() == 1) {
            Square atkSq = attackers.get(0);
            for (Square curSq : playerSquares) {
                if (simulateMove(board, curSq, atkSq)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Get a list of all of the squares that contain a piece that can attack the
     * king.
     * 
     * @param board      Contains the state of all of the squares/pieces on the
     *                   board.
     * @param kingSquare The square that the king is located on.
     * @return List of all of the squares that contain a piece that can attack the
     *         king.
     */
    private ArrayList<Square> getKingAttackers(Square[][] board, Square kingSquare) {
        ArrayList<Square> list = new ArrayList<>();
        String enemyColor = (kingSquare.piece.getColor().equals("w")) ? "b" : "w";
        ArrayList<Square> enemySquares = boardObject.getSquares(enemyColor);

        // isCheck is not necessary because capturing the enemy king will end the game.
        for (Square attacker : enemySquares) {
            if (canMove(board, attacker, kingSquare)) {
                list.add(attacker);
            }
        }
        return list;
    }

    /**
     * Simulates a move using a combination of canMove and isCheck. Checks if a move
     * is possible and does not put the king in check.
     * 
     * @param board      Contains the state of all of the squares/pieces on the
     *                   board.
     * @param curSquare  The currently selected square.
     * @param destSquare The destination square that the user wants to move to.
     * @return True if the move can be made and does not put the king in check.
     */
    private boolean simulateMove(Square[][] board, Square curSquare, Square destSquare) {
        if (!canMove(board, curSquare, destSquare)) {
            return false;
        }

        Piece tempPiece = destSquare.piece;
        movePiece(board, curSquare, destSquare);

        // get king's location after you move piece
        Square kingSquare = kingLocation(board, destSquare.piece.getColor());

        if (isCheck(board, kingSquare)) {
            // reverts move
            movePiece(board, destSquare, curSquare);
            destSquare.piece = tempPiece;
            return false;
        }
        // reverts move
        movePiece(board, destSquare, curSquare);
        destSquare.piece = tempPiece;
        return true;
    }
}
