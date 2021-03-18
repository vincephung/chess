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
            Piece destPiece = destSquare.piece;
            Square kingSquare = kingLocation(board, curPiece.getColor());

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

            if (isCheckmate(board, kingSquare)) {
                gameOver = true;
                System.out.println("Checkmate");
                if (whiteTurn) {
                    System.out.println("White wins");
                } else {
                    System.out.println("Black wins");
                }

                break;
            }

            boardClass.printBoard();
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
        if (curPiece == null)
            return false;

        // cannot move piece to the same spot
        if (curCol == destCol && curRow == destRow)
            return false;

        // check boundaries
        if (curCol < 0 || curRow < 0 || curCol > 7 || curRow > 7 || destCol < 0 || destRow < 0 || destCol > 7
                || destRow > 7) {
            return false;
        }

        // player can only move their own colored pieces, ex: white moves white
        if (whiteTurn && curPiece.getColor().equals("b")) {
            return false;
        } else if (!whiteTurn && curPiece.getColor().equals("w")) {
            return false;
        }

        // check to make sure dest isn't of same color
        if (destPiece != null && curPiece.sameColor(destPiece))
            return false;

        // check if this piece can make this type of move.

        boolean validMove = curPiece.validMove(boardClass, curSquare, destSquare);

        if (!validMove)
            return false;

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

        System.out.println("top is " + top);
        System.out.println("bot is " + bottom);
        System.out.println("right is " + right);
        System.out.println("left is " + left);

        // attempts to move king in every direction to get out of check
        if ((simulateMove(board, kingSquare, board[top][left])) || (simulateMove(board, kingSquare, board[top][curCol]))
                || (simulateMove(board, kingSquare, board[top][right]))
                || (simulateMove(board, kingSquare, board[curRow][right]))
                || (simulateMove(board, kingSquare, board[bottom][right]))
                || (simulateMove(board, kingSquare, board[bottom][curCol]))
                || (simulateMove(board, kingSquare, board[bottom][left]))
                || (simulateMove(board, kingSquare, board[curRow][left]))) {
            return false;
        }

        /*
         * // attempts to move king in every direction to get out of check if
         * (simulateMove(board, kingSquare, board[top][left])) return false; if
         * (simulateMove(board, kingSquare, board[top][curCol])) return false; if
         * (simulateMove(board, kingSquare, board[top][right])) return false; if
         * (simulateMove(board, kingSquare, board[curRow][right])) return false; if
         * (simulateMove(board, kingSquare, board[bottom][right])) return false; if
         * (simulateMove(board, kingSquare, board[bottom][curCol])) return false; if
         * (simulateMove(board, kingSquare, board[bottom][left])) return false; if
         * (simulateMove(board, kingSquare, board[curRow][left])) return false;
         */

        // get cur player's squares
        Piece curPiece = kingSquare.piece;
        ArrayList<Square> playerSquares = boardClass.getSquares(curPiece.getColor());
        ArrayList<Square> attackers = getKingAttackers(board, kingSquare);

        // Check if you can block an attacker's path
        // if there is only one attacker, get that square/piece
        Square atkSq = attackers.get(0);
        Piece atkPiece = atkSq.piece;

        // path of the attacker
        ArrayList<Square> attackPath = atkPiece.getAtkPath(board, atkSq, kingSquare);

        // Can only block path if there is one attacker
        if (attackers.size() == 1) {
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
            for (Square curSq : playerSquares) {
                /*
                 * //if you can capture atk sq and king is no longer in check return false
                 * if(canMove(board,curSq,atkSq)) { //temporarily move piece
                 * movePiece(board,curSq,atkSq);
                 * 
                 * //check if king piece moved Square checkSquare = kingSquare;
                 * if(!(kingSquare.piece instanceof King)) { checkSquare = atkSq; } //king no
                 * longer in check if(!isCheck(board,checkSquare)) { //revert move
                 * movePiece(board,atkSq,curSq); return false; } //revert move
                 * movePiece(board,atkSq,curSq); }
                 */
                // if you can capture atk sq and king is no longer in check return false
                if (simulateMove(board, curSq, atkSq))
                    return false;
            }
        }

        return true;
    }

    /*
     * //returns the square 1 space around the current square that can be attacked
     * private ArrayList<Square> attackSquares(Square[][] board, Square curSquare){
     * ArrayList<Square> list = new ArrayList<>(); int curRow = curSquare.getRow();
     * int curCol = curSquare.getCol();
     * 
     * int top = curRow + 1; int bottom = curRow -1; int right = curCol + 1; int
     * left = curCol -1;
     * 
     * //Knights might need to be excluded because they cannot be blocked //pawns
     * too?
     * 
     * //this logic is messed up because it doesnt have to be 1 spot around //you
     * could block anywhere if(curSquare.piece instanceof Knight) {
     * 
     * }
     * 
     * //call check/attack on all squares around current square
     * if(isCheck(board,board[top][left])) list.add(board[top][left]);
     * if(isCheck(board,board[top][curCol])) list.add(board[top][curCol]);
     * if(isCheck(board,board[top][right])) list.add(board[top][right]);
     * if(isCheck(board,board[curRow][right])) list.add(board[curRow][right]);
     * if(isCheck(board,board[bottom][right])) list.add(board[bottom][right]);
     * if(isCheck(board,board[bottom][curCol])) list.add(board[bottom][curCol]);
     * if(isCheck(board,board[bottom][left])) list.add(board[bottom][left]);
     * if(isCheck(board,board[curRow][left])) list.add(board[curRow][left]);
     * 
     * return list; }
     */

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

        if (!canMove(board, curSquare, destSquare))
            return false;

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
