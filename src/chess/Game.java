package chess;

import java.util.ArrayList;
import java.util.Scanner;

import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;

public class Game {
    Board boardClass;
    Square[][] board;
    boolean whiteTurn;
    boolean gameOver;
    Square prevEnpassant;

    public Game() {
        boardClass = new Board();
        board = boardClass.board;
        whiteTurn = true;
        gameOver = false;
        prevEnpassant = null;
    }

    public void startGame() {
        boardClass.printBoard();

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
            if (input.length == 3) {
                promotionType = input[2].charAt(0);
            }
            // convert user input into integers
            int curCol = input[0].charAt(0) - 'a';
            int curRow = input[0].charAt(1) - '0';
            int destCol = input[1].charAt(0) - 'a';
            int destRow = input[1].charAt(1) - '0';

            Square curSquare = board[curRow - 1][curCol];
            Square destSquare = board[destRow - 1][destCol];
            Piece curPiece = curSquare.piece;
            String enemyColor = (curSquare.piece.getColor().equals("w")) ? "b" : "w";
            Square enemyKing = kingLocation(board, enemyColor);

            // player can only move their own colored pieces, ex: white moves white
            if (whiteTurn && curPiece.getColor().equals("b")) {
                continue;
            } else if (!whiteTurn && curPiece.getColor().equals("w")) {
                continue;
            }
            
            if (!simulateMove(board, curSquare, destSquare)) {
                System.out.println("Illegal move, try again");
                continue;
            }

            // moves piece if move was considered valid
            movePiece(board, curSquare, destSquare);

            // handle possible enpassant
            if (boardClass.getEnpassant() != null) {
                if (prevEnpassant != null && prevEnpassant.equals(boardClass.getEnpassant())) {
                    boardClass.setEnpassant(null);
                    prevEnpassant = null;
                } else {
                    prevEnpassant = boardClass.getEnpassant();
                }
            }

            // handle promotion
            if (curPiece instanceof Pawn) {
                promotion((Pawn) curPiece, destSquare, promotionType);
            }

            boardClass.printBoard();

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
    }

    public boolean canMove(Square[][] board, Square curSquare, Square destSquare) {

        int curCol = curSquare.getCol();
        int curRow = curSquare.getRow();
        int destCol = destSquare.getCol();
        int destRow = destSquare.getRow();
        Piece curPiece = curSquare.getPiece();
        Piece destPiece = destSquare.getPiece();

        // current square needs to be a piece
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
        
        /*
        // player can only move their own colored pieces, ex: white moves white
        if (whiteTurn && curPiece.getColor().equals("b")) {
            return false;
        } else if (!whiteTurn && curPiece.getColor().equals("w")) {
            return false;
        }
        */

        // check to make sure dest isn't of same color
        if (destPiece != null && curPiece.sameColor(destPiece)) {
            return false;
        }

        // check if this piece can make this type of move.

        boolean validMove = curPiece.validMove(boardClass, curSquare, destSquare);

        if (!validMove) {
            return false;
        }

        return true;
    }

    // move current piece to new square
    public void movePiece(Square[][] board, Square curSquare, Square destSquare) {
        destSquare.piece = curSquare.piece;
        curSquare.piece = null;
    }

    // locates the player's king
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

    // Checks if the king/selected square can be attacked
    public boolean isCheck(Square[][] board, Square curSquare) {
        // Loops the entire board and sees if a piece can attack the king's square
        String enemyColor = (curSquare.piece.getColor().equals("w")) ? "b" : "w";
        ArrayList<Square> enemySquares = boardClass.getSquares(enemyColor);
        for (Square attacker : enemySquares) {
            if (canMove(board, attacker, curSquare)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(Square[][] board, Square kingSquare) {
        // needs to check which color is in checkmate

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

        // get cur player's squares
        Piece curPiece = kingSquare.piece;
        ArrayList<Square> playerSquares = boardClass.getSquares(curPiece.getColor());
        ArrayList<Square> attackers = getKingAttackers(board, kingSquare);

        // Check if you can block an attacker's path
        // if there is only one attacker, get that square/piece
        if (attackers.size() == 1) {
            Square atkSq = attackers.get(0);
            Piece atkPiece = atkSq.piece;
            // path of the attacker
            ArrayList<Square> attackPath = atkPiece.getAtkPath(board, atkSq, kingSquare);

            // Can only block path if there is one attacker
            // check if a piece can block one of the squares in the atk path
            for (Square curSq : playerSquares) {
                for (Square pathSq : attackPath) {
                    if (simulateMove(board, curSq, pathSq))
                        return false;
                }
            }

        }

        // check if you can capture the attacking piece
        // can only capture one piece
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

    private ArrayList<Square> getKingAttackers(Square[][] board, Square curSquare) {
        ArrayList<Square> list = new ArrayList<>();
        String enemyColor = (curSquare.piece.getColor().equals("w")) ? "b" : "w";
        ArrayList<Square> enemySquares = boardClass.getSquares(enemyColor);

        // does not need isCheck because we only need to get the king attackers
        // You can potentially capture the enemy king and in doing so put yourself in
        // check, but the game is already over
        for (Square attacker : enemySquares) {
            if (canMove(board, attacker, curSquare)) {
                list.add(attacker);
            }
        }

        return list;
    }

    private boolean simulateMove(Square[][] board, Square curSquare, Square destSquare) {
        // combines canMove and isCheck
        // Basically if a piece moves and the king is NOT in check
        // Technically this should be called "valid move"

        if (!canMove(board, curSquare, destSquare)) {
            return false;
        }
        
        movePiece(board, curSquare, destSquare);

        // get king's location after you move piece
        Square kingSquare = kingLocation(board, destSquare.piece.getColor());

        if (isCheck(board, kingSquare)) {
            // reverts move
            movePiece(board, destSquare, curSquare);
            return false;
        }
        // reverts move
        movePiece(board, destSquare, curSquare);
        return true;

    }
}
