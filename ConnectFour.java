package project2;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**********************************************************************
 * This class creates and displays the main program frame using
 * an instance of ConnectFourPanel.
 * 
 * @author Alex Porter
 * @version 1.0
 *********************************************************************/
public class ConnectFour {

	/******************************************************************
	 * Displays the ConnectFourPanel panel on a frame
	 * By putting the creation of a new window in the constructor,
	 * it can be called later to create multiple windows.
	 *****************************************************************/
	public ConnectFour() {
		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem gameItem;   

		JFrame frame = new JFrame ("Connect Four");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("Quit");
		gameItem = new JMenuItem("New Game");

		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		ConnectFourPanel panel = new ConnectFourPanel(quitItem,
				gameItem);
		frame.getContentPane().add(panel);

		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	/******************************************************************
	 * Creates a single instance of the JFrame 
	 * 
	 * @param args Used for main method
	 *****************************************************************/
	public static void main (String[] args)
	{
		ConnectFour game = new ConnectFour();
		
	} 
}
