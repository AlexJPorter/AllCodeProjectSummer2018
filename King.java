package project3;

public class King extends ChessPiece {
	private boolean hasMoved = false;
	
	//holds a ChessModel so the piece can "see" the board
	private ChessModel engine;

	public King(Player player) {
		super(player);
	}

	public String type() {
		return  "King";
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
		boolean valid;
		//if the move is a castle then return true
			if(castlingValid(move, board))
				return true;
			
		if(super.isValidMove(move, board)) {
			
			//get change in position
			int rows = Math.abs(move.fromRow - move.toRow);
			int cols = Math.abs(move.fromColumn - move.toColumn);
			
			//if change in row and col is only one, return true
			//else return false
			if((rows == 1 && cols < 2) || (cols == 1 && rows < 2)) {
				valid = true;
			}
			else
				valid = false;
		}
		else {
			valid = false;
		}
		return valid;
	}

	private boolean castlingValid(Move move, IChessPiece[][] board) {
		//determines what row the king should be on
		int row;
		if (super.getOwner().equals(Player.WHITE)) {
			row = 7;
		}
		else
			row = 0;

		//if the move is to a specific position that represents
		//castling and neither piece has moved and spaces
		//between are empty
		if(move.fromColumn == 4 && move.fromRow == row 
				&& move.toColumn == 6 && move.toRow == row) {
			if(board[row][7] != null && board[row][7] instanceof Rook 
					&& ((Rook) board[row][7]).
					getHasMoved() == false &&
					board[row][5] == null && board[row][6] == null) {
				//pulls the model from ChessPiece
				this.engine = super.getEngine();
				//False if in check
				if(engine.inCheck(this.getOwner()))
					return false;
				//false if moving through check
				if(canAttack(row, 5))
					return false;
				else {
					//false if finishing in check
					if(canAttack(row, 6))
						return false;
					else {
						//move the pieces and return true
						executeShortCastle(board, row);
						return true;
					}
				}
			}
		}

		//Castling long, positions change slightly
		else if(move.fromColumn == 4 && move.fromRow == row &&
				move.toColumn == 2 && move.toRow == row) {
			if(board[row][0] instanceof Rook && ((Rook) board[row][0]).
					getHasMoved() == false &&
					board[row][1] == null && board[row][2] == null &&
					board[row][3] == null) {
				//pull the engine
				this.engine = super.getEngine();
				//false if in check
				if(canAttack(row, 4))
					return false;
				//false if moving through check
				if(canAttack(row, 3))
					return false;
				else {
					//false if finish in check
					if(canAttack(row, 2))
						return false;
					else {
						//move the pieces and return true
						executeLongCastle(board, row);
						return true;
					}
				}
			}
		}
		//if neither of the above are true, return false
		return false;
	}
	
	//move for castling queenside
	//for both, row denotes where the king is
	public void executeLongCastle(IChessPiece[][] board, int row) {    

		//move the rook
		board[row][0] = null;
		board[row][3] = new Rook(this.player());
		((Rook) board[row][3]).setHasMoved(true);

		//move the king
		board[row][4] = null;
		board[row][2] = this;
		((King) board[row][2]).setHasMoved(true);
	}
	//move to castle kingside
	public void executeShortCastle(IChessPiece[][] board, int row) {
		//move the rook
		board[row][7] = null;
		board[row][5] = new Rook(this.player());
		((Rook) board[row][5]).setHasMoved(true);

		//move the king
		board[row][4] = null;
		board[row][6] = this;
		((King) board[row][6]).setHasMoved(true);	
	}
	//used to see if a piece can attack a square
	//square determined by params
	public boolean canAttack(int rowC, int colC) {
		//pull the engine
		this.engine = super.getEngine();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				//if the piece can move to the square, return true
				Move potMove = new Move(row, col, rowC, colC);
				if (this.engine.pieceAt(row, col) != null && 
						this.engine.pieceAt(row, col) != this 
						&& this.engine.pieceAt(row, col).
						isValidMove(potMove, this.engine.getBoard())
						&& this.engine.pieceAt(row, col).player() != 
						this.player()) {
					return true;
				}
				else 
					continue;
			}
		}
		//if no piece is found, return false
		return false;
	}
}