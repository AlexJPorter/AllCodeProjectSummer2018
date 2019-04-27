package project3;

/**********************************************************************
 * This class holds an array of IChessPiece s, and uses those pieces
 * to run a chess game. Check, checkmate, and pawn promotion are 
 * handled here. Used by panel class to update the board as well.
 * Implements the IChessModel class which enforces some method
 * definitions 
 * @author Alex Porter, Jack Schettler, Frankie
 * @version 3/28/2018
 *********************************************************************/

public class ChessModel implements IChessModel {
	/** Value of current player turn */
	public Player player; 

	/** Holds the board that holds each piece*/
	public IChessPiece[][] board;

	/** Used in the undoMove method */
	private IChessPiece previousPiece = null;

	/******************************************************************
	 * Creates a model, sets the board to 8x8
	 * Sets the player to the passed player param.
	 * 
	 * @param player Sets model player to given param
	 *****************************************************************/
	public ChessModel(Player player) {
		//board is always 8x8
		board = new IChessPiece[8][8];
		this.player = player;

		//sets up the board
		reset(player);
	}
	/******************************************************************
	 * Fills the IChessPiece board with pieces. Takes the param player
	 * so the AI can play from both sides.
	 * 
	 * @param player Resets player if AI plays as WHITE
	 *****************************************************************/
	public void reset(Player player) {
		this.player = player;

		//set board to null
		for (int row = 0; row < 8; row ++)
			for (int col = 0; col < 8; col++) 
				board[row][col] = null;

		//fill black rank
		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);

		//fill black pawns
		for (int i = 0; i < 8; i++)
			board[1][i] = new Pawn(Player.BLACK);

		//fill white ranks
		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);

		//fill white pawns
		for (int i = 0; i < 8; i++)
			board[6][i] = new Pawn(Player.WHITE);
	}

	/******************************************************************
	 * Returns the IChessPiece[][] board that holds all the pieces 
	 * 
	 * @return board The IChessPiece board
	 *****************************************************************/
	public IChessPiece[][] getBoard() {
		return board;
	}

	/******************************************************************
	 * Checks if current player is in checkmate, if they are, returns
	 * true. If they can get out of check, it returns false. 
	 * 
	 * @return boolean True if game is over, false if it's not
	 *****************************************************************/
	public boolean isComplete() {
		
		//defines a move to try to get out of check
		Move getOutOfCheck;
		
		//only checks if the player is in check, if they aren't then 
		//they obviously cannot be checkmated
		if(inCheck(player)) {
			
			//moves every piece to every position on the board
			for(int row = 0; row < 8; row ++) 
				for(int col = 0; col < 8; col++) 
					for(int row2 = 0; row2 < 8; row2 ++) 
						for(int col2 = 0; col2 < 8; col2++) 
							
							//if the square has a piece,
							//and the piece is the player's color
							if(pieceAt(row, col) != null && pieceAt(row, col).player() == player) {
								
								//define the move to the new square
								getOutOfCheck = new Move(row, col, row2, col2);
								
								//if it's valid, do the move
								if(isValidMove(getOutOfCheck)) {
									move(getOutOfCheck);
									
									//if you're out of check return
									//false and undo the move
									if(!(inCheck(player))) {
										undoLastMove(getOutOfCheck);
										return false;
									}
									else
										
										//if not, undo and continue
										undoLastMove(getOutOfCheck);
								}
							}



			//if no move is found that exits check, return true
			return true;
		}
		//false if not in check
		else
			return false;
	}
	/******************************************************************
	 * This method calls the isValidMove of each particular piece.
	 * The particular piece isValid move is called with polymorphism.
	 * It also checks if doing the move puts the player in check.
	 * If it does, it returns false. It also makes sure the 
	 * player gets out of check if they are in check.
	 * Checks if a player tries to move out of turn, and if an empty
	 * square has been clicked. In all those cases, returns false.
	 * 
	 * @param Move move The move to be determined if valid or not
	 * @return boolean True if valid, false if not
	 *****************************************************************/
	public boolean isValidMove(Move move) {
		
		//not an empty square and current player's turn
		if (board[move.fromRow][move.fromColumn] != null && 
				board[move.fromRow][move.fromColumn].player() 
				== this.currentPlayer()) 

			//if particular piece says the move is valid
			if (board[move.fromRow][move.fromColumn].
					isValidMove(move, board)) {

				//do the move, see if you're still in check
				move(move);
				
				//if you are, undo and say it's not valid
				if (board[move.toRow][move.toColumn] != null && 
						inCheck(board[move.toRow][move.toColumn].
								player())) {
					undoLastMove(move);;
					return false;
				}
				
				//if you aren't, undo and return valid
				else {
					undoLastMove(move);
					return true;
				}
			}
		return false;
	}

	/******************************************************************
	 * Changes piece on the from row and column on the board to the to
	 * row and to column position on the board. Also saves the current
	 * piece on toRow,toColumn to be used in undoMove later. 
	 * 
	 * @param Move move Move to be committed to the board
	 *****************************************************************/
	public void move(Move move) {
		
		//Although moves passed to this method should not start at
		//blank squares, castling manages to "slip by" that. But, 
		//it cannot move forward with this if statement here.
		if(!(board[move.fromRow][move.fromColumn] == null)) {
			previousPiece = board[move.toRow][move.toColumn];
			this.board[move.toRow][move.toColumn] = 
					this.board[move.fromRow][move.fromColumn];
			this.board[move.fromRow][move.fromColumn] = null;
		}
	}

	/******************************************************************
	 * This method determines if the passes player is in check. This
	 * method finds the king of the passed player, then sees if an
	 * enemy player can move on top of that king. If they can, the
	 * player is in check. If not, the player is not in check.
	 * 
	 * @param Player player Player that check status is calculated for
	 * @return boolean True if player in check, false if not
	 *****************************************************************/
	public boolean inCheck(Player player) {
		//defines position of players king
		int kingRow = 0;
		int kingCol = 0;
		
		// for all pieces, see if the piece is the player king
		for (int row = 0; row < 8; row++) 
			for (int col = 0; col < 8; col++) 
				
				//only the player's king passes the following if
				if (pieceAt(row, col) != null && pieceAt(row, col).
					type().equals("King")
					&& pieceAt(row, col).player() == player) {
						kingRow = row;
						kingCol = col;
						
						//once found, break out since there's only one
						break;
				}
		//go through every square on the board again
		for (int row = 0; row < 8; row++) 
			for (int col = 0; col < 8; col++) {
				
				//create a move that goes to current king
				Move potMove = new Move(row, col, kingRow, kingCol);
				
				//if it's valid and belongs to enemy player return true
				if (pieceAt(row, col) != null && pieceAt(row, col).
						isValidMove(potMove, board)
						&& pieceAt(row, col).player() != player) 
					return true;
			}
		//if no valid move is found player is not in check return false
		return false;
	}

	/******************************************************************
	 * Returns the current player
	 * 
	 * @return Player player The current player
	 *****************************************************************/
	public Player currentPlayer() {
		return player;
	}
	/******************************************************************
	 * Rotates the current player to the next one
	 * 
	 *****************************************************************/
	public void nextPlayer() {
		if (player == Player.WHITE)
			player = Player.BLACK;
		else
			player = Player.WHITE;

	}
	
	/******************************************************************
	 * Returns the piece at the row and column inside the board
	 * 
	 * @param row Row position in IChessPiece[][] board
	 * @param col Column position in IChessPiece[][] board
	 * @return IChessPiece piece Piece at position
	 *****************************************************************/
	public IChessPiece pieceAt(int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 * Reverses the given move. If a piece was taken on the given move
	 * it would be stored in preivousPiece. If no piece was taken, 
	 * previousPiece would be null.
	 * 
	 * @param move Move to be reversed
	 *****************************************************************/
	public void undoLastMove(Move move) {
		//Set whatever piece at to position to from position
		board[move.fromRow][move.fromColumn] = 
				board[move.toRow][move.toColumn];
		
		//set to position to whatever was there last
		board[move.toRow][move.toColumn] = previousPiece;
	}

	/******************************************************************
	 * Sets the given position on the board to the given piece.
	 * 
	 * @param row Row position in board
	 * @param col Column position in board
	 * @param piece IChessPiece to be set
	 *****************************************************************/
	public void setBoard(int row, int col, IChessPiece piece) {
		board[row][col] = piece;
	}

	/******************************************************************
	 * Updates the hasMoved boolean for pawns, rooks, and kings
	 * Since the ability for pawns, rooks, and kings to move
	 * sometimes depends on if they have moved or not i.e(castle),
	 * this variable needs to be tracked. Runs after a move has been
	 * committed in the panel class.
	 * 
	 * @param row Row position on board
	 * @param col Column position on board
	 *****************************************************************/
	public void updateHasMoved(int row, int col) {
		//if pawn, king, or rook, setHasMoved to true;
		//only runs after a move has been committed
		if(board[row][col] instanceof Pawn )
			((Pawn) board[row][col]).setHasMoved(true);
		if(board[row][col] instanceof King)
			((King) board[row][col]).setHasMoved(true);
		if(board[row][col] instanceof Rook)
			((Rook) board[row][col]).setHasMoved(true);
	}
	/******************************************************************
	 * Determines if a pawn can be promoted or not. If the pawn is
	 * white and on the back rank, promote it. If it's black and 
	 * on the front rank, promote it. Else don't promote it.
	 * 
	 * @param col Column pawn is on
	 * @return boolean True if can promote, false if cannot
	 *****************************************************************/
	public boolean pawnPromotion(int col) {
		
		//white pawn on last row
		if(board[0][col] != null &&
				board[0][col].player() == Player.WHITE && 
				board[0][col] instanceof Pawn)
			return true;
		
		//black pawn on first (nearest) row
		else if(board[7][col] != null &&
				board[7][col].player() == Player.BLACK && 
				board[7][col] instanceof Pawn)
			return true;
		
		//if neither, don't promote
		else
			return false;
	}
}
