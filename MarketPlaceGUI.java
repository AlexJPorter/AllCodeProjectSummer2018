import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.lang.Integer;
import java.lang.Double;

/*************************************************************
 * GUI for a Baby Name Database
 * 
 * @author Scott Grissom
 * @version October 7, 2017
 ************************************************************/
public class MarketPlaceGUI extends JFrame implements ActionListener{

    MarketPlace store;

    // Define simulate button
    JButton simulate;
    
    //define check box
    JCheckBox displayBox;

    //Define text areas
    JTextField cashiersArea;
    JTextField avgArrivalTimeArea;
    JTextField avgServiceTimeArea;

    /** Results text area */
    JTextArea resultsArea;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem clearItem;
    
    //signify checkbox status
    //and if an error was made
    boolean displayCheck = false;
    boolean paramError = false;
    

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        //creates the GUI
        MarketPlaceGUI gui = new MarketPlaceGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("MarketPlace Simulation");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public MarketPlaceGUI(){

        //instantiate an object of MarketPlace
        store = new MarketPlace();

        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        // create results area to span one column and 10 rows
        resultsArea = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 10;  
        loc.insets.left = 20;
        loc.insets.right = 20;
        loc.insets.bottom = 20;
        add(scrollPane, loc);  

        // create Results label
        loc = new GridBagConstraints();
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        loc.insets.top = 20;
        add(new JLabel("Results"), loc);

        // create cashiers label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Cashiers"), loc);
        
        //create arrival time label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 1;
        loc.gridwidth = 2;
        add(new JLabel("Avg arrival time:"), loc);
        
        //create service time label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 2;
        loc.gridwidth = 2;
        add(new JLabel("Avg service time: "), loc);

        // cashierArea text field
        loc = new GridBagConstraints();
        loc.gridx = 3;
        loc.gridy = 0;
        loc.gridwidth = 50;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        loc.insets.right = 10;
        cashiersArea = new JTextField("Enter text");
        cashiersArea.setSize(20,24);
        cashiersArea.setEditable(true);
        add(cashiersArea,loc);
        
        //arrivalTime text field
        loc = new GridBagConstraints();
        loc.gridx = 3;
        loc.gridy = 1;
        loc.gridwidth = 50;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        loc.insets.right = 10;
        avgArrivalTimeArea = new JTextField("Enter text");
        avgArrivalTimeArea.setSize(20,24);
        avgArrivalTimeArea.setEditable(true);
        add(avgArrivalTimeArea,loc);
        
        //avgServicetime text field
        loc = new GridBagConstraints();
        loc.gridx = 3;
        loc.gridy = 2;
        loc.gridwidth = 50;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        loc.insets.right = 10;
        loc.insets.left = 0;
        avgServiceTimeArea = new JTextField("Enter text");
        avgServiceTimeArea.setSize(20,24);
        avgServiceTimeArea.setEditable(true);
        add(avgServiceTimeArea,loc);
        
        //add simulate button
        loc = new GridBagConstraints();
        loc.gridx = 0;
        loc.gridy = 20;
        loc.gridwidth = 2;
        loc.insets.bottom = 10;
        simulate = new JButton("Simulate");
        add(simulate,loc);
        
        //put the checkbox on screen
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 3;
        loc.gridwidth = 2;
        displayBox = new JCheckBox("Display");
        add(displayBox,loc);

        //register listeners for the buttons
        
        simulate.addActionListener(this);
        // hide details of creating menus
        setupMenus();
    }

    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent buttonPressed = (JComponent) e.getSource();
        
        //check parameters and simulate
        //change output based on display as well
        if(buttonPressed == simulate) {
            displayCheck = displayBox.isSelected();
            checkSetParameters();
            if(!paramError) {
                store.startSimulation();
                resultsArea.setText(store.getReport());
            }
        }
        
        //quits the GUI
        if(buttonPressed == quitItem) {
            System.exit(0);
        }
        
        //clears the screen
        if(buttonPressed == clearItem) {
            resultsArea.setText("");
        }
    }
    
    public void checkSetParameters() {
        int cashInput = 0;
        double arrivalInput = 0.0;
        double timeInput = 0.0;
        try{
            //checks if cashiers is wrong
            try {
                 cashInput = Integer.parseInt(cashiersArea.getText());
                 if(cashInput <= 0 || cashInput > 20) {
                     throw new Exception();
                 }
            }
            catch(Exception e) {
                //shows message then exits with another throw
                JOptionPane.showMessageDialog(null, "Invalid cashiers: Numeral between 1 and 8 required.");
                throw new Exception();
            }
            
            try {
                //checks if arrival is a double
                //and if it had a suitable range
                arrivalInput = Double.parseDouble(avgArrivalTimeArea.getText());
                if(arrivalInput <= 0 || arrivalInput >= 480) {
                    throw new Exception();
                }
            }
            catch(Exception e) {
                //show message then exit
                JOptionPane.showMessageDialog(null, "Invalid arrival time: Numeral between 1 and 480 required.");
                throw new Exception();
            }
            
            try {
                //check if double and good range
                timeInput = Double.parseDouble(avgServiceTimeArea.getText());
                if(timeInput <= 0 || timeInput >= 480) {
                    throw new Exception();
                }
            }
            catch(Exception e) {
                //show message then exit
                JOptionPane.showMessageDialog(null, "Invalid service time: Numeral between 1 and 480 required.");
                throw new Exception();
            }
            
            //only runs if no errors occur
            store.setParameters(cashInput, timeInput, arrivalInput, displayBox.isSelected());
        }
        catch(Exception e) {
            //method exits and allows user to try a different input
            //paramError stops simulation from running
            paramError = true;
        }
    }


    /*******************************************************
    Creates the menu items
     *******************************************************/    
    private void setupMenus(){
        //add file, quit, and clear
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        clearItem = new JMenuItem("Clear");
        //add to bar
        fileMenu.add(clearItem);
        fileMenu.add(quitItem);
        //set up bar
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        //register the menu items with the action listener
        fileMenu.addActionListener(this);
        quitItem.addActionListener(this);
        clearItem.addActionListener(this);

    }
}