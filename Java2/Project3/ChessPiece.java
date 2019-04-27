package project3;

/**********************************************************************
 * Chess piece class which contains the color of the piece, the type of piece,
 * and the basic necessities for a valid move pertaining to all chess pieces.
 **********************************************************************/
public abstract class ChessPiece implements IChessPiece {

	/** A string that describes what type of ChessPiece it is */
	public abstract String type();

	/** A static instance of the ChessModel */
	public static ChessModel engine;

	/** The owner of the piece */
	private Player owner;

	/**********************************************************************
	 * The constructor to create a chess piece.
	 * 
	 * @param player
	 *            The owner of the piece being created.
	 **********************************************************************/
	protected ChessPiece(Player player) {
		this.owner = player;
	}

	/**********************************************************************
	 * Returns who the owner of the chess piece is.
	 * 
	 * @return The owner of the piece.
	 **********************************************************************/
	protected Player getOwner() {
		return owner;
	}

	/**********************************************************************
	 * Checks basic validity for moving a chess piece that would apply to all
	 * pieces
	 * 
	 * @param move
	 *            Describes the move being checked, containing the coordinates of
	 *            where the piece is, and coordinates for where the piece is
	 *            attempting to move to.
	 * 
	 * @param board
	 *            The chess board itself containing the location of all pieces.
	 * 
	 * @return True if the move is valid, and false if the move is not valid
	 **********************************************************************/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		boolean valid;
		if (move.toColumn >= 8 || move.toRow >= 8) {
			throw new IndexOutOfBoundsException();
		}
		if (this != board[move.fromRow][move.fromColumn]) {
			throw new IllegalArgumentException();
		}

		if (move.toColumn == move.fromColumn && move.toRow == move.fromRow) {
			valid = false;
		} else if (board[move.fromRow][move.fromColumn] == null) {
			valid = false;
		} else if (board[move.toRow][move.toColumn] != null && board[move.toRow][move.toColumn].player() == owner) {
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

	/**********************************************************************
	 * Sets the instance of ChessModel, engine, to a different ChessModel instance.
	 * 
	 * @param model
	 *            The ChessModel instance that engine is being set to.
	 **********************************************************************/
	public static void setEngine(ChessModel model) {
		engine = model;
	}

	/**********************************************************************
	 * Returns the ChessModel, engine.
	 * 
	 * @return the ChessModel, engine.
	 **********************************************************************/
	public ChessModel getEngine() {
		return engine;
	}

}