package project4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**********************************************************************
 * This class reads input from the user and modifies the linked list
 * they give through program arguments. Also saves the reverse of each
 * command, so the message can be returned to it's original state 
 * later.
 * 
 * @author Alex Porter
 * @version 4/11/2018
 *********************************************************************/
public class Mix {
	/** An engine that commits the commands to the linked list*/
	private LinkedList engine;
	
	/** A scanner that reads input from the user*/
	private Scanner scnr;
	
	/** A string holding all the reverse commands */
	private String undoCommands;
	
	/** Prints the undoCommands to a file later*/
	private PrintWriter out;

	/******************************************************************
	 * Simple constructor creates a new LinkedList engine, 
	 * and a scanner, and intialized undoCommands.
	 *****************************************************************/
	public Mix() {
		engine = new LinkedList(null);
		scnr = new Scanner(System.in);
		undoCommands = "";
	}
	
	/******************************************************************
	 * This method does most of the work for the program.
	 * It reads the commands the user enters, and commits the 
	 * action the user desires accordingly. Each command also
	 * adds the reverse to the undoCommands string. Loop
	 * exits once user quits and saves their message to a file.
	 * 
	 * @param input String message to convert to linked list
	 * @throws IllegalArgumentException() If insert index out of bounds
	 *****************************************************************/
	public void mixMessage(String input) {
		boolean exitCondition = true;
		engine.convertToLinkedListNew(input);
		displayCurrentMessage();
		//engine.displayCurrentMessage();

		while(exitCondition) {
			try {
				//gets the command denoted by first character
				String firstChar = scnr.next().toLowerCase();
				switch(firstChar) {
				//for each, get input by scanning and perform
				//method after necessary input is gathered
				
				case("q") : //quit and save
					String fileName = scnr.next();
				save(fileName);
				displayQuitMessage(fileName);
				engine.quitMix();	
				break;
				
				case("b") : //insert before
					int atPos = scnr.nextInt();
				String toInsert = scnr.nextLine();
				toInsert = toInsert.substring(1);
				//throw if insert out of bounds
				if(atPos == engine.getLength()) {
					throw new IllegalArgumentException();
				}
				else {
					bInsert(toInsert, atPos);
				}
				displayCurrentMessage();
				break;
				
				case("a") : //insert after
				atPos = scnr.nextInt();
				toInsert = scnr.nextLine();
				toInsert = toInsert.substring(1);
				bInsertAfter(toInsert, atPos);
				displayCurrentMessage();
				break;
				
				case("r") : //remove
					int indexStart = scnr.nextInt();
				if(scnr.hasNextInt()) {
					int indexEnd = scnr.nextInt();
					remove(indexStart, indexEnd); 
				}
				else {
					remove(indexStart, indexStart);
				}
				displayCurrentMessage();
				break;
				
				case("s") : //swap
					int firstNum = scnr.nextInt();
				int secondNum = scnr.nextInt();
				swapPositions(firstNum, secondNum);
				displayCurrentMessage();
				break;
				
				case("t") : //replace
					firstNum = scnr.nextInt();
				secondNum = scnr.nextInt();
				String toReplace = scnr.nextLine();
				toReplace = toReplace.substring(1);
				replace(firstNum, secondNum, toReplace);
				displayCurrentMessage();
				break;
				
				case("h") : //help
					hHelp();
				displayCurrentMessage();
				break;
				case("m") : //move to front
					atPos = scnr.nextInt();
				moveToFront(atPos);
				displayCurrentMessage();
				break;
				
				case("x") : //cut
					firstNum = scnr.nextInt();
				secondNum = scnr.nextInt();
				int thirdNum = scnr.nextInt();
				cut(firstNum, secondNum, thirdNum);
				displayCurrentMessage();
				break;
				
				case("c") : //copy
					firstNum = scnr.nextInt();
				secondNum = scnr.nextInt();
				thirdNum = scnr.nextInt();
				copy(firstNum, secondNum, thirdNum);
				displayCurrentMessage();
				break;
				
				case("p") : //paste
					atPos = scnr.nextInt();
				secondNum = scnr.nextInt();
				paste(atPos, secondNum);
				displayCurrentMessage();
				break;

				//if some other letter is entered
				default :
					System.out.println("Invalid command: "
							+ "Type 'h' for options.");
					//engine.displayCurrentMessage();
					continue;
				}
			}
			//runs scanner doesn't find a letter or illegal arg
			catch(Exception e) {
				System.out.println("Error: Expected value was"
						+ " not found. Type 'h' for commands.");
				//runs if error was thrown in LinkedList
				if(e instanceof IllegalArgumentException) {
					System.out.println("Invalid integer entry: "
							+ "Index out of bounds or negative.");
					//engine.displayCurrentMessage();
				}
			}
		}

	}
	/******************************************************************
	 * Converts program input to a single string
	 * 
	 * @param config Program arguments
	 * @return String of all program args
	 *****************************************************************/
	public static String convertArgsToString(String[] config) {
		String toReturn = "";
		toReturn += config[0];
		for(int i = 1; i < config.length; i++) {
			toReturn += " " + config[i];
		}
		return toReturn;
	}
	/******************************************************************
	 * Prints a help page to the screen.
	 *****************************************************************/
	private void hHelp() {
System.out.println("Help page:");
System.out.println("____________________"
		+ "____________________________________________________");
System.out.println("Commands:            Description:");
System.out.println("____________________"
		+ "____________________________________________________");
System.out.println("Q s                  "
		+ "Quit the program, store decrypt key in file s");
System.out.println("m #                  "
		+ "Move element at position # to the front");
System.out.println("r # *                "
		+ "Remove all chars from range # to *");
System.out.println("b # s                "
		+ "Insert the string s before position #");
System.out.println("a # s                "
		+ "Insert the string s after position #");
System.out.println("s # *                "
		+ "Swap element at position # with position *");
System.out.println("t # * s              "
		+ "Replace the range (#,*) of chars with new chars (s)");
System.out.println("x # * &             "
		+ " Cut range (#,*) to clipboard number &");
System.out.println("c # * &             "
		+ " Cut range (#,*) to clipboard number &");
System.out.println("p # *                "
		+ "Paste string in clipboard * before position #");
System.out.println("_____________________"
		+ "___________________________________________________");
System.out.println("");
System.out.println("Spaces Note: The ` "
		+ "character is interchangeable with spaces.");
System.out.println("Use it to insert spaces"
		+ " before a word.(\"``from\" becomes \"  from\"");
System.out.println("This means all instances"
		+ " of ` in the message will be replaced with spaces.");
	}
	/******************************************************************
	 * Called when executing remove command. Removes node
	 * from given position in the linked list and provides
	 * the reverse of the command. Before printing the reverse, it
	 * converts all spaces to the ` character.
	 * 
	 * @param pos1 Beginning of position range
	 * @param pos2 End of position range
	 *****************************************************************/
	private void remove(int pos1, int pos2) {
		String topString = engine.sendNodeToString();
		topString = topString.substring(pos1, pos2 + 1);
		topString = modifySpaces(topString);
		engine.remove(pos1, pos2);
		undoCommands = "b " + pos1 + " " + topString + 
				"\n" + undoCommands;
	}

	/******************************************************************
	 * Called when executing the insert before command. Also
	 * adds the reverse command to the undoCommands string.
	 * 
	 * @param str String to be inserted
	 * @param pos Position to insert the string before
	 *****************************************************************/
	private void bInsert(String str, int pos) {
		engine.bInsert(str, pos);
		int toAdd = 0;
		if(str.length() > 1) {
			toAdd = str.length();
		}
		undoCommands = "r " + pos + " " + (toAdd + pos)
				+ "\n" + undoCommands;
	}

	/******************************************************************
	 * Called when executing insert after command. Adds reverse command
	 * to the undoCommands string as well.
	 * 
	 * @param str String to insert after position
	 * @param pos Position to insert before
	 *****************************************************************/
	private void bInsertAfter(String str, int pos) {
		engine.bInsertAfter(str, pos);
		int toAdd = 0;
		if(str.length() > 1) {
			toAdd = str.length();
		}
		undoCommands = "r " + (pos + 1) + " " + (toAdd + pos + 1)
				+ "\n" + undoCommands;
	}

	/******************************************************************
	 * Called when swapping two positions. Finds strings
	 * to be replaced and adds them to undoCommands as
	 * well. 
	 * 
	 * @param pos1 First position to switch
	 * @param pos2 Second position to switch
	 *****************************************************************/
	private void swapPositions(int pos1, int pos2) {
		String topString = engine.sendNodeToString();
		String first = topString.substring(pos1, pos1 + 1);
		String second = topString.substring(pos2, pos2 + 1);
		second = modifySpaces(second);
		first = modifySpaces(first);
		//if first or second is a space, then write it as a `
		engine.swapPositions(pos1, pos2);
		undoCommands = "b " + pos2 + " " + second + "\n" + 
		undoCommands;
		undoCommands = "b " + (pos1) + " " + first + "\n"+
		undoCommands;
		undoCommands = "r " + (pos1) + " " + (pos1) + "\n" +
		undoCommands;
		undoCommands = "r " + (pos2) + " " + (pos2) + "\n" + 
		undoCommands;
	}

	/******************************************************************
	 * Called when executing move to front command. Modifies
	 * linked list and adds reverse command to txt file as well
	 * 
	 * @param pos Position to move to front.
	 *****************************************************************/
	private void moveToFront(int pos) {
		String topString = engine.sendNodeToString();
		String moved = topString.substring(pos, pos + 1);
		moved = modifySpaces(moved);
		engine.moveToFront(pos);
		undoCommands = "b " + pos + " " + moved + "\n" + undoCommands;
		undoCommands = "r 0 0\n" + undoCommands;
	}

	/******************************************************************
	 * Called when executing the replace command. Adds reverse
	 * command to undoCommands string as well. 
	 * 
	 * @param pos1 Beginning of replace range
	 * @param pos2 End of replace range
	 * @param str String that replaces given range
	 *****************************************************************/
	private void replace(int pos1, int pos2, String str) {
		String topString = engine.sendNodeToString();
		String removed = topString.substring(pos1, pos2 + 1);
		removed = modifySpaces(removed);
		engine.replace(pos1, pos2, str);
		int toAdd = str.length() - 1;
		undoCommands = "t " + pos1 + " " + (pos1 + toAdd) + " " + 
		removed + "\n" + undoCommands;
	}
	/******************************************************************
	 * Displays the current message with formatting.
	 *****************************************************************/
	private void displayCurrentMessage() {
		System.out.println("Message: ");
		System.out.println(engine.displayString());
		System.out.println("Command:  ");
	}
	/******************************************************************
	 * Displays the modified string with quotes, and the file name.
	 * This makes it east for the user to copy and paste into Unmix.
	 * 
	 * @param fileName Name of file to be displayed
	 *****************************************************************/
	private void displayQuitMessage(String fileName) {
		System.out.println("Final mixed up message: ");
		System.out.print("Key: " + fileName + "  ");
		String topString = engine.sendNodeToString();
		if(topString == "") {
			System.out.println("null");
		}
		else {
			System.out.print("\"");
			System.out.print(topString);
		}
		System.out.println("\"");
	}

	/******************************************************************
	 * Copies the given range to a new clipboard.
	 * Since this does not modify the string, nothing
	 * is added to undoCommands.
	 * 
	 * @param pos1 Beginning of range to copy
	 * @param pos2 End of range to copy
	 * @param index Clipboard snippet is copied to
	 *****************************************************************/
	private void copy(int pos1, int pos2, int index) {
		engine.copy(pos1, pos2, index);
	}
	
	/******************************************************************
	 * Cuts the given range from the linked list and adds it to a 
	 * clipboard at the given index. Removes the range from the linked
	 * list and adds reverse to undoCommands.
	 * 
	 * @param pos1 Beginning of range
	 * @param pos2 End of range
	 * @param index Clipboard to cut to
	 *****************************************************************/
	private void cut(int pos1, int pos2, int index) {
		String topString = engine.sendNodeToString();
		topString = topString.substring(pos1, pos2 + 1);
		topString = modifySpaces(topString);
		undoCommands = "b " + pos1 + " " + topString + 
				"\n" + undoCommands;
		engine.cut(pos1, pos2, index);
	}
	/******************************************************************
	 * Pastes the node stored in the clipboard before position pos.
	 * Adds a remove command to undoCommands as well.
	 * 
	 * @param pos
	 * @param index
	 *****************************************************************/
	private void paste(int pos, int index) {
		String firstTop = engine.sendNodeToString();
		engine.paste(pos, index);
		String secondTop = engine.sendNodeToString();
		int lengthAdded = secondTop.length() - firstTop.length();
		undoCommands = "r " + pos + " " + (lengthAdded + pos - 1) 
				+ "\n" + undoCommands;
	}

	/******************************************************************
	 * Saves the undoCommands to a file named fileName. Does so
	 * by creating a printwriter and printing undoCommands to the 
	 * file. 
	 * 
	 * @param fileName File name to print to 
	 *****************************************************************/
	private void save(String fileName) {
		out = null;
		try {
			//attempt to create the printwriter
			out = new PrintWriter(new BufferedWriter(new 
					FileWriter(fileName + ".txt")));
		}
		//runs if invalid input
		catch (Exception e) {
			throw new IllegalArgumentException();
		}
		out.print(undoCommands);
		out.close();
	}

	/******************************************************************
	 * Parses the given string and replaces all the spaces with the
	 * ` character. That way spaces can be modified easily in the 
	 * unmix class.
	 * 
	 * @param str String to modify spaces
	 * @return str String with spaces modified
	 *****************************************************************/
	private String modifySpaces(String str) {
		if(str.charAt(0) == ' ') {
			str = "`" + str.substring(1);
		}
		for(int i = 0; i < str.length(); i ++) {
			if(str.charAt(i) == ' ') {
				str = str.substring(0, i) + "`" + str.substring(i + 1);
			}
		}
		return str;
	}


	/******************************************************************
	 * The main method takes in an array and converts
	 * the pieces to a single string. It then takes user input
	 * to modify the string until exiting with quitMix().
	 * 
	 * @param args User input entered
	 *****************************************************************/
	public static void main(String[] args) {
		String input = convertArgsToString(args);
		Mix mixer = new Mix();
		mixer.mixMessage(input);

	}
}

