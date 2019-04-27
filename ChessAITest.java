package project3;

import java.util.Random;

public class ChessAITest {

	ChessModel model;
	Player playerColor;
	Player AIColor;
	private int turns;

	public ChessAITest(Player oppColor, ChessModel model) {
		playerColor = oppColor;
		if (playerColor == Player.WHITE)
			AIColor = Player.BLACK;
		else
			AIColor = Player.WHITE;
		this.model = model;
		reset();
	}

	private Move getOutOfCheck() {
		int fromRow = 0;
		int fromCol = 0;
		int toRow = 0;
		int toCol = 0;
		for (int row = 0; row < 8; row++)
			for (int col = 0; col < 8; col++)
				if (model.pieceAt(row, col) instanceof King)
					if (((King) model.pieceAt(row, col)).getOwner() == AIColor) {
						fromRow = row;
						fromCol = col;
					}

		model.nextPlayer();
		for (int row = 0; row < 8; row++)
			for (int col = 0; col < 8; col++) {
				Move move = new Move(row, col, fromRow, fromCol);
				if (model.isValidMove(move)) {
					toRow = row;
					toCol = col;
				}
			}
		model.nextPlayer();
		Move move = randomToSquare(toRow, toCol);
		if (move == null)
			return randomMove();
		else
			return move;
	}

	private Move randomToSquare(int toRow, int toCol) {
		Move move = new Move(0, 0, 0, 0);
		Random rand = new Random();
		int i = 0;
		while (i < 100 && !model.isValidMove(move)) {
			int fromRow = rand.nextInt(8);
			int fromCol = rand.nextInt(8);
			i++;
			move = new Move(fromRow, fromCol, toRow, toCol);
		}
		if (!model.isValidMove(move)) {
			return null;
		} else
			return move;
	}

	public Move move() {

		if (model.inCheck(AIColor))
			return getOutOfCheck();
		if (turns == 0) {
			incrementTurns();
			return turnOneMove(AIColor);
		} else {
			Move move = outOfDanger();
			if (move != null)
				return move;
			else
				return mainMove();
		}

	}

	public Move turnOneMove(Player player) {
		Random rand = new Random();
		int n = rand.nextInt(3);
		if (player == Player.BLACK) {
			if (n == 0)
				return new Move(0, 1, 2, 2);
			if (n == 1)
				return new Move(0, 6, 2, 5);
			else
				return new Move(1, 4, 3, 4);
		} else {
			if (n == 0)
				return new Move(7, 1, 5, 2);
			if (n == 1)
				return new Move(7, 6, 5, 5);
			else
				return new Move(6, 4, 4, 4);
		}
	}

	public void incrementTurns() {
		turns++;
	}

	public Move mainMove() {
		Move tempMove;
		Move betterMove = new Move(0, 0, 0, 0);
		int tempScore = 0;
		int highScore = 0;
		for (int fromRow = 0; fromRow < 8; fromRow++)
			for (int fromCol = 0; fromCol < 8; fromCol++)
				for (int toRow = 0; toRow < 8; toRow++)
					for (int toCol = 0; toCol < 8; toCol++) {
						tempMove = new Move(fromRow, fromCol, toRow, toCol);
						if (model.isValidMove(tempMove)) {
							if (model.pieceAt(toRow, toCol) instanceof Queen) {
								tempScore = 9;
								if (tempScore > highScore) {
									highScore = tempScore;
									betterMove = tempMove;
								}
							} else if (model.pieceAt(toRow, toCol) instanceof Rook) {
								tempScore = 5;
								if (tempScore > highScore)
									if (!(model.pieceAt(fromRow, fromCol) instanceof Queen)
											|| checkUnguarded(tempMove)) {
										highScore = tempScore;
										betterMove = tempMove;
									}
							} else if (model.pieceAt(toRow, toCol) instanceof Bishop
									|| model.pieceAt(toRow, toCol) instanceof Knight) {
								tempScore = 3;
								if (tempScore > highScore)
									if (!(model.pieceAt(fromRow, fromCol) instanceof Queen) || checkUnguarded(tempMove))
										if (!(model.pieceAt(fromRow, fromCol) instanceof Rook)
												|| checkUnguarded(tempMove)) {
											highScore = tempScore;
											betterMove = tempMove;
										}

							} else if (model.pieceAt(toRow, toCol) instanceof Pawn) {
								tempScore = 1;
								if (tempScore > highScore)
									if (model.pieceAt(fromRow, fromCol) instanceof Pawn || checkUnguarded(tempMove)) {
										highScore = tempScore;
										betterMove = tempMove;
									}
							}
						}
					}
		if (model.isValidMove(betterMove))
			return betterMove;
		else
			return randomMove();
	}

	private Move outOfDanger() {
		for (int toRow = 0; toRow < 8; toRow++)
			for (int toCol = 0; toCol < 8; toCol++)
				if (model.pieceAt(toRow, toCol) instanceof Queen)
					if (checkInDanger(toRow, toCol))
						return moveToSafety(toRow, toCol);

		for (int toRow = 0; toRow < 8; toRow++)
			for (int toCol = 0; toCol < 8; toCol++)
				if (model.pieceAt(toRow, toCol) instanceof Rook)
					if (checkInDanger(toRow, toCol))
						return moveToSafety(toRow, toCol);

		for (int toRow = 0; toRow < 8; toRow++)
			for (int toCol = 0; toCol < 8; toCol++)
				if (model.pieceAt(toRow, toCol) instanceof Bishop || model.pieceAt(toRow, toCol) instanceof Knight)
					if (checkInDanger(toRow, toCol))
						return moveToSafety(toRow, toCol);
		return null;
	}

	private Move moveToSafety(int row, int col) {
		for (int toRow = 7; toRow >= 0; toRow--)
			for (int toCol = 7; toCol >= 0; toCol--) {
				Move move = new Move(row, col, toRow, toCol);
				if (model.isValidMove(move))
					if (!checkInDanger(toRow, toCol)) {
						return move;
					}

			}
		return null;
	}

	private boolean checkInDanger(int row, int col) {
		model.nextPlayer();
		for (int fromRow = 0; fromRow < 8; fromRow++)
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				Move move = new Move(fromRow, fromCol, row, col);
				if (model.isValidMove(move)) {
					model.nextPlayer();
					return true;
				}

			}
		model.nextPlayer();
		return false;
	}

	private boolean checkUnguarded(Move pmove) {
		model.nextPlayer();
		int toRow = pmove.toRow;
		int toCol = pmove.toColumn;
		Move random = pieceToRandom(toRow, toCol);
		model.move(random);
		for (int fromRow = 0; fromRow < 8; fromRow++)
			for (int fromCol = 0; fromCol < 8; fromCol++) {
				Move move = new Move(fromRow, fromCol, toRow, toCol);
				if (model.isValidMove(move)) {
					model.undoLastMove(random);
					model.nextPlayer();
					return false;
				}

			}
		model.undoLastMove(random);
		model.nextPlayer();
		return true;

	}

	private Move randomMove() {
		Move move = new Move(0, 0, 0, 0);
		Random rand = new Random();
		while (!model.isValidMove(move)) {
			int a = rand.nextInt(8);
			int b = rand.nextInt(8);
			int c = rand.nextInt(8);
			int d = rand.nextInt(8);
			if (model.pieceAt(a, b) instanceof King && !model.inCheck(model.currentPlayer())) {
			} else {
				move = new Move(a, b, c, d);
			}
		}

		return move;
	}

	public Move pieceToRandom(int fromRow, int fromCol) {
		Move move = new Move(0, 0, 0, 0);
		Random rand = new Random();
		int i = 0;
		while (!model.isValidMove(move) || i == 100) {
			int toRow = rand.nextInt(8);
			int toCol = rand.nextInt(8);
			i++;
			move = new Move(fromRow, fromCol, toRow, toCol);
		}
		return move;
	}

	public void reset() {
		turns = 0;
	}

}

/*
 * Order of AI opertions: Put your opponent in check //"Always repeat" - GM
 * Finegold. We want draw by repetition as much as possible - As long as you
 * don't lose your piece EITHER Take an unguarded piece OR Move an unguarded
 * piece away from danger Move a piece(pawns first) toward the king - As long as
 * you don't lose that piece
 */