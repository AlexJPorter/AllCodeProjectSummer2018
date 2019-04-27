package project3;

/**********************************************************************
 * This class represents a pawn in the chess game.
 * It contains methods that return the type of piece it is,
 * the owner of the piece, and whether is has moved or not.
 * It also contains isValidMove() which determines if a move
 * is valid specifically for this pawn. Inherits ChessPiece.
 * 
 * @author Alex Porter, Jack Schettler, Frankie
 * @version 3/29/2018
 *********************************************************************/
public class Pawn extends ChessPiece {
	
	/** Determines if the pawn has moved or not. True if it has */
	private boolean hasMoved = false;

	/******************************************************************
	 * Creates a new pawn with passed player as the owner.
	 * Owner is stored in the super class, ChessPiece.
	 * 
	 * @param player Owner passed to new pawn
	 *****************************************************************/
	public Pawn(Player player) {
		super(player);
	}
	
	/******************************************************************
	 * Returns a string representing piece type,
	 * in this case "Pawn"
	 * 
	 * @return string Returns "Pawn"
	 *****************************************************************/
	public String type() {
		return  "Pawn";
	}
	
	/******************************************************************
	 * Returns the player that owns the piece
	 * 
	 * @return player Returns piece owner (Player)
	 *****************************************************************/
	public Player player() {
		return super.getOwner();
	}
	
	/******************************************************************
	 * Returns the field hasMoved, which represents if the piece has
	 * moved or not
	 * 
	 * @return boolean hasMoved True if piece has moved, false if not
	 ******************************************************************/
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/******************************************************************
	 * This setter sets hasMoved to given boolean
	 * 
	 * @param bool Sets hasMoved to given boolean
	 *****************************************************************/
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}
	
	/******************************************************************
	 * This method takes a move and determines if it is valid 
	 * specifically for a pawn. If the move is valid, it returns true.
	 * Else it returns false. See inner comments for more explanation.
	 * 
	 * @param Move move The move to be determined if valid
	 * @param IChessPiece[][] board A board of all the pieces
	 * @return boolean valid True if valid, false if not
	 *****************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		
		//initialize boolean
		boolean valid = false;
		
		//determine how many rows and columns the piece changes by
		int rows = Math.abs(move.toRow - move.fromRow);
		int cols = Math.abs(move.toColumn - move.fromColumn);
		
		//initialize two more booleans
		boolean moveForward;
		boolean enemyInFront = false;
		
		//determine if the piece is moving forward
		//false if it's not, true if it is
		if(this.player() == Player.BLACK) {
			moveForward = (move.toRow - move.fromRow > 0);
		}
		else {
			moveForward = (move.toRow - move.fromRow < 0);
		}
		
		//if there's an enemy piece in front of the pawn, set to true
		if(this.player() == Player.BLACK && move.fromRow < 7 && 
				board[move.fromRow + 1][move.fromColumn] != null && 
				board[move.fromRow + 1][move.fromColumn].player() 
				== Player.WHITE) {
			enemyInFront = true;
		}
		
		//same as above, but in case player is white
		if(this.player() == Player.WHITE && move.fromRow > 0 && 
				board[move.fromRow - 1][move.fromColumn] != null && 
				board[move.fromRow - 1][move.fromColumn].player() == 
				Player.BLACK) {
			enemyInFront = true;
		}

		//call ChessPiece isValidMove, a general method
		//that works for all pieces
		if(super.isValidMove(move, board)) {
			
			//if it moves two spaces forward(first move)
			if(rows == 2 && cols == 0 && moveForward && 
					
					//make sure you can't capture a piece
					board[move.toRow][move.toColumn] == null) {
				
				//make sure it hasn't moved before
				if(hasMoved == false) {
					
					//only if all of those are true, is valid true
					valid = true;
				
				}

			}
			else {
				
				//if row only changes by one, it's moving forward and
				//there's no enemy in front. OR you're taking a piece
				if((rows == 1 && cols == 0 && moveForward 
						&& !enemyInFront) || (rows == 1 && cols == 1 && 
						board[move.toRow][move.toColumn] != null && 
						board[move.toRow][move.toColumn].player() != 
						this.player() && moveForward)) {
					
					//then set valid to true
					valid = true;
				}
			}

		}	
		//if neither two cases resets valid to true, valid is false
		//but if the move makes it to the end of those ifs,
		//it returns true
		return valid;
	}
	
}

	
	