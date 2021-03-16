package chess;

public class Chess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board test = new Board();
		test.printBoard();
		
		//testing out valid moves
		Square square = test.board[0][3];
		Square destSquare = test.board[2][5];
		boolean move = square.piece.validMove(test.board,square,destSquare);
	    
		System.out.println("Current piece is " + square);
		System.out.println("Valid move: " + move);
	}

}
