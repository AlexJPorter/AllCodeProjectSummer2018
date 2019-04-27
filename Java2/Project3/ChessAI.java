package project3;

public class ChessAI {
	
	ChessModel model;
	
	Player opponentOfAI;
	Player AIColor;
	
	public ChessAI(Player myColor, Player oppColor) {
		AIColor = myColor;
		opponentOfAI = oppColor;
	}
	
	public Move chessAI(Player player) {
		if(model.inCheck(AIColor)) {
			getOutOfCheck();
			//if it can't get out, then declare mate using isComplete();
		}
		//the below should be extracted into its own method
		for(int row = 0; row < 8; row ++) {
			for(int col = 0; col < 8; col++) {
				if(!(model.pieceAt(row, col).type().equals("King") ) && model.pieceAt(row, col).player() == AIColor) {
					int fromRow = row;
					int fromCol = col;
					for(int row2 = 0; row2 < 8; row2 ++) {
						for(int col2 = 0; col2 < 8; col2++) {
							Move potentialMove = new Move(fromRow, fromCol, row2, col2);
							if(model.isValidMove(potentialMove)) {
								model.move(potentialMove);
								if(model.inCheck(opponentOfAI)) {
									//if for all pieces, no other piece can take the piece that just moved
									model.setBoard(fromRow, fromCol, model.getBoard()[row2][col2]);
									model.setBoard(row2, col2, null);
									return potentialMove;
								}
								else {
									model.setBoard(fromRow, fromCol, model.getBoard()[row2][col2]);
									model.setBoard(row2, col2, null);
									continue;
								}
							}
						}
					}
				}
			}
		}
	}
	public Move getOutOfCheck() {
		for(int row = 0; row < 8; row ++) {
			for(int col = 0; col < 8; col++) {
				if (model.pieceAt(row, col).player() == AIColor) {
					int fromRow = row;
					int fromCol = col;
					for(int row2 = 0; row2 < 8; row2 ++) {
						for(int col2 = 0; col2 < 8; col2++) {
							Move potentialMove = new Move(fromRow, fromCol, row2, col2);
							if(model.isValidMove(potentialMove)) {
								model.move(potentialMove);
								if(!(model.inCheck(AIColor))) {
									model.setBoard(fromRow, fromCol, model.getBoard()[row2][col2]);
									model.setBoard(row2, col2, null);
									return potentialMove;
							}
								else {
									//reverse the move
									model.setBoard(fromRow, fromCol, model.getBoard()[row2][col2]);
									model.setBoard(row2, col2, null);
								}
						}
						}
					}
				}
			}
		}
		return null;
	}
	public Move takeAnUnguardedPiece() {
		for(int row = 0; row < 8; row ++) {
			for(int col = 0; col < 8; col++) {
				if (model.pieceAt(row, col).player() != AIColor) {
					int toRow = row;
					int toCol = col;
					for(int row2 = 0; row2 < 8; row2 ++) {
						for(int col2 = 0; col2 < 8; col2++) {
							if(model.pieceAt(row2, col2).player() == AIColor) {
								Move potentialMove = new Move(row2, col2, toRow, toCol);
								if(model.isValidMove(potentialMove) && isUnguarded(toRow, toCol, opponentOfAI)) {
									return potentialMove;
								}
						}
					}
				}
			}
		}
	}
		Move noMoveFound = new Move(-1, -1, -1, -1);
		return noMoveFound;
	}
	public void retreatAnAttackedPiece() {
		for(int row = 0; row < 8; row ++) {
			for(int col = 0; col < 8; col++) {
				if (model.pieceAt(row, col).player() == AIColor && isUnguarded(row, col, AIColor)) {
					
				}
			}

		}
	}
	public boolean isUnguarded (int rowPos, int colPos, Player player) {
		for(int row = 0; row < 8; row ++) {
			for(int col = 0; col < 8; col++) {
				if(model.pieceAt(row, col).player() == player) {
					int toRow = row;
					int toCol = col;
					for(int row2 = 0; row2 < 8; row2 ++) {
						for(int col2 = 0; col2 < 8; col2++) {
							if(model.pieceAt(row2, col2).player() == player) {
								Move potentialMove = new Move(row2, col2, toRow, toCol);
								if(model.isValidMove(potentialMove)) {
									return false;
								}	
							}
						}
					}
				}
			}
		}
		return true;
	}
}
/* Order of AI opertions:
 * Put your opponent in check //"Always repeat" - GM Finegold. We want draw by repetition as much as possible
 *  - As long as you don't lose your piece
 *  EITHER Take an unguarded piece OR Move an unguarded piece away from danger
 *  Move a piece(pawns first) toward the king
 *   - As long as you don't lose that piece
 */
