package project3;

public class Rook extends ChessPiece{
	private boolean hasMoved = false;

	public Rook(Player player) {
		super(player);
	}	
	public String type() {
		return  "Rook";
	}	
	public Player player() {
		return super.getOwner();
	}	
	public boolean getHasMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		//get differences in values
		int colDif = (move.toColumn - move.fromColumn);
		int rowDif = (move.toRow - move.fromRow);
		int rowAbs = Math.abs(rowDif);
		int colAbs = Math.abs(colDif);
		if(super.isValidMove(move, board)) {
			if(colDif == 0 || rowDif == 0) {
				//4 cases
				//col increases
				//col decreases
				//row increases
				//row decreases

				if(colDif != 0) {
					if(colDif > 0) {
						//col increases
						for(int i = 1; i < colAbs; i++) {
							//check if null
							if(board[move.fromRow]
									[move.fromColumn + i] != null)
								return false;
						}
						return true;
					}
					else {
						//col decreases
						for(int i = 1; i < colAbs; i++) {
							if(board[move.fromRow]
									[move.fromColumn - i] != null)
								return false;
						}
						return true;
					}
				}
				else {
					if(rowDif > 0) {
						//row increases
						for(int i = 1; i < rowAbs; i ++) {
							if(board[move.fromRow + i]
									[move.fromColumn] != null)
								return false;
						}
						return true;
					}
					else {
						//row decreases
						for(int i = 1; i < rowAbs; i++) {
							if(board[move.fromRow - i]
									[move.fromColumn] != null)
								return false;
						}
						return true;
					}
				}
			}
			//if neither of the above return false
			else
				return false;
		}
		//if not rowabs == 0 || colabs == 0
		else
			return false;
	}
}