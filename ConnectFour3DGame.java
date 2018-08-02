package project2;

/**********************************************************************
 * This class describes the methods used by the ConnectFourPanel class
 * that operate a 3 dimensional Connect 4 game.
 * 
 * @author Alex Porter
 * @version 2/12/2018
 **********************************************************************/

public class ConnectFour3DGame {

	/** Holds a value for the size of the board */
	private int size;
	
	/** Holds either 0 or 1, which defines the current player */
	private int player;
	
	/** Helps determine the next player */
	private int playerCount;
	
	/** Initializes a board that holds player values */
	private int board[][][];
	
	/******************************************************************
	 * Takes size and creates a 3D cube with those dimensions. Initial
	 * player is set to 0, playerCount is set to two and the board is 
	 * cleared to begin the game.
	 * 
	 * @param pSize The given size of the board
	 *****************************************************************/
	public ConnectFour3DGame(int pSize) {
		size = pSize;
		board = new int[pSize][pSize][pSize];
		player = 0;
		playerCount = 2;
		reset();
	}
	
	/******************************************************************
	 * Clears the board of all player values by setting them to -1
	 *****************************************************************/
	public void reset() {
		//reset player to 0
		player = 0;
		
		for(int row = size - 1; row >= 0; row --)
			for(int col = 0; col < size; col ++)
				for(int dep = 0; dep < size; dep ++)
					//goes through all board positions and sets to -1
					board[row][col][dep] = -1;
	}
	
	/******************************************************************
	 * Checks all possible combinations of 4 in a row on a three D
	 * board. Such as vertical, horizontal, and 3D diagnols. If a 
	 * combination would be considered a win, true is returned. Else 
	 * false is returned. 
	 * 
	 * @return boolean True or false depending on there being a winner
	 *****************************************************************/
	public boolean isWinner() {
		//initialize row, column, and depth
		int row = 0;
		int col = 0;
		int dep = 0;
		
		//Checks for a vertical four in a row
		//row is restriced from the full size to >=3 to avoid
		//an out of bounds exception
		for(row = size - 1; row >= 3; row --)
			for(col = 0; col < size; col ++)
				for(dep = 0; dep < size; dep ++) {
					//a variable check is made to save lines
					int check = board[row][col][dep];
					//checks if the value is a player, and it
					//qualifies as a vertical win. If it does,
					//the for loop returns true.
					if (check != -1
						 && check == board[row - 1][col][dep] &&
							check == board[row - 2][col][dep] &&
							check == board[row - 3][col][dep] )
						return true;
				}

		//check for horizontal four in a row
		//col is restriced and increasing (+)
		for (dep = 0; dep < size; dep ++)
			for(row = size - 1; row >= 0; row --)
				for(col = 0; col < size - 3; col ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row][col + 1][dep]
							&& check == board[row][col + 2][dep] 
							&& check == board[row][col + 3][dep])
						return true;

				}
		//check for x,y upDiagnol four in a row on each depth
		//row is restricted and decreasing (-)
		//col is restricted and increasing (+)
		for (dep = 0; dep < size; dep ++)
			for(row = size - 1; row >= 3; row --)
				for(col = 0; col < size - 3; col ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row - 1][col + 1][dep]
							&& check == board[row - 2][col + 2][dep]
							&& check == board[row - 3][col + 3][dep])
						return true;
				}
		//check for x,y downDiagnol four in a row on each depth
		//row is restricted and increasing (+)
		//col is restricted and increasing (+)
		for (dep = 0; dep < size; dep ++)
			for(row = 0; row < size - 3; row ++)
				for(col = 0; col < size - 3; col ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row + 1][col + 1][dep]
							&& check == board[row + 2][col + 2][dep]
							&& check == board[row + 3][col + 3][dep])
						return true;
				}
		//check for x,z horizontal four in a row on each column
		//dep is restricted and increasing (+)
		for(col = 0; col < size; col ++)
			for(row = size - 1; row >= 0; row --)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row][col][dep + 1]
							&& check == board[row][col][dep + 2]
							&& check == board[row][col][dep + 3])
						return true;
				}
		//check for x,z upDiagnol four in a row on ecah column
		//row is restricted and decreasing (-)
		//dep is restricted and increasing (+)
		for(col = 0; col < size; col ++)
			for(row = size - 1; row >= 3; row --)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row - 1][col][dep + 1]
							&& check == board[row - 2][col][dep + 2]
							&& check == board[row - 3][col][dep + 3])
						return true;
				}
		//check for x,z downDiagnol four in a row
		// dep +, row +
		for(col = 0; col < size; col ++)
			for(row = 0; row > size - 3; row ++)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
							&& check == board[row + 1][col][dep + 1]
							&& check == board[row + 2][col][dep + 2]
							&& check == board[row + 3][col][dep + 3])
						return true;

				}
		//check for x,y,z up diagnol to the top right
		//row -, col +, dep +
		for(row = size - 1; row >= 3; row --)
			for(col = 0; col < size - 3; col ++)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row - 1][col + 1][dep + 1]
						&& check == board[row - 2][col + 2][dep + 2]
						&& check == board[row - 3][col + 3][dep + 3])
					return true;
				}
		//check for x,y,z up diagnol to the top left
		//row -, col -, dep +
		for(row = size - 1; row >= 3; row --)
			for(col = size -1; col >= 3; col --)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row - 1][col - 1][dep + 1]
						&& check == board[row - 2][col - 2][dep + 2]
						&& check == board[row - 3][col - 3][dep + 3])
					return true;
				}
		//check for x,y,z up diagnol to the down left
		//row -, col -, dep -
		for(row = size - 1; row >= 3; row --)
			for(col = size -1; col >= 3; col --)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row - 1][col - 1][dep - 1]
						&& check == board[row - 2][col - 2][dep - 2]
						&& check == board[row - 3][col - 3][dep - 3])
					return true;
				}
		//check for x,y,z up diagnol to the down right
		//row -, col +, dep -
		for(row = size - 1; row >= 3; row --)
			for(col = 0; col < size - 3; col ++)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row - 1][col + 1][dep - 1]
						&& check == board[row - 2][col + 2][dep - 2]
						&& check == board[row - 3][col + 3][dep - 3])
					return true;
				}
		//Same as above, but now row increases rather than decreasing
		//check for down diagnol from top left to center
		//row +, col +, dep +
		for(row = 0; row > size - 3; row ++)
			for(col = 0; col < size - 3; col ++)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1
						&& check == board[row + 1][col + 1][dep + 1]
						&& check == board[row + 2][col + 2][dep + 2]
						&& check == board[row + 3][col + 3][dep + 3])
					return true;
				}
		//check for down diagnol from top right to center
		//row + , col -, dep +
		for(row = 0; row > size - 3; row ++)
			for(col = size -1; col >= 3; col --)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row + 1][col - 1][dep + 1]
						&& check == board[row + 2][col - 2][dep + 2]
						&& check == board[row + 3][col - 3][dep + 3])
					return true;
				}
		//check for down diagnol from bottom left to center
		//row +, col -, dep -
		for(row = 0; row > size - 3; row ++)
			for(col = size -1; col >= 3; col --)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row + 1][col - 1][dep - 1]
						&& check == board[row + 2][col - 2][dep - 2]
						&& check == board[row + 3][col - 3][dep - 3])
					return true;
				}
		//check for down diagnol from bottom right to center
		//row +, col +, dep -
		for(row = 0; row > size - 3; row ++)
			for(col = 0; col < size - 3; col ++)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row + 1][col + 1][dep - 1]
						&& check == board[row + 2][col + 2][dep - 2]
						&& check == board[row + 3][col + 3][dep - 3])
					return true;
				}
		
		//check for diagonals where row stays the same, but x,z change
		//depth up diagnol, col +, dep +
		for(row = size - 1; row >= 0 ; row --)
			for(col = 0; col < size - 3; col ++)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row][col + 1][dep + 1]
						&& check == board[row][col + 2][dep + 2]
						&& check == board[row][col + 3][dep + 3])
					return true;
				}
		
		//depth down diagnol, col +, dep -
		for(row = size - 1; row >= 0 ; row --)
			for(col = 0; col < size - 3; col ++)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row][col + 1][dep - 1]
						&& check == board[row][col + 2][dep - 2]
						&& check == board[row][col + 3][dep - 3])
					return true;
				}
		
		// depth down left diagnol, col -, dep +
		for(row = size - 1; row >= 0 ; row --)
			for(col = size - 1; col >= 3; col --)
				for(dep = 0; dep < size - 3; dep ++) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row][col - 1][dep + 1]
						&& check == board[row][col - 2][dep + 2]
						&& check == board[row][col - 3][dep + 3])
					return true;
				}
		
		//depth down right diagnol, col -, dep -
		for(row = size - 1; row >= 0 ; row --)
			for(col = size - 1; col >= 3; col --)
				for(dep = size - 1; dep >= 3; dep --) {
					int check = board[row][col][dep];
					if(check != -1 
						&& check == board[row][col - 1][dep - 1]
						&& check == board[row][col - 2][dep - 2]
						&& check == board[row][col - 3][dep - 3])
					return true;
				}
		//returns false if none of the above return true
		return false;
	}
	
	/******************************************************************
	 * Switches the current player and returns the new player
	 * 
	 * @return int player Returns the new current player
	 *****************************************************************/
	public int nextPlayer() {
		player = (player + 1) % playerCount;
		return player;
	}
	
	/******************************************************************
	 * Takes a value for column and depth, and returns the first
	 * available row for the chip to go. Also assigns the current
	 * player to that row, column, and depth. If all rows are full, it
	 * returns -1.
	 *  
	 * @param int col Column position on the board
	 * @param int dep Depth position on the board
	 * @return int row First available row
	 *****************************************************************/
	public int selectColDep(int col, int dep) {
		//iterates from the bottom to the top of all the rows
		for(int row = size - 1; row >= 0; row --) {
			//the first empty row found returns the row and assigns it
			if(board[row][col][dep] == -1) {
				board[row][col][dep] = player;
				return row;
			}
		}
		//returns -1 if all rows are full
		return -1;

	}
	
	/******************************************************************
	 * Returns the current player
	 * 
	 * @return int player Current player
	 *****************************************************************/
	public int getCurrentPlayer() {
		return player;
	}
	
	/******************************************************************
	 * Returns the value at the specified place on the board
	 * 
	 * @param int row Row position on the board (y)
	 * @param int col Column position on the board (x)
	 * @param int dep Depth position on the board (z)
	 * @return int value Returns player value at position
	 *****************************************************************/
	public int getValue(int row, int col, int dep) {
		return board[row][col][dep];
	}
	
	/******************************************************************
	 * Checks if the game is a tie, and the board is full
	 * 
	 * @return boolean True if the board is full, false if not
	 *****************************************************************/
	public boolean tieGame() {
		int counter = 0;
		for(int col = 0; col <= size - 1; col ++) 
			for(int dep = 0; dep <= size - 1; dep ++)
				if(board[0][col][dep] != -1) {
					counter += 1;
					if(counter == size * size) 
						return true;
				}
		return false;
	}
}
