package project3;

/**********************************************************************
 * This class represents a queen in the Chess game.
 * It uses the Rook and Bishop class to determine if a move is valid.
 * This class also extends ChessPiece, a super class that is above
 * all versions of chess pieces.
 * 
 * @author Alex Porter, Jack Schettler, Frankie
 *********************************************************************/
public class Queen extends ChessPiece {
	
	/******************************************************************
	 * This constructor creates a new piece that belongs
	 * to the player that is passed as an argument. That
	 * player data is saved in the super class
	 * 
	 * @param player
	 *****************************************************************/
	public Queen(Player player) {
		super(player);
	}
	/******************************************************************
	 * This getter returns a string representing what type of piece
	 * 'this' is. In this case, Queen.
	 * 
	 * @return string Returns name of piece, Queen
	 *****************************************************************/
	public String type() {
		return "Queen";
	}
	
	/******************************************************************
	 * This getter returns the owner of the piece
	 * 
	 * @return Player The owner of the piece
	 *****************************************************************/
	public Player player() {
		return super.getOwner();
	}
	
	/**
	 * This isValidMove specific to the piece determines if the given
	 * move is valid or invalid. It does so by creating a Rook and 
	 * Bishop, and replacing the 
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Bishop b = new Bishop(player());
		Rook r = new Rook(player());
		
		board[move.fromRow][move.fromColumn] = r;
		if(r.isValidMove(move, board)) {
			board[move.fromRow][move.fromColumn] = this;
			return true;
		}
		board[move.fromRow][move.fromColumn] = b;
		if(b.isValidMove(move, board)) {
			board[move.fromRow][move.fromColumn] = this;
			return true;
		}
		else {
			board[move.fromRow][move.fromColumn] = this;
			return false;
		}
	}

}
