package project3;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessPanel extends JPanel {

	private ButtonListener listener;
	private JButton[][] board;
	private final int BOARD_SIZE = 8;

	private ChessModel model;

	private int fromRow = 0;
	private int fromCol = 0;
	private int toRow = 0;
	private int toCol = 0;
	private int numPlayers;
	private Player player;
	private ChessAITest AI;

	private JMenuItem newGameItem;
	private JMenuItem quitItem;
	private JMenuItem restartItem;
	private JMenuItem undoItem;

	private ImageIcon kingW;
	private ImageIcon queenW;
	private ImageIcon bishopW;
	private ImageIcon knightW;
	private ImageIcon rookW;
	private ImageIcon pawnW;

	private ImageIcon kingB;
	private ImageIcon queenB;
	private ImageIcon bishopB;
	private ImageIcon knightB;
	private ImageIcon rookB;
	private ImageIcon pawnB;

	public ChessPanel(JMenuItem pquitItem, JMenuItem pNewGameItem, JMenuItem prestartItem, JMenuItem pundoItem) {

		newGameItem = pNewGameItem;
		quitItem = pquitItem;
		restartItem = prestartItem;
		undoItem = pundoItem;

		numPlayers = askNumberOfPlayers();
		player = Player.WHITE;

		if (numPlayers == 1) {
			player = askPlayerColor();
			model = new ChessModel(player);
			AI = new ChessAITest(player, model);

		} else
			model = new ChessModel(Player.WHITE);

		setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE)); // room for top row

		listener = new ButtonListener();
		quitItem.addActionListener(listener);
		newGameItem.addActionListener(listener);
		restartItem.addActionListener(listener);
		undoItem.addActionListener(listener);

		board = new JButton[BOARD_SIZE][BOARD_SIZE];
		// pieces = new IChessPiece[BOARD_SIZE][BOARD_SIZE];

		kingW = createIcon("wKing.png");
		queenW = createIcon("wQueen.png");
		bishopW = createIcon("wBishop.png");
		knightW = createIcon("wKnight.png");
		rookW = createIcon("wRook.png");
		pawnW = createIcon("wPawn.png");

		kingB = createIcon("bKing.png");
		queenB = createIcon("bQueen.png");
		bishopB = createIcon("bBishop.png");
		knightB = createIcon("bKnight.png");
		rookB = createIcon("bRook.png");
		pawnB = createIcon("bPawn.png");

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (row % 2 == 1 && col % 2 == 0) {
					board[row][col] = createBlackButton();
					add(board[row][col]);
				} else if (row % 2 == 0 && col % 2 == 1) {
					board[row][col] = createBlackButton();
					add(board[row][col]);
				} else
					board[row][col] = createWhiteButton();
				add(board[row][col]);
			}
		}
		setBoard();
		if (numPlayers == 1 && player == Player.BLACK) {
			model.nextPlayer();
			computerTurn.start();
		}

	}

	private void setBoard() {

		for (int row = 0; row < 8; row++)
			for (int col = 0; col < 8; col++) {
				board[row][col].setIcon(null);
			}
		update();
	}

	private int askNumberOfPlayers() {

		int bPlayers;

		String[] options = { "Human Vs AI", "Human Vs Human" };

		int numPlayers = JOptionPane.showOptionDialog(this, "Select Mode.", "Game Mode", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);

		if (numPlayers == JOptionPane.YES_OPTION) {
			bPlayers = 1;
		} else
			bPlayers = 2;

		if (numPlayers == -1)
			System.exit(0);

		return bPlayers;
	}

	private Player askPlayerColor() {

		String[] options = { "White", "Black" };

		int colorChoice = JOptionPane.showOptionDialog(this, "Select Color.", "Color", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);

		if (colorChoice == JOptionPane.YES_OPTION)
			return Player.WHITE;
		else if (colorChoice == JOptionPane.NO_OPTION)
			return Player.BLACK;

		if (colorChoice == -1)
			System.exit(0);

		return null;

	}

	private void reset() {

		String[] options = { "Yes", "No" };

		int dim = JOptionPane.showOptionDialog(this, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);

		if (dim == JOptionPane.YES_OPTION) {
			model.reset(player);
			setBoard();
			AI.reset();
			if (numPlayers == 1 && player == Player.BLACK) {
				model.nextPlayer();
				computerTurn.start();
			}
		}
	}

	private void quit() {

		String[] options = { "Yes", "No" };

		int dim = JOptionPane.showOptionDialog(this, "Are you sure?", "Quit", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);

		if (dim == JOptionPane.YES_OPTION)
			System.exit(0);

	}

	private JButton createBlackButton() {

		JButton button = new JButton();
		button.addActionListener(listener);
		button.setBackground(Color.DARK_GRAY);

		return button;
	}

	private JButton createWhiteButton() {

		JButton button = new JButton();
		button.addActionListener(listener);
		button.setBackground(Color.WHITE);

		return button;
	}

	private ImageIcon createIcon(String filename) {
		ImageIcon icon = null;

		try {
			Image img = ImageIO.read(getClass().getResource(filename));
			icon = new ImageIcon(img);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return icon;
	}

	private String promote(Player player) {

		String[] options = new String[] { "Rook", "Bishop", "Knight", "Queen" };
		int response = JOptionPane.showOptionDialog(null, "Message", "Title", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0)
			return "Rook";
		if (response == 1)
			return "Bishop";
		if (response == 2)
			return "Knight";
		if (response == 3)
			return "Queen";
		else {
			JOptionPane.showMessageDialog(this, "Must select a piece", "ERROR", JOptionPane.ERROR_MESSAGE);
			promote(player);
			return null;
		}

	}

	private void performPromote() {
		String tempPiece = promote(model.currentPlayer());
		if (tempPiece == "Rook") {
			model.setBoard(toRow, toCol, new Rook(model.currentPlayer()));
			if (model.currentPlayer() == model.currentPlayer())
				board[toRow][toCol].setIcon(rookB);
			else
				board[toRow][toCol].setIcon(rookW);
		}
		if (tempPiece == "Bishop") {
			model.setBoard( toRow, toCol,new Bishop(model.currentPlayer()));
			if (model.currentPlayer() == Player.BLACK)
				board[toRow][toCol].setIcon(bishopB);
			else
				board[toRow][toCol].setIcon(bishopW);
		}
		if (tempPiece == "Knight") {
			model.setBoard( toRow, toCol,new Knight(model.currentPlayer()));
			if (model.currentPlayer() == Player.BLACK)
				board[toRow][toCol].setIcon(knightB);
			else
				board[toRow][toCol].setIcon(knightW);
		}
		if (tempPiece == "Queen") {
			model.setBoard(toRow, toCol,new Queen(model.currentPlayer()) );
			if (model.currentPlayer() == Player.BLACK)
				board[toRow][toCol].setIcon(queenB);
			else
				board[toRow][toCol].setIcon(queenW);
		}
		update();
	}

	private void checkComplete() {
		if (model.isComplete()) {
			if (model.currentPlayer() == Player.BLACK)
				JOptionPane.showMessageDialog(null, "Black has been mated. Game Over!");
			else
				JOptionPane.showMessageDialog(null, "White has been mated. Game Over!");
			model.reset(player);
			setBoard();
			update();

		}
	}

	private void kingIconRed(Player player) {

		for (int row = 0; row < 8; row++)
			for (int col = 0; col < 8; col++) {
				if (model.pieceAt(row, col) instanceof King) {
					if (model.pieceAt(row, col).player() == player) {
						board[row][col].setBackground(Color.RED);
					}
				}
			}
	}

	private void checkInCheck() {
		if (model.inCheck(Player.WHITE)) {
			kingIconRed(player.WHITE);
		}
		if (model.inCheck(Player.BLACK)) {
			kingIconRed(player.BLACK);
		}
	}

	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent evt) {

			model.move(AI.move());
			model.nextPlayer();
			update();
			computerTurn.stop();

		}
	};

	Timer computerTurn = new Timer(800, taskPerformer);

	// *****************************************************************
	// Represents a listener for button push (action) events.
	// *****************************************************************
	private class ButtonListener implements ActionListener {
		// --------------------------------------------------------------
		// Updates the counter and label when the button is pushed.
		// --------------------------------------------------------------

		private boolean flag = false;
		private Move recentMove = new Move(0, 0, 0, 0);

		public void actionPerformed(ActionEvent event) {

			JComponent comp = (JComponent) event.getSource();

			// boolean didSecondClick = false;

			if (numPlayers == 2) {
				if (flag) {
					for (int row2 = 0; row2 < BOARD_SIZE; row2++)
						for (int col2 = 0; col2 < BOARD_SIZE; col2++) {
							if (row2 == fromRow && col2 == fromCol) {
								flag = false;
								update();
								checkInCheck();
							} else if (board[row2][col2] == comp) {
								toRow = row2;
								toCol = col2;
								Move move = new Move(fromRow, fromCol, toRow, toCol);
								if (model.isValidMove(move)) {
									recentMove = move;
									model.move(move);
									flag = false;
									update();
									if (toRow == 0 || toRow == 7)
										if (model.pawnPromotion(toCol))
											performPromote();
									model.updateHasMoved(toRow, toCol);
									model.nextPlayer();
									ChessPiece.setEngine(model);
									checkComplete();
									checkInCheck();

								}
							}
						}
				}

				else if (!flag) // {
					for (int row = 0; row < BOARD_SIZE; row++)
						for (int col = 0; col < BOARD_SIZE; col++) {
							if (board[row][col] == comp) {
								if (model.pieceAt(row, col) != null)
									if (model.pieceAt(row, col).player() == model.currentPlayer()) {
										fromRow = row;
										fromCol = col;
										for (int row2 = 0; row2 < BOARD_SIZE; row2++)
											for (int col2 = 0; col2 < BOARD_SIZE; col2++) {
												Move move = new Move(fromRow, fromCol, row2, col2);

												//This has to go, because when isValid move checks if 
												//castling is valid, it returns true AND
												//executes the castle. So sadly it needs to go unless
												//you guys can think of a solution
												/*if (model.isValidMove(move)) {
													board[row2][col2].setBackground(Color.GREEN);
												}*/

											}
										board[fromRow][fromCol].setBackground(Color.CYAN);

										flag = true;

									}
							}
						}
				if (comp == undoItem) {
					model.undoLastMove(recentMove);
					update();
				}
				// AI Game
			} else {
				if (flag) {
					for (int row2 = 0; row2 < BOARD_SIZE; row2++)
						for (int col2 = 0; col2 < BOARD_SIZE; col2++) {
							if (row2 == fromRow && col2 == fromCol) {
								flag = false;
								update();
							} else if (board[row2][col2] == comp) {
								toRow = row2;
								toCol = col2;
								Move move = new Move(fromRow, fromCol, toRow, toCol);
								if (model.isValidMove(move)) {
									model.move(move);
									flag = false;
									update();
									if (toRow == 0 || toRow == 7)
										if (model.pawnPromotion(toCol))
											performPromote();
									model.updateHasMoved(toRow, toCol);
									model.nextPlayer();
									checkComplete();
									checkInCheck();
									ChessPiece.setEngine(model);
									computerTurn.start();
								}
							}
						}
				}

				else if (!flag) // {
					for (int row = 0; row < BOARD_SIZE; row++)
						for (int col = 0; col < BOARD_SIZE; col++) {
							if (board[row][col] == comp)
								if (model.pieceAt(row, col) != null)
									if (model.pieceAt(row, col).player() == player)
										if (model.pieceAt(row, col).player() == model.currentPlayer()) {
											fromRow = row;
											fromCol = col;
											for (int row2 = 0; row2 < BOARD_SIZE; row2++)
												for (int col2 = 0; col2 < BOARD_SIZE; col2++) {
													Move move = new Move(fromRow, fromCol, row2, col2);

													if (model.isValidMove(move)) {
														board[row2][col2].setBackground(Color.GREEN);
													}

												}
											board[fromRow][fromCol].setBackground(Color.CYAN);

											flag = true;

										}
						}
			}

			if (comp == newGameItem)

			{
				String[] a = new String[0];
				ChessFrame.main(a);

			}

			if (comp == restartItem)
				reset();

			if (comp == quitItem)
				quit();

		}

	}

	private void update() {
		updateSquares();
		updatePieceIcons();
	}

	private void updateSquares() {

		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (row % 2 == 1 && col % 2 == 0) {
					board[row][col].setBackground(Color.DARK_GRAY);
					add(board[row][col]);
				} else if (row % 2 == 0 && col % 2 == 1) {
					board[row][col].setBackground(Color.DARK_GRAY);
					add(board[row][col]);
				} else
					board[row][col].setBackground(Color.WHITE);
				add(board[row][col]);
			}
		}
	}

	private void updatePieceIcons() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (model.pieceAt(row, col) instanceof King) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(kingW);
					} else {
						board[row][col].setIcon(kingB);
					}
				}
				if (model.pieceAt(row, col) instanceof Queen) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(queenW);
					} else {
						board[row][col].setIcon(queenB);
					}
				}
				if (model.pieceAt(row, col) instanceof Rook) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(rookW);
					} else {
						board[row][col].setIcon(rookB);
					}
				}
				if (model.pieceAt(row, col) instanceof Bishop) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(bishopW);
					} else {
						board[row][col].setIcon(bishopB);
					}
				}
				if (model.pieceAt(row, col) instanceof Knight) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(knightW);
					} else {
						board[row][col].setIcon(knightB);
					}
				}
				if (model.pieceAt(row, col) instanceof Pawn) {
					if (model.pieceAt(row, col).player() == Player.WHITE) {
						board[row][col].setIcon(pawnW);
					} else {
						board[row][col].setIcon(pawnB);
					}
				}
				if (model.pieceAt(row, col) == null) {
					board[row][col].setIcon(null);
				}
			}
		}
	}
}