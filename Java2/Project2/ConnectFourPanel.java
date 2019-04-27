package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**********************************************************************
 * This panel creates a GUI for the user to play Connect 4.
 * Whether against an AI, against another human, or a 3D game.
 * This panel is added to a master JFrame class later.
 * 
 * @author Alex Porter
 * @version 2/12/2018
 *********************************************************************/

public class ConnectFourPanel extends JPanel {

	/** Holds a value for the size of the board, default is 10 */
	private int boardSize = 10;

	/** Creates a 2D matrix for select buttons and board display */
	private JLabel[][] matrix;

	/** Creates a line of buttons that denote the column selection */
	private JButton[] selection;

	/** Creates a rectangle of buttons that show column and depth */
	private JButton[][] threeSelection;

	/** Creates a menu item to start a new game */
	private JMenuItem gameItem;

	/** Creates a menu item to exit the game */
	private JMenuItem quitItem;

	/** Holds an image that represents an empty spot on the board */
	private ImageIcon iconBlank;

	/** Holds an image of a red chip for player 1 */
	private ImageIcon iconPlayer1;

	/** Holds an image of a blue chip for player 2 */
	private ImageIcon iconPlayer0;

	/** Holds an image of a gold chip to highlight a win */
	private ImageIcon iconWin;

	/** Holds an image the welcomes the user to the game */
	private ImageIcon iconWelcome;

	/** Holds a string to determine the game type */
	private String input;

	/** Creates an engine object for a 2D game */
	private ConnectFourGame game;

	/** Creates an engine object for a 3D game */
	private ConnectFour3DGame threeGame;

	/******************************************************************
	 * Initializes the game depending on the style chosen. Also shows
	 * a welcome screen and prompts for board size and style. Creates
	 * the button arrays and generally prepares the game
	 * 
	 * @param JMenuItem pquitItem The quit game menu item
	 * @param JMenuItem pgameItem The neww game menu item
	 *****************************************************************/
	public ConnectFourPanel(JMenuItem pquitItem, JMenuItem pgameItem){
		
		//set the welcome screen icon to an image
		iconWelcome = new ImageIcon("welcomeScreen.png");
		
		//display the welcome screen
		JOptionPane.showMessageDialog(null, iconWelcome);
		
		//explicitly initialize sizeInput to null
		String sizeInput = null;

		//uses a do while to prompt for game style and sizeinput
		do {
			
			//prompts for the game style
			if(input == null) {
				input = JOptionPane.showInputDialog(
						"'1' for normal, '2' for AI, and '3' for 3D "
								+ "('Cancel' to quit)");
			}
			
			//if cancel is clicked, quit the game
			if(input == null)
				System.exit(0);
			
			//if input is invalid, re-prompt
			if(!(input.equals("1") || input.equals("2") 
					|| input.equals("3"))) {
				input = null;
				JOptionPane.showMessageDialog(null,
						"Invalid game style. Enter 1, 2, or 3");
				continue;
			}
			
			//prompt for the size of the board
			sizeInput = JOptionPane.showInputDialog(
					"Enter the size of your board: "
					+ "('Cancel' to re-choose style)");
			
			//if cancel is hit, go back a step
			if(sizeInput == null) {
				input = null;
				continue;
			}

			//try to get a board size from the user
			try {
				//if this fails, board size is re-prompted
				boardSize = Integer.parseInt(sizeInput);
				
				//checks if boardsize is too large or too small
				if(boardSize > 30 || boardSize < 3) {
					JOptionPane.showMessageDialog(null, 
						"Invalid size. Enter a value from 3 to 30");
					
					//re prompt for size if it is
					sizeInput = null;
				}
				
				//check if 3D and board is too large
				else if(boardSize > 7 && input.equals("3")) {
					JOptionPane.showMessageDialog(null, 
						"Invalid size. Enter a value from 3 to 7");
					
					sizeInput = null;
				}
			}
			//runs if boardsize is invalid
			catch(Exception e) {
				
				//alert the user
				JOptionPane.showMessageDialog(null, 
						"Invalid size. Enter an Int from 3 to 30");
				
				//go back to re enter the size
				sizeInput = null;
			}
		} while(sizeInput == null);

		//if an invalid input is entered, style is set to normal
		if(!(input.equals("1") || input.equals("2") || 
				input.equals("3")))
			input = "1";


		//create a new 2D game engine with the given board size
		game = new ConnectFourGame(boardSize);

		//create a new 3D game engine with the given board size
		threeGame = new ConnectFour3DGame(boardSize);

		//create the new game item
		gameItem = pgameItem;

		//create the quit game item
		quitItem = pquitItem;

		//create a list of buttons the same amount as the board
		selection = new JButton[boardSize];

		//create a layout, with room for the top row of buttons
		setLayout(new GridLayout(boardSize+1,boardSize));

		//initialize the images
		iconBlank = new ImageIcon ("blank.png");
		iconPlayer1 = new ImageIcon ("player1.png");
		iconPlayer0 = new ImageIcon ("player2.png");
		iconWin = new ImageIcon ("winChips.png");

		//add the button listeners
		ButtonListener listener = new ButtonListener();
		quitItem.addActionListener(listener);
		gameItem.addActionListener(listener);

		//if a 2D game was selected, create the row of buttons
		if(input.equals("1") || input.equals("2")) {

			for (int col = 0; col < selection.length; col++) {
				selection[col] = new JButton ("Select");
				selection[col].addActionListener(listener);
				add(selection[col]);
			}

			//Create an image matrix to represent the board
			matrix = new JLabel[selection.length][selection.length];

			//Set all images in the board intially to blank
			for (int row = 0; row < selection.length; row++) 
				for (int col = 0; col < selection.length; col++) {
					matrix[row][col] = new JLabel("",iconBlank,
							SwingConstants.CENTER);
					add(matrix[row][col]);					
				}
		}
		//if 3D is selected, create a rectangle of buttons
		//add the listeners to those buttons as well
		else if(input.equals("3") ) {
			threeSelection = new JButton[boardSize][boardSize];
			for(int col = 0; col < selection.length; col ++ ) 
				for(int dep = 0; dep <selection.length; dep ++) {
					threeSelection[col][dep] = new JButton ("");
					threeSelection[col][dep].
					addActionListener(listener);
					add(threeSelection[col][dep]);
				}
		}
	}



	/******************************************************************
	 * Used as a actionListener method that triggers when buttons are
	 * pushed. Or menu items like quit and new game.
	 *  
	 * @author Alex Porter
	 * @version 2/12/2018
	 *****************************************************************/
	private class ButtonListener implements ActionListener
	{
		/**************************************************************
		 * Takes an ActionEvent and interprets the appropriate action
		 * if a button or menu item is pressed
		 * 
		 * @param ActionEvent event The button or menu item pressed
		 *************************************************************/
		public void actionPerformed (ActionEvent event)
		{
			//casts the event to a button or menu item
			JComponent comp = (JComponent) event.getSource();

			//fail safe if the first player to move is somehow 1 not 0
			if(game.getNumMoves() == 0 && 
					game.getCurrentPlayer() == game.COMPUTER)
				game.nextPlayer();

			//runs if a 3 game is not selected
			if(!(input.equals("3"))) {
				normalBoardMove(comp);
			}
			//if a 3D board has been chosen, this runs
			else {
				//initialize a value for row

				threeDMove(event);
			}
			//checks if new game menu item was selected
			if (comp == gameItem) { 
				//if not 3D
				if(!(input.equals("3"))) {
					//reset game, reset board image
					game.reset();

					for (int row = 1; row < selection.length; row++) 
						for(int col = 0; col < selection.length; col++) 
							matrix[row][col].setIcon( iconBlank);

				}

				else {
					//reset game, reset button text
					threeGame.reset();
					
					for (int col = 0; col< selection.length; col++) 
						for(int dep = 0; dep < selection.length; dep++)
							threeSelection[col][dep].setText("");
				}
				ConnectFour newGame = new ConnectFour();
			}

			//if quit, then exit the program
			if (comp == quitItem)
				System.exit(0);
		}

	}
	/******************************************************************
	 * This private helper method highlights the winning pieces for a 
	 * 2D game.
	 * 
	 *****************************************************************/
	private void makeItGold() {
		int row;
		int col;
		for (row = 0; row < selection.length; row++) {
			for (col = 0; col < selection.length; col++) {
				
				//finds the connections for each chip
				game.findConnections(row, col);
				
				//if horizontal, sets the chips to gold
				if(game.getHorizontalConnections() == 4) {
					matrix[row][col].setIcon( iconWin);
					matrix[row][col + 1].setIcon(iconWin);
					matrix[row][col + 2].setIcon(iconWin);
					matrix[row][col + 3].setIcon(iconWin);
				}
				//sets vertical chips to gold
				else if(game.getVerticalConnections() == 4) {
					matrix[row][col].setIcon( iconWin);
					matrix[row - 1][col].setIcon(iconWin);
					matrix[row - 2][col].setIcon(iconWin);
					matrix[row - 3][col].setIcon(iconWin);

				}
				//sets downDiagnol to gold
				else if(game.getDownDiagnol() == 4) {
					matrix[row][col].setIcon( iconWin);
					matrix[row + 1][col + 1].setIcon(iconWin);
					matrix[row + 2][col + 2].setIcon(iconWin);
					matrix[row + 3][col + 3].setIcon(iconWin);

				}
				//sets up diagnol to gold
				else if(game.getUpDiagnol() == 4) {
					matrix[row][col].setIcon( iconWin);
					matrix[row - 1][col + 1].setIcon(iconWin);
					matrix[row - 2][col + 2].setIcon(iconWin);
					matrix[row - 3][col + 3].setIcon(iconWin);

				}
			}
		}
	}
	private void normalBoardMove(JComponent comp) {
		//checks which button was pressed
		for (int col = 0; col < boardSize; col++) {
			if (selection[col] == comp) {
				
				//selects that column with the same button
				int row = game.selectCol(col);
				
				//if column is full, alert the player
				if (row == -1) {
					if(game.checkCat()) {
						JOptionPane.showMessageDialog(null,"Tie game! "
				+ "A cat game has been reached. (Resetting game...)");

						//if it is, reset the game
						panelReset();
					}
					else {
						JOptionPane.showMessageDialog(null,
								"Column is full!");
					}
				}
				else {
					//change the icon from blank to the
					//current player if the row is not full
					matrix[row][col].setIcon((
							game.getCurrentPlayer() 
							== game.COMPUTER) ? iconPlayer1 : iconPlayer0);

					//check if there is a winner
					if (game.isWinner()) {
						//highlight the winning pieces
						makeItGold();

						//determine the winning player icon
						ImageIcon winner = new ImageIcon();
						if(game.getCurrentPlayer() == game.COMPUTER) 
							winner = iconPlayer1;
						else 
							winner = iconPlayer0;

						//display the winner to the screen
						//plus the winning image icon
						JOptionPane.showMessageDialog(null,
								"Player " + 
									game.getCurrentPlayer() + 
									" Wins!", "Winner!", 
									JOptionPane.INFORMATION_MESSAGE, 
									winner);

						//reset the game to stop infinite wins
						panelReset();
					}
					//pass the turn to the next player
					//if the game reset after the player won, cycle the
					//next player method to bring it back to player 0
					if(game.getNumMoves() == 0 && 
							game.getCurrentPlayer() == game.USER)
						game.nextPlayer();
					game.nextPlayer();
				}

				//checks if it's the computer's turn and 
				//the AI game style has been chosen
				if(game.getCurrentPlayer() == game.COMPUTER
						&& input.equals("2")) {

					//determines the row the AI wants to select
					int compSelect = game.connectAI();

					//selects that row
					int compRow = game.selectCol(compSelect);

					//show message that slows the computer down
					//temporarily removed for demo
					/*JOptionPane.showMessageDialog(null, 		
							"I AM THINKING...");*/

					//if the computer can't select a row, a cat
					//game is decided
					if(compSelect == -1) {
						JOptionPane.showMessageDialog(null, 		
								"Tie game! A cat game has been reached. "
								+ "(Resetting game...");
						panelReset();
					}
					else {
						//set the position to the correct icon
						matrix[compRow][compSelect]
								.setIcon(iconPlayer1);

						//check if there is a winner, same as above
						if(game.isWinner()) {
							makeItGold();

							ImageIcon winner = new ImageIcon();
							if(game.getCurrentPlayer() == game.COMPUTER) 
								winner = iconPlayer1;							
							else 
								winner = iconPlayer0;
							JOptionPane.showMessageDialog(null,
									"Player " + 
									game.getCurrentPlayer() + " Wins!",
									"Winner!", 
									JOptionPane.INFORMATION_MESSAGE, 
									winner);
							panelReset();
						}
						game.nextPlayer();
					}
				}
			}
		}
	}
	public void threeDMove(ActionEvent event) {
		//check if there is a tie, if there is, end the game
		if(threeGame.tieGame()) {
			
			//alert the player of a tie
			JOptionPane.showMessageDialog(null, 
					"Tie game! A cat game has been reached."
							+ " (Resetting game...)");
			//reset the game
			threeGame.reset();

			//reset the buttons to empty text
			for (int col = 0; col< selection.length; col++) 
				for(int dep = 0; dep < selection.length; dep++)
					threeSelection[col][dep].setText("");
		}
		//if there isn't a tie, then do a normal move
		else {

			//initialize a value for row
			int row = 0;
			
			//find which button had been pressed
			for(int col = 0; col < boardSize; col ++) 
				for(int dep = 0; dep < boardSize; dep ++) {
					if(threeSelection[col][dep] == 
							event.getSource()) {

						//select the row given that button position
						row = threeGame.selectColDep(col, dep);

						//alert the player if the column is full
						if(row == -1) 
							JOptionPane.showMessageDialog(null, 
									"Column is full!");
						//create a string to set to the button
						else {
							String toSet = "";
							
							//for each row, if it's empty,
							//add that int to the button
							for(row = boardSize - 1; row >= 0; 
									row --) 
								if(threeGame.getValue(row, col, 
										dep) != -1) {
									toSet += (threeGame.getValue(
											row, col, dep));
									
							//if it's not the last row, add a comma
									if(row > 0 && threeGame.
											getValue(row - 1, 
													col, dep) != -1)
										toSet += ",";
									
									//set the text to toSet
									threeSelection[col][dep]
											.setText(toSet);		
								}
						}
					}
				}
			//check if there is a winner
			if(threeGame.isWinner()) {
				//alert the player
				JOptionPane.showMessageDialog(null, "Player " + 
						threeGame.getCurrentPlayer() + " Wins!");

				threeGame.reset();

				//reset the buttons to empty text
				for (int col = 0; col < selection.length; col++) 
					for(int dep = 0; dep < selection.length; dep++)
						threeSelection[col][dep].setText("");
			}
			threeGame.nextPlayer();
		}
	}
	private void panelReset() {
		game.reset();
		int row;
		int col;
		
		//set all icons to blank 
		for (row = 0; row < 
				selection.length; row++) 
			for (col = 0; col < 
					selection.length; col++) 
				matrix[row][col].
				setIcon( iconBlank);
	}
}