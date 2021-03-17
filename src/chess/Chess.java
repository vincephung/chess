package chess;

import java.util.Scanner;

public class Chess {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Board test = new Board();
        Game game = new Game();
        test.printBoard();
        
        // take in user input
        Scanner sc = new Scanner(System.in);
        boolean gameOver = false;
        boolean whiteTurn = true;
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
            
            Square curSquare = test.board[curRow-1][curCol];
            Square destSquare = test.board[destRow-1][destCol];
            boolean validMove = game.movePiece(test.board,curSquare,destSquare,promotionType);

            if(!validMove) {
                System.out.println("Illegal move, try again");
                continue;
            }
            
            test.printBoard();
            whiteTurn = !whiteTurn;
        }

    }
    
}
