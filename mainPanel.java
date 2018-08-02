package project1;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**********************************************************************
 * This class creates three CountDownTimerGUI panels and places them
 * in one main panel. It also implements a global "Suspend" button
 * that suspends operations an all CountDownTimer Objects. This is
 * a toggle button. A single instance of this class is created in
 * the mainFrame class which holds this panel.
 * 
 * @author Alex Porter
 * @version 1/20/2018
 *********************************************************************/
public class mainPanel extends JPanel implements ActionListener {
	/** Creates the first GUI panel */
	CountDownTimerGUI timer1;
	
	/** Creates the second GUI panel */
	CountDownTimerGUI timer2;
	
	/** Creates the third GUI panel */
	CountDownTimerGUI timer3;
	
	/** Creates the suspend button */
	JButton susButton;
	/******************************************************************
	 * Instantiates the three GUI panels and adds a distinguishing 
	 * border. Also adds the suspend button and sets up its Action
	 * Listener.
	 *****************************************************************/
	public mainPanel() {
		//creates three panels with unique titles
		timer1 = new CountDownTimerGUI("Timer 1");
		timer2 = new CountDownTimerGUI("Timer 2");
		timer3 = new CountDownTimerGUI("Timer 3");
		
		//adds borders to the panels
		timer1.setBorder(BorderFactory.createLineBorder(Color.black));
		timer2.setBorder(BorderFactory.createLineBorder(Color.black));
		timer3.setBorder(BorderFactory.createLineBorder(Color.black));
		
		//instantiates suspend button
		susButton = new JButton();

		//adds actionListener to button
		susButton.addActionListener(this);
		
		//add timers to main panel
		add(timer1);
		add(timer2);
		add(timer3);
		
		//add button to main panel
		susButton.setText("Suspend All Operations");
		add(susButton);
	}
	/******************************************************************
	 * Runs when the master suspend button is triggered. Based on the 
	 * state of the suspendFlag, it toggles between true and false. 
	 * 
	 * @param ActionEvent e
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == susButton)
			//changes to true if already false
			if(CountDownTimer.getFlag() == false)
				CountDownTimer.suspend(true);
			//changes to false if true
			else
				CountDownTimer.suspend(false);
	}
}