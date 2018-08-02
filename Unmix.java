package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**********************************************************************
 * This class takes a string and file, reads the commands from the file
 * and applies them to the string to return a secret message. 
 * 
 * @author Alex Porter
 * @version 4/11/2018
 *********************************************************************/
public class Unmix {
	/** Holds the name of the file being read from */
	private String fileName = "";
	
	/** Holds the message that will be printed later */
	private String message = "";
	
	/** Represents an engine that performs all of the commands*/
	private LinkedList engine;
	
	/** Reads each entry from the file*/
	private Scanner fileReader;
	
	/******************************************************************
	 * Simple constructor creates a new linked list engine
	 * 
	 *****************************************************************/
	public Unmix() {
		engine = new LinkedList(null);
	}
	
	/******************************************************************
	 * Returns string message
	 * 
	 * @return message Current string message
	 *****************************************************************/
	public String getMessage() {
		return message;
	}
	/******************************************************************
	 * Sets message to given string
	 * 
	 * @param str String to set message to
	 *****************************************************************/
	public void setMessage(String str) {
		message = str;
	}
	
	/******************************************************************
	 * Returns name of file
	 * 
	 * @return fileName String of file name
	 *****************************************************************/
	public String getFileName() {
		return fileName;
	}
	
	/******************************************************************
	 * Sets fileName to given string
	 * 
	 * @param str String to set fileName to 
	 *****************************************************************/
	public void setFileName(String str) {
		fileName = str;
	}
	
	/******************************************************************
	 * This method does the work of the class. It takes in a string 
	 * for the message it will be modifying, and a filename which
	 * holds a list of commands. Depending on the command, the 
	 * LinkedList engine executes a change to the string. After the
	 * file is empty, the method exits and prints the modified string
	 * 
	 * @param fileName String name of file
	 * @param input String message to modify
	 *****************************************************************/
	public void UnmixMessage(String fileName, String input) {
		
		//create a new fileReader
		fileReader = null;
		try {
			fileReader = new Scanner(new File(fileName + ".txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Encryption key file not found");
		}
		//convert the message to a linked list
		engine.convertToLinkedListNew(input);
		
		//while there's still commands in the file
		while(fileReader.hasNext()) {
			
			//get first char, represents command
			String firstChar = fileReader.next();
			switch(firstChar) {
			//if r, remove from given params
			case("r") :
				int firstPos = fileReader.nextInt();
				int secondPos = fileReader.nextInt();
				engine.remove(firstPos, secondPos);
				break;
			//if b, insert before given params
			case("b") :
				int pos = fileReader.nextInt();
				String toAdd = fileReader.nextLine();
				toAdd = toAdd.substring(1);
				engine.bInsert(toAdd, pos);
				break;
			//if t, replace given area with param
			case("t") :
				int pos1 = fileReader.nextInt();
				int pos2 = fileReader.nextInt();
				String toReplace = fileReader.nextLine();
				toReplace = toReplace.substring(1);
				engine.replace(pos1, pos2, toReplace);
				break;
				//some other letter was read, notify user of error
			default :
				System.out.println("Error reading from file");
				break;
			}
		}
		//Print final unmixed message
		System.out.println("Final unmixed message: ");
		System.out.print(engine.sendNodeToString());
	}
	
	/******************************************************************
	 * This method reverts all ` characters in the file with spaces.
	 * This reverses the mix file, which replaces all spaces with `.
	 * That way, spaces can be read by the scanner since they are
	 * represented by a solid character.
	 * 
	 * @param str String with ` characters inside
	 * @return str String replaced ` with spaces
	 *****************************************************************/
	public String modifySpaces(String str) {
		if(str.charAt(0) == '`') {
			str = " " + str.substring(1);
		}
		for(int i = 0; i < str.length(); i ++) {
			if(str.charAt(i) == '`') {
				str = str.substring(0, i) + " " + str.substring(i + 1);
			}
		}
		return str;
	}
	/******************************************************************
	 * Prints given message and fileName from program args.
	 * Modifies spaces before passing the string to UnmixMessage.
	 * Finds and prints unmixed message.
	 * 
	 * @param args String[] of inputs fileName, message
	 *****************************************************************/
	public static void main(String[] args) {
		Unmix unmixer = new Unmix();
		unmixer.setFileName(args[0]);
		System.out.println("File: " + unmixer.getFileName());
		String modSpaces = args[1];
		modSpaces = unmixer.modifySpaces(modSpaces);
		unmixer.setMessage(modSpaces);
		System.out.println("Message: "+ unmixer.getMessage() + "\n");
		unmixer.UnmixMessage(unmixer.getFileName(), unmixer.getMessage());
		
	}
}
