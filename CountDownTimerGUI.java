package project1;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Integer;
import java.awt.Dimension;

/**********************************************************************
 * This class creates the buttons and fields for one timer panel.
 * It also includes the actions these buttons perform when pressed.
 * By making use of CountDownTimer methods, this GUI creates an easy
 * way to interact with a CountDownTimer object.
 * 
 * @author Alex Porter
 * @version 1/20/2018
 *********************************************************************/
public class CountDownTimerGUI extends JPanel implements ActionListener {
	/** Holds the timer information for this panel */
	CountDownTimer timerObj;

	/** Uses the Timer object to decrement every second */
	Timer countDown;

	/** Creates the suspend button */
	JButton suspendToggle;

	/** Creates the increment button */
	JButton incrementButton;

	/** Creates the decrement button */
	JButton decrementButton;

	/** Creates the subtract button */
	JButton subtractButton;

	/** Creates the add button */
	JButton addButton;

	/** Creates the save button */
	JButton saveButton;

	/** Creates the load button */
	JButton loadButton;

	/** Creates the input field for add/subtract */
	JTextField subAddField;

	/** Creates the display of the timerObj */
	JTextArea displayArea;

	/** Creates a text area for the results */
	JTextArea resultsArea;

	/** Adds a title to the panel */
	JLabel timerTitle;

	/** Initializes a text file title that is unique to the instance */ 
	String textFile = "";

	/** Initializes a file chooser to open and save files */
	JFileChooser chooser;

	/******************************************************************
	 * Creates the panel and adds all the components at their locations.
	 * Also adds actionlisteners to each component.
	 * Sets text to each component.
	 * Title parameter is used to define the JLabel.
	 * 
	 * @param title
	 *****************************************************************/
	public CountDownTimerGUI(String title) {

		//creates a text file title for the object, with format .txt
		textFile = title + ".txt";

		//instantiate an object of Timer
		timerObj = new CountDownTimer();

		//timerGetter = new mainPanel();

		// set the layout to GridBag
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();

		//create insets for each component
		Insets inset = new Insets(10,10,10,10);

		//define the areas and locations for each component

		//defines location for title label
		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 0;
		loc.gridwidth = 1;
		loc.insets = inset;
		timerTitle = new JLabel();
		timerTitle.setText(title);
		add(timerTitle, loc);

		//defines location for display area
		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 1;
		loc.gridwidth = 3;
		loc.insets = inset;
		displayArea = new JTextArea();
		displayArea.setText(timerObj.toString());
		add(displayArea, loc);

		//defines location for add/sub text field
		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 2;
		loc.gridwidth = 1;
		loc.insets = inset;
		subAddField = new JTextField();
		//sets text to instructions for user
		subAddField.setText("Enter # to add or subtract");
		//set the width to a fixed size
		subAddField.setColumns(14);
		add(subAddField,loc);

		//defines location for add button
		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 2;
		loc.gridwidth = 1;
		loc.insets = inset;
		addButton = new JButton();
		addButton.setText("Add");
		add(addButton,loc);

		//defines location sub button
		loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.gridy = 2;
		loc.gridwidth = 1;
		loc.insets = inset;
		subtractButton = new JButton();
		subtractButton.setText("Subtract");
		add(subtractButton, loc);

		//defines location for increment button
		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 3;
		loc.gridwidth = 1;
		loc.insets = inset;
		incrementButton = new JButton();
		incrementButton.setText("Increment");
		add(incrementButton, loc);

		//defines location for decrement button
		loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.gridy = 3;
		loc.gridwidth = 1;
		loc.insets = inset;
		decrementButton = new JButton();
		decrementButton.setText("Decrement");
		add(decrementButton, loc);

		//defines location for save button
		loc = new GridBagConstraints();
		loc.gridx = 1;
		loc.gridy = 4;
		loc.gridwidth = 1;
		loc.insets = inset;
		saveButton = new JButton();
		saveButton.setText("Save");
		add(saveButton, loc);

		//defines location for load button
		loc = new GridBagConstraints();
		loc.gridx = 2;
		loc.gridy = 4;
		loc.gridwidth = 1;
		loc.insets = inset;
		loadButton = new JButton();
		loadButton.setText("Load");
		add(loadButton, loc);

		//defines location for result area
		loc = new GridBagConstraints();
		loc.gridx = 0;
		loc.gridy = 5;
		loc.gridwidth = 1;
		loc.insets = inset;
		resultsArea = new JTextArea();
		resultsArea.setText("Save State Empty");
		add(resultsArea, loc);

		//add the actionlisteners to each button
		incrementButton.addActionListener(this);
		decrementButton.addActionListener(this);
		subtractButton.addActionListener(this);
		addButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);

		//create the timer that decrements every second
		//it does this by triggering its own action event
		countDown = new Timer(1000, this);

		//start the timer once the object is created
		countDown.start();

		displayArea.setFont(new Font("Courier New", Font.PLAIN, 24));
	}
	/******************************************************************
	 * This method overrides the actionPerformed method in the Action
	 * Event class. It describes what happens whenever a user presses a
	 * button. Display is then updated with changes to the internal 
	 * data.  
	 * 
	 * @param ActionEvent e
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {

		//increments and updates display
		if(e.getSource() == incrementButton) {
			timerObj.inc();
			displayArea.setText(timerObj.toString());
		}
		//decrements and updates display
		if(e.getSource() == decrementButton) {
			timerObj.dec();
			displayArea.setText(timerObj.toString());
		}
		//gets user input then subtracts
		if(e.getSource() == subtractButton) {
			try {
			int toSubtract = Integer.parseInt(subAddField.getText());
			timerObj.sub(toSubtract);
			}
			catch(Exception er) {
				throw new IllegalArgumentException();
			}
			displayArea.setText(timerObj.toString());
		}
		//gets user input then adds
		if(e.getSource() == addButton) {
			try {
			int toAdd = Integer.parseInt(subAddField.getText());
			timerObj.add(toAdd);
			}
			catch(Exception er) {
				throw new IllegalArgumentException();
			}
			displayArea.setText(timerObj.toString());
		}
		//saves current state to a file
		//changes result area to that state
		if(e.getSource() == saveButton) {
			//stop timer from modifying during saving
			countDown.stop();
			
			//creates fileChooser and creates open dialog
			chooser = new JFileChooser();
			chooser.showSaveDialog(null);
			
			//Creates writer to print to .txt
			String saveFile = chooser.getSelectedFile() + ".txt";
			timerObj.save(saveFile);
			
			//set text to last save state
			resultsArea.setText(timerObj.toString());
			
			//restart timer after saving
			countDown.start();
		}
		//loads last saved state
		if(e.getSource() == loadButton) {

			//create new chooser, open open dialog
			chooser = new JFileChooser();
			chooser.showOpenDialog(null);

			//checks if user cancels load at some point
			int status = chooser.showOpenDialog(null);
			if(status != JFileChooser.APPROVE_OPTION)
				//update text to a cancel message
				resultsArea.setText("No save file chosen");
			else {
				//cast fileName to string
				String fileName = "" + chooser.getSelectedFile(); 

				//load the string and update display
				timerObj.load(fileName);
				displayArea.setText(timerObj.toString());
				displayArea.setFont(new Font("Courier New", Font.PLAIN, 24));
			}	
		}
		//used by timer object, triggers every second
		//decrements object and updates display
		if(e.getSource() == countDown) {
			timerObj.dec();
			displayArea.setText(timerObj.toString());
			Dimension minSize = new Dimension(100, 1000);
			subAddField.setMinimumSize(minSize);

		}
	}
	/******************************************************************
	 * Gets the internal timer object.
	 * 
	 * @return CountDownTimer timerObj
	 *****************************************************************/
	public CountDownTimer getTimer() {
		return timerObj;
	}
	/******************************************************************
	 * Main method creates the GUI panel.
	 * 
	 * @param String[] args
	 *****************************************************************/
	public static void main(String args[]){
		//creates the GUI
		CountDownTimerGUI gui = new CountDownTimerGUI("");
		gui.setVisible(true);
	}
}