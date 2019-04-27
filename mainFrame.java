package project1;

import javax.swing.JFrame;

/**********************************************************************
 * This mainFrame class creates a single JFrame that holds a mainPanel
 * instance. This is the main container for each GUI panel. Running 
 * the main method of this class creates the frame the user can 
 * interact with.
 * 
 * @author Alex Porter
 * @version 1/2018
 *********************************************************************/
public class mainFrame {
	/******************************************************************
	 * Running this method creates the frame the user can interact with
	 * 
	 * @param String[] args
	 *****************************************************************/	
	public static void main (String[] args ) {
		//creates frame title
		JFrame frame = new JFrame("CountDown Timers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creates and adds the main panel
		mainPanel panel = new mainPanel();
		frame.getContentPane().add(panel);
		
		//sets the size and makes it visible
		frame.setSize(1500,380);
		frame.setVisible(true);
	}
}