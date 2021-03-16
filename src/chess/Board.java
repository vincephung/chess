package chess;

import pieces.Piece;

public class Board {
	
	private Piece[][] board = new Piece[8][8];
	
	public Board() {
		
	}
	
	public void printBoard() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(board[i][j] != null) {
					System.out.print(board[i][j] + " ");
				}
				else {
					if(i % 2 == 0) {
						if(j % 2 == 0) {
							System.out.print("   ");
						}
						else {
							System.out.print("## ");
						}
					}
					else {
						if(j % 2 == 0) {
							System.out.print("## ");
						}
						else {
							System.out.print(("   "));
						}
					}
				}
			}
			System.out.println(i + 1);
		}
		//print letters in last line
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	public boolean move(int start, int finish) {
		return false;
	}
}
