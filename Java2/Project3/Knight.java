package project3;

import java.lang.Math;

public class Knight extends ChessPiece {
	
	public Knight(Player player) {
		super(player);
	}

	public Player player() {
		return super.getOwner();
	}
	public String type() {
		return "Knight";
	}
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//gets difference in rows and columns
		int cols = Math.abs(move.toColumn - move.fromColumn);
		int rows = Math.abs(move.toRow - move.fromRow);

		if(super.isValidMove(move, board)) {
			//if moving over two and up one, or over one and up two
			//then its true. Else it's false
			if((cols == 2 && rows == 1 )|| (rows == 2 && cols == 1)) {
				return true;
			}
		}
			return false;
	}
}
