package project3;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ChessFrame {

	//-----------------------------------------------------------------
	//  Creates and displays the main program frame.
	//-----------------------------------------------------------------
	public static void main (String[] args)
	{

	    JMenuBar menus;
	    JMenu fileMenu;
	    JMenuItem quitItem;
	    JMenuItem gameItem;  
	    JMenuItem restartItem;
	    JMenuItem undoItem;

		JFrame frame = new JFrame ("Chess");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        gameItem = new JMenuItem("New game");
        restartItem = new JMenuItem("Restart");
        undoItem = new JMenuItem("Undo");
        
        fileMenu.add(gameItem);
        fileMenu.add(quitItem);
        fileMenu.add(restartItem);
        menus = new JMenuBar();
        frame.setJMenuBar(menus);
        menus.add(fileMenu);
     
        ChessPanel panel = new ChessPanel(quitItem, gameItem, restartItem, undoItem);
		frame.getContentPane().add(panel);

		frame.setSize(800, 800);
		frame.setVisible(true);
	} 
}
