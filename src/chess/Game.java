package chess;

import java.util.Scanner;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;

public class Game {
        Board boardClass;
        Square [][] board;
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
                if(whiteTurn) {
                    System.out.print("White's move: ");
                }else {
                    System.out.print("Black's move: ");
                }
                
                //handle user input
                String str = sc.nextLine();
                String[] input= str.split(" ");
                char promotionType = ' ';
                if(input.length == 3) {
                     promotionType = input[2].charAt(0);
                }
                //convert user input into integers
                int curCol = input[0].charAt(0) - 'a';
                int curRow = input[0].charAt(1) - '0';
                int destCol = input[1].charAt(0) - 'a';
                int destRow = input[1].charAt(1) - '0';
                
                Square curSquare = board[curRow-1][curCol];
                Square destSquare = board[destRow-1][destCol];
                boolean validMove = movePiece(board,curSquare,destSquare);

                if(!validMove) {
                    System.out.println("Illegal move, try again");
                    continue;
                }
                
                //handle possible enpassant
                if(boardClass.getEnpassant() != null) {
                	if(prevEnpassant != null && prevEnpassant.equals(boardClass.getEnpassant())){
                		boardClass.setEnpassant(null);
                		prevEnpassant = null;
                	}
                	else {
                		prevEnpassant = boardClass.getEnpassant();
                	}
                }
                
                // handle promotion
                Piece curPiece = curSquare.piece;
                if (curPiece instanceof Pawn) {
                    promotion((Pawn) curPiece, destSquare, promotionType);
                }
                
                boardClass.printBoard();
                whiteTurn = !whiteTurn;
            }
        }
        
    public boolean movePiece(Square[][] board, Square curSquare, Square destSquare) {

        int curCol = curSquare.getCol();
        int curRow = curSquare.getRow();
        int destCol = destSquare.getCol();
        int destRow = destSquare.getRow();
        Piece curPiece = curSquare.getPiece();
        Piece destPiece = destSquare.getPiece();

        //current square needs to be a piece
        if(curPiece == null) return false;
        
        // cannot move piece to the same spot
        if (curCol == destCol && curRow == destRow) return false;

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
        
        //check to make sure dest isn't of same color
        if(destPiece != null && curPiece.sameColor(destPiece)) return false;
        
        // check if this piece can make this type of move.
        boolean validMove = curPiece.validMove(boardClass, curSquare, destSquare);
        
        if (!validMove) return false;

        // move current piece to new square
        Piece tempPiece = destSquare.piece; //temp piece for undoing move
        destSquare.piece = curSquare.piece;
        curSquare.piece = null;
        
        //Check if this move puts the cur player's king in check
        //UNDO move if true
        Square kingSquare = kingLocation(board,curPiece.getColor());
        if(isCheck(board,kingSquare)) {
            curSquare.piece = destSquare.piece;
            destSquare.piece = tempPiece;
            return false;
        }
        
        return true;
    }
    
    //locates the player's king
    private Square kingLocation(Square[][] board, String color) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Square curSquare = board[i][j];
                Piece curPiece = curSquare.piece;
                if((curPiece instanceof King) && curPiece.getColor().equals(color)) {
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
    
    //Checks if the king/selected square can be attacked
    public boolean isCheck(Square[][] board, Square kingSquare) {
        //Loops the entire board and sees if a piece can attack the king's square
        //could shorten this by only using opponent's pieces
        
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Square curSquare = board[i][j];
                boolean validAttack = movePiece(board,curSquare,kingSquare);
                if(validAttack) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isCheckmate(Square[][] board, Square kingSquare) {
        //needs to check which color is in checkmate
        
        int top = 1, right = 1;
        int bottom = -1, left = -1;
        int curRow = kingSquare.getRow();
        int curCol = kingSquare.getCol();
        
        //check if the king can move out of check itself
        if(movePiece(board,kingSquare,board[curRow+top][curCol+left])) return false;
        if(movePiece(board,kingSquare,board[curRow+top][curCol])) return false;
        if(movePiece(board,kingSquare,board[curRow+top][curCol+right])) return false;
        if(movePiece(board,kingSquare,board[curRow][curCol+right])) return false;
        if(movePiece(board,kingSquare,board[curRow+bottom][curCol+right])) return false;
        if(movePiece(board,kingSquare,board[curRow+bottom][curCol])) return false;
        if(movePiece(board,kingSquare,board[curRow+bottom][curCol+left])) return false;
        if(movePiece(board,kingSquare,board[curRow][curCol+left])) return false;

        
        return true;
    }
}
