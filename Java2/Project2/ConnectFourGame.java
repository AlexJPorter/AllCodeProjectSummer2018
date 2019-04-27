package project2;

import java.util.Random;

import javax.swing.JOptionPane;

/**********************************************************************
 * Describes the methods used by a 2D connect 4 game.
 * This class also includes AI functionality for Connect 4.
 * 
 * @author Alex Porter
 * @version 2/12/2018
 *********************************************************************/

public class ConnectFourGame {

	/** Creates a 2D representation of the board */
	private int[][] board;

	/** Holds a value for the board size */
	private int size;

	/** Holds an int for the current player */
	private int player;

	/** Used to determine the next player, always 2 */
	private int playerCount;

	/** Holds a win value, in this case 4 */
	private int connections;

	/** Used to determine connections */
	private int orientation;

	/** Number of horizontal connections of a chip */
	private int horizontalConnections = 0;

	/** Number of vertical connections of a chip */
	private int verticalConnections = 0;

	/** Number of upward diagonal connections of a chip */
	private int upDiagonal = 0;

	/** Number of downward diagonal connections of a chip */
	private int downDiagonal = 0;
	
	/** Counts the number of times selectCol runs */
	private int numMoves = 0;
	
	/**  Represents the user's turn */
	public static final int USER = 0;
	
	/** Represents the computer's turn */
	public static final int COMPUTER = 1;

	/******************************************************************
	 * Creates a 2D board of size pSize, sets playercount to 2,
	 * sets connections to 4, and resets the board
	 * 
	 * @param pSize The given board size from the user
	 *****************************************************************/
	public ConnectFourGame (int pSize) {
		size = pSize;
		board = new int[pSize][pSize];
		this.playerCount = 2;
		this.connections = 4;
		this.player = USER;
		reset();
	}
	/******************************************************************
	 * Returns horizontalConnections
	 * 
	 * @return int horizontalConnections Horizontal connections value
	 *****************************************************************/
	public int getHorizontalConnections() {
		return horizontalConnections;
	}
	/******************************************************************
	 * Returns verticalConnections
	 * 
	 * @return int verticalConnections Vertical connections value
	 *****************************************************************/
	public int getVerticalConnections() {
		return verticalConnections;
	}
	/******************************************************************
	 * Returns upDiaganol
	 * 
	 * @return int upDiagnol upDiagonal connections value
	 *****************************************************************/
	public int getUpDiagnol() {
		return upDiagonal;
	}
	/******************************************************************
	 * Returns downDiaganol
	 * 
	 * @return int downDiagnol downDiagonal connections value
	 *****************************************************************/
	public int getDownDiagnol() {
		return downDiagonal;
	}

	/******************************************************************
	 * Sets all connections to 0, and resets the board to -1.
	 * Also sets player and numMoves back to 0
	 *****************************************************************/
	public void reset() {
		horizontalConnections = 0;
		verticalConnections = 0;
		upDiagonal = 0;
		downDiagonal = 0;
		player = USER;
		numMoves = 0;
		
		//resets all board values to -1
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				board[row][col] = -1;
	}
	/******************************************************************
	 * Sets the lowest available row to current player in the given 
	 * column. If now row is available, it returns -1
	 * 
	 * @param int col Given column position
	 * @return int row First available row
	 *****************************************************************/
	public int selectCol (int col) {
		numMoves += 1;
		for (int row = size - 1; row >= 0; row--) 
			if (board[row][col] == -1) {
				board[row][col] = player;
				return row;
			}
		return -1;
	}

	/******************************************************************
	 * Toggle switches the current player. Also returns the new current
	 * player.
	 * 
	 * @return int player The new current player
	 ******************************************************************/
	public int nextPlayer() {
		player = (player + 1) % playerCount;

		return player;
	}

	/******************************************************************
	 * Returns current player
	 * 
	 * @return int player Current player
	 *****************************************************************/
	public int getCurrentPlayer () {
		return player;
	}
	/******************************************************************
	 * Returns number of moves that have been made. Used to calculate
	 * the first mover of a new game.
	 * 
	 * @return int numMoves The number of times selectCol has run 
	 *****************************************************************/
	public int getNumMoves() {
		return numMoves;
	}
	
	/******************************************************************
	 * Finds the amount of connections for all chips. If any of the
	 * connections equal the connections win value, returns true.
	 * If none of the connections equal 4, returns false.
	 * 
	 * @return boolean True or false depending if the board has a win
	 *****************************************************************/
	public boolean isWinner() {
		for(int row = size - 1; row >=0; row --) 
			for(int col = size - 1; col >=0; col --) {
				if(board[row][col] != -1) {
					findConnections(row, col);
					if(verticalConnections == connections || 
							horizontalConnections == connections || 
							upDiagonal == connections || downDiagonal 
							== connections) 
						return true;
				}
			}
		return false;
	}

	/******************************************************************
	 * This method houses the AI. It returns a column choice, just
	 * like a player would. The AI tries to win if it can, then tries
	 * to block an opponent from winning. Then it does a variety of 
	 * operations, calculating them in order of importance. See inner
	 * inline comments for explanation.
	 *  
	 * @return int col The computer selection choice
	 *****************************************************************/
	public int connectAI() {
		//create a value that represents what NOT to return
		int doNotReturn = -10;
		
		//define a row and column variable
		int row = 0;
		int col = 0;
		
		//checks if it can win, if it can, it moves there
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			
			//if row is full, keep checking
			if(row == -1)
				continue;
			
			//temporarily set the value to 1
			board[row][col] = COMPUTER;
			
			//check for win
			if(isWinner()) {
				
				//reset the value
				board[row][col] = -1;
				
				//reset the doNotReturn
				doNotReturn = -10;
				
				//return the column choice
				return col;
			}
			else {
				//reset the value and continue
				board[row][col] = -1;
				continue;
			}
		}

		//uses the same method as above, but temporarily
		//assigns the position to the opponent instead
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) 
				continue;
			board[row][col] = USER;
			if(isWinner()) {
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else {
				board[row][col] = -1;
				continue;
			}
		}

		//looks two moves in advance to see if there is a move it 
		//shouldn't play. If there is, it updates doNotReturn
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) 
				continue;
			
			//temp cast first layer to itself
			board[row][col] = COMPUTER;
			int row2 = selectCol(col);
			if(row2 != -1) {
				
				//cast second layer to opponent
				board[row2][col] = USER;
				
				//checks if winner
				if(isWinner()) {
					
					//reset casts, set doNotReturn to column
					board[row][col] = -1;
					board[row2][col] = -1;
					doNotReturn =  col;
				}
				else {
					//reset casts and continue
					board[row][col] = -1;
					board[row2][col] = -1;
					continue;
				}
			}
			else {
				board[row][col] = -1;
				continue;
			}
		}

		//attempts to captalize on a trap, that is, a point where a win
		//is inevitable after the move.
		for(row = size - 1; row >= 0; row --) 
			for(col = 0; col < size; col ++) {
				if(board[row][col] == COMPUTER) {
					findConnections(row, col);
					
					//if row is full, continue
					if(findTraps(row, col) != -1 && 
							board[0][findTraps(row, col)] != -1) 
						continue;
					
					//if trap, reset doNotReturn and move there
					if(findTraps(row, col) != -1 && findTraps(row, col) 
							!= doNotReturn) {
						doNotReturn = -10;
						return findTraps(row, col);
					}
					//if no trap, then continue
					else
						continue;
				}
			}
		//tries to block a player from capitalizing on a trap
		for(row = size - 1; row >= 0; row --) 
			for(col = 0; col < size; col ++) {
				
				//the following line is the only difference from above
				if(board[row][col] == USER) {
					findConnections(row, col);
					if(findTraps(row, col) != -1 && 
							board[0][findTraps(row, col)] != -1) 
						continue;		
					if(findTraps(row, col) != -1 && findTraps(row, col)
							!= doNotReturn) {
						doNotReturn = -10;
						return findTraps(row, col);
					}
					else
						continue;
				}
			}
		//attempts to create a new potential trap
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			//if row is full, continue
			if(row == -1) {
				continue;
			}
			//temp cast to one and see if there is a trap
			board[row][col] = COMPUTER;
			findConnections(row, col);
			//if there is a trap 
			//and it's not giving the win to the other player
			if(findTraps(row, col) != -1 && findTraps(row, col) 
					!= doNotReturn) {
				//reset cast, reset doNotReturn, return trap column
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else {
				//reset cast and continue
				board[row][col] = -1;
				continue;
			}
		}
		//tries to block the player from creating their own trap
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) {
				continue;
			}
			//the following line is the only difference from above
			board[row][col] = USER;
			findConnections(row, col);
			if(findTraps(row, col) != -1 && findTraps(row, col) != 
					doNotReturn) {
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else{
				board[row][col] = -1;
				continue;
			}
		}

		//tries to create a string of three chips
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			
			//if row is full, continue, you get the idea
			if(row == -1) 
				continue;
			board[row][col] = COMPUTER;
			findConnections(row, col);
			
			//if any connections are three and not it's doNotReturn,
			if((horizontalConnections == 3 || verticalConnections == 3
					|| upDiagonal == 3 || downDiagonal == 3) && 
					col != doNotReturn) {
				
				//reset casts, doNotReturn, return choice
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else{
				//reset cast and continue
				board[row][col] = -1;
				continue;
			}
		}
		
		//attempt to block the player from creating their own three
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) 
				continue;
			
			//the following line is the only difference from above
			board[row][col] = USER;
			
			findConnections(row, col);
			if((horizontalConnections == 3 || verticalConnections == 3 
					|| upDiagonal == 3 || downDiagonal == 3) && 
					col != doNotReturn) {
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else{
				board[row][col] = -1;
				continue;
			}
		}
		
		//attempt to create a line of 2 connected chips
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) 
				continue;
			board[row][col] = COMPUTER;
			findConnections(row, col);
			if((horizontalConnections == 2 || verticalConnections == 2 
					|| upDiagonal == 2 || downDiagonal == 2) &&
					col != doNotReturn) {
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}
			else{
				board[row][col] = -1;
				continue;
			}
		}
		
		//block the opponent from creating a line of two connect chips
		for(col = 0; col < size; col ++) {
			row = selectCol(col);
			if(row == -1) 
				continue;
			
			//the following line is different
			board[row][col] = USER;
			
			findConnections(row, col);
			if ((horizontalConnections == 2 || verticalConnections == 2 ||
			     upDiagonal == 2 			|| downDiagonal == 2) && 
					col != doNotReturn) {
				board[row][col] = -1;
				doNotReturn = -10;
				return col;
			}

			else{
				board[row][col] = -1;
				continue;
			}
		}
		//if none of the above run, then play in the middle
		//also checks if the board is full in that column
		if(board[0][size/2] == -1 && (size/2) != doNotReturn) {
			
			//reset doNotReturn, return size
			doNotReturn = -10;
			return size/2;
		}

		//if all that fails, go through each column and see if it's
		//empty enough to place a chip
		for(col = 0; col <= size - 1; col ++) {
			if(board[0][col] != -1) {
				return col;	
			}
		}
		
		//if all fails, return -1
		return -1;
	}
	/******************************************************************
	 * Checks if the entire board is full, returns true if it is.
	 * Returns false if it is not.
	 * 
	 * @return boolean True if the board is full, else false
	 *****************************************************************/
	public boolean checkCat() {
		int col = size - 1;
		int counter = 1;
		for(col = size - 1; col > 0; col --) {
			if(board[0][col] != -1) {
				counter += 1;			
				if(counter == size) 
					return true;
			}
		}
		//else return false
		return false;
	}

	/******************************************************************
	 * Checks if there are any columns that a trap could occur. This
	 * includes horizontal, vertical, upDiagnol, and downDiagnol.
	 * If there is, it returns the column, if not, it returns -1
	 * 
	 * @param int row Given row position
	 * @param int col Given column position
	 * @return int Place to move to create a trap
	 *****************************************************************/
	private int findTraps(int row, int col) {
		//find a horizontal trap
		findConnections(row, col);
		// for all if statements, 
		//checks if row and col are valid, and if a trap is possible
		//if it is, it returns the appropriate column
		if (horizontalConnections == 2 && col > 0 && col < size - 2 &&
				board[row][col +1] == -1 &&
				board[row][col - 1] == -1) {
			if((row < size - 1 && board[row + 1][col - 1] != -1 &&
					board[row + 1][col +1] != -1) || row == size -1)
				return col + 2;
		}
		//block an up diagnol trap
		else if(upDiagonal == 2 && row > 1 && row < size - 1 && col > 0
				&& col < size - 2 && board[row - 2][col + 2] == -1 &&
				board[row + 1][col - 1] == -1 && 
				board[row - 1][col + 2] != -1) {
			if((row < size - 2 && board[row + 2][col -1] != -1) || 
					row == size - 2)
				return col -1;
		}

		//block a down diagnol trap
		else if(downDiagonal == 2 && row > 0 && row < size - 2 && 
				col > 0 && col < size - 2 && 
				board[row - 1][col - 1] == -1 && 
				board[row + 2][col + 2] == -1 && 
				board[row][col - 1] != -1) {
			if((row < size - 3 && board[row + 3][col + 2] != -1) 
					|| row == size - 3)
				return col -1;
		}
		//if no traps, returns -1
		return -1;
	}

	/******************************************************************
	 * Finds the horizontal, vertical, and diagnol connections.
	 * each chip has. This is useful for the AI and winner methods.
	 * It is also used once in the panel class for makeItGold().
	 * 
	 * @param int row Given row position
	 * @param int col Given column position
	 *****************************************************************/
	public void findConnections(int row, int col) {
		//resets all connections
		verticalConnections = 0;
		horizontalConnections = 0;
		upDiagonal = 0;
		downDiagonal = 0;
		
		//runs if the board is not -1
		if(board[row][col] != -1) {
			int sim = board[row][col];
			
			//determines an orientation value
			orientation = -1;
			
			//if there is a horizontal connection, then set
			//orientation to 0 and use the private method to find
			//total number of connections
			if(col < size - 1 && board[row][col + 1] == sim) { 
				orientation = 0;
				
				//update horizontalConnections to calculated values
				horizontalConnections = 
						findConnectionsWithOrientation(row, col, 
								orientation);
			}
			//same, but now verticalConnections
			if(row > 0 && board[row - 1][col] == sim) {
				orientation = 1;
				verticalConnections = 
						findConnectionsWithOrientation(row, col, 
								orientation);
			}
			//same, but now with upDiagnol
			if(row > 0 && col < size - 1 && board[row - 1][col + 1]
					== sim) {
				orientation = 2;
				upDiagonal = 
					findConnectionsWithOrientation(row, col, 
							orientation);
			}
			//same, but now with downDiagnol
			if(row < size - 1 && col < size - 1 && 
					board[row + 1][col + 1] == sim) {
				orientation = 3;
				downDiagonal = 
					findConnectionsWithOrientation(row, col, 
							orientation);
			}
		}
	}
	/******************************************************************
	 * Takes a row, column, and orientation to determine how many 
	 * connections a chip has. 0 relates to horizontal connections,
	 * 1 to vertical, 2 to upDiagnol, and 3 to downDiagnol. The loop
	 * runs until a opponent chip is found or the index goes 
	 * out of bounds. Invalid orientation returns 0.
	 * 
	 * @param int row Row position of the chip
	 * @param int column Column position of the chip
	 * @param int orient Direction the connection is looking
	 * @return int the number of connections in a given direction
	 *****************************************************************/
	private int findConnectionsWithOrientation(int row, int column, 
			int orient) {
		//sets a value for the initial board position
		int sim = board[row][column];
		
		//intializes a variable to return
		int connection = 0;
		switch (orient) {
		
		//runs if horizontal
		case 0:			
			try {
				for(int i = 0; i < size; i++) {
					//keeps checking until sim is unequal or it
					//goes out of bounds. 
					if(board[row][column + i] == sim)
						connection += 1;
					else
						return connection;
				}
			}
			//if it goes out of bounds, it returns the current value
			catch(Exception e) {
				return connection;
			}
		//runs if vertical is given
		case 1:
			try {
				for(int i = 0; i < size; i++) {
					if(board[row - i][column] == sim)
						connection += 1;
					else
						return connection;
				}

			}
			catch(Exception e) {
				return connection;
			}
		//runs if upDiagnol is given
		case 2:
			try {
				for(int i = 0; i < size; i++) {
					if(board[row - i][column + i] == sim)
						connection += 1;
					else
						return connection;
				}
			}
			catch(Exception e) {
				return connection;
			}
		//runs if downDiagnol is given
		case 3:
			try {
				for(int i = 0; i < size; i++) {
					if(board[row + i][column + i] == sim)
						connection += 1;
					else
						return connection;
				}
			}
			catch(Exception e) {
				return connection;
			}
			//returns 0 if orient is not 0-3
		default:
			return 0;
		}
	}
}
