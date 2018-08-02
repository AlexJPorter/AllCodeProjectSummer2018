package project3;

import java.lang.Math;

public class Bishop extends ChessPiece {
	
	public Bishop(Player player) {
		super(player);
	}
	public Player player() {
		return super.getOwner();
	}
	public String type() {
		return "Bishop";
	}
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//polymorphic line
		if(super.isValidMove(move, board)) {
			//difference between col and row
			int colSub = move.fromColumn - move.toColumn;
			int rowSub = move.fromRow - move.toRow;
			
			//checks all 4 directions
			if(colSub == rowSub || colSub == -rowSub ||
					-colSub == rowSub || -colSub == -rowSub) {
				
				int toCheck = Math.abs(move.fromColumn -
						move.toColumn);

				if(move.fromRow < move.toRow) {
					//row and column increase
					if(move.fromColumn < move.toColumn) {
						for(int i = 1; i < toCheck; i++) {
							//checks for null
							if(board[move.fromRow + i]
									[move.fromColumn + i] != null) {
								return false;
							}
						}
						return true;
					}
					else {
						//row increases, column decreases
						for(int i = 1; i < toCheck; i++) {
							if(board[move.fromRow + i]
									[move.fromColumn - i] != null) {	 
								return false;
							}
						}
						return true;
					}
				}
				else {
					//row decreases, column increases
					if(move.fromColumn < move.toColumn) {
						for(int i = 1; i < toCheck; i++) {
							if(board[move.fromRow - i]
									[move.fromColumn + i] != null) {								 
								return false;
							}
						}
						return true;
					}
					else {	
						//both decrease
						for(int i = 1; i < toCheck; i++) {
							if(board[move.fromRow - i]
									[move.fromColumn - i] != null) {								 
								return false;
							}
						}
						return true;
					}
				}
			}
			//runs if none of the four conditions are met
			else 
				return false;
		}
			return false;
	}
}

	
	