import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

/*************************************************************
 * GUI for a Baby Name Database
 * 
 * @author Scott Grissom
 * @version October 7, 2017
 ************************************************************/
public class BabyNameGUI extends JFrame implements ActionListener{

    // FIX ME: Define a BabyNameDatabase variable
    BabyNamesDatabase database = new BabyNamesDatabase();

    // FIX ME: Define buttons
    JButton byYear;
    JButton mostPopular;
    JButton topTen;
    JButton byName;

    // FIX ME: Define text fields
    JTextField yearSearch;
    JTextField nameSearch;

    /** Results text area */
    JTextArea resultsArea;

    /** menu items */
    JMenuBar menus;
    JMenu fileMenu;
    JMenuItem quitItem;
    JMenuItem openItem;
    JMenuItem countItem;
    
    private boolean check = false;

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        BabyNameGUI gui = new BabyNameGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Baby Names");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public BabyNameGUI(){

        // FIX ME: instantiate an object of type BabyNameDatbase  
        BabyNamesDatabase database = new BabyNamesDatabase();

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

        // create Searches label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Searches"), loc);     

        // FIX ME: create labels, textfields and buttons 
        //create year label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 1;
        loc.gridwidth = 1;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        add(new JLabel("Year"),loc);
        
        //create yearSearch TextField
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 1;
        loc.gridwidth = 50;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        yearSearch = new JTextField("Enter text");
        yearSearch.setSize(20,24);
        yearSearch.setEditable(true);
        add(yearSearch,loc);
        
        //create By year button
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 2;
        loc.gridwidth = 2;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        byYear = new JButton("By Year");
        add(byYear,loc);
        
        //create most popular button
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 3;
        loc.gridwidth = 2;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        mostPopular = new JButton("Most Popular");
        add(mostPopular,loc);
        
        //create top ten button
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 4;
        loc.gridwidth = 2;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        topTen = new JButton("Top Ten");
        add(topTen,loc);
        
        //create name label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 7;
        loc.gridwidth = 2;
        loc.insets.bottom = 10;
        loc.insets.top = 10;
        add(new JLabel("Name"),loc);
        
        //create name search text box
        loc = new GridBagConstraints();
        loc.gridx = 3;
        loc.gridy = 7;
        loc.gridwidth = 50;
        nameSearch = new JTextField("Enter text");
        nameSearch.setSize(20,24);
        nameSearch.setEditable(true);
        add(nameSearch,loc);
        
        //create By name button
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 8;
        loc.gridwidth = 2;
        byName = new JButton("By Name");
        add(byName,loc);
        
        

        // FIX ME: register listeners for the buttons
        topTen.addActionListener(this);
        mostPopular.addActionListener(this);
        byName.addActionListener(this);
        byYear.addActionListener(this);
        

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
        
        // Allow user to load baby names from a file    
        if (buttonPressed == openItem){
            openFile();
            //changes a boolean if a file is opened
            this.check = true;
        }  
        
        //displays top ten if button is pressed
        if (buttonPressed == topTen) {
            displayTopTen(getYearFromText());
            //checks if file has been loaded
            //used in all action methods
            checkOpenFile(check);
        }
        
        //displays most popular if button is presed
        if (buttonPressed == mostPopular) {
            displayMostPopular(getYearFromText());
            checkOpenFile(check);
        }
        
        //displays by name if button is pressed
        if (buttonPressed == byName) {
            //picks up the name from the text box
            String nameInput = nameSearch.getText();
            
            //runs only if name has a length
            if(nameInput.length() == 0) {
                resultsArea.setText("Error: You did not enter a name");
            }
            else {
                displayByName(nameInput);
                checkOpenFile(check);
            }
        }
        
        //displays by year if button is pressed
        if (buttonPressed == byYear) {
            displayByYear(getYearFromText());
            checkOpenFile(check);
        }
        
        //displays counts if file item is pressed
        if(buttonPressed == countItem) {
            displayCounts();
            checkOpenFile(check);
        }
        
        //quits if quit file item is pressed
        if(buttonPressed == quitItem) {
            System.exit(0);
        }

    }
    
    /**
     * Get the year from the text box.
     * Checks if year is invalid
     */
    private int getYearFromText() {
        int year = 0;
        try {
            String yearInput = yearSearch.getText();
            if(yearInput.length() == 0) {
                //if no year entered, go to default error checking
                throw new NumberFormatException();
            }
            //throws error if not an integer
            year = Integer.parseInt(yearInput);
        }
        catch (NumberFormatException f) {
            //this value is handled later in the program
            year = 0;
        }
        
        //returns 0 if yearSearch was empty or invalid
        return year;
    }
    
    /*****************************************
     * Check if a data file has been loaded
     ******************************************/
    private void checkOpenFile(boolean check) {
        if(!check) {
            //changes the text to explain why nothing happened in the program
            resultsArea.setText("Error: No data file loaded.");
        }
    }

    /*****************************************************************
     * open a data file with the name selected by the user
     ****************************************************************/ 
    private void openFile(){

        // create File Chooser so that it starts at the current directory
        String userDir = System.getProperty("user.dir");
        JFileChooser fc = new JFileChooser(userDir);

        // show File Chooser and wait for user selection
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            
            // FIX ME: change the following line as needed
            // to use your instance variable name
            database.readBabyNameData(filename);          
        }
    }
    
    /***************************************
     * Displays the top ten names for a year
     ***************************************/
    private void displayTopTen(int year) {
        try{
            //initializes string to a banner that is displayed at the top of the results
            String displayData = "Top Ten Names in " + year + "\n\n";
            //goes through each name in the topTenList and adds it to the string
            for(BabyName name : database.topTenNames(year)) {
                displayData += name.toString();
            }
            //checks for an invalid year
            if(year == 0 || year > 2016 || year < 1880) {
                resultsArea.setText("Error: Invalid year (<1880 or >2016) or no year entered.");
            }
            else {
                //displays the results plus the total names in the list
                resultsArea.setText(displayData + "\n Total: " + database.topTenNames(year).size());
            }
        }
        //for loop throws an error if no data is loaded, this catches that error
        catch(IndexOutOfBoundsException e) {
            resultsArea.setText("Error: No data file loaded.");
        }
    }
    /***************************************************
     * Displays the most popular boy and girl for a year
     ***************************************************/   
    private void displayMostPopular(int year) {
        //initialize the string to an upper banner
        String result = "Most Popular names for year " + year + "\n";
        //adds the most popular boy
        result += database.mostPopularBoy(year).toString();
        //adds the most popular girl
        result += database.mostPopularGirl(year).toString();
        //checks if year is valid
        if(year == 0 || year > 2016 || year < 1880) {
            resultsArea.setText("Error: Invalid year (<1880 or >2016) or no year entered.");
        }
        else {
            //if year is valid, sets text to the result string
            resultsArea.setText(result);
        }
    }
    
    /******************************************
     * Displays all the years a name appears in
     ******************************************/
    private void displayByName(String nameParam) {
        //initialize string and a count variable
        String displayData = "";
        int count = 0;
        for(BabyName name: database.searchForName(nameParam)) {
            //for all the names in the returned list, add it to the string
            //also increment count by 1
            displayData += name.toString();
            count += 1;
        }
        //returns list of names/years, the # of years the name appears, and the name provided
        resultsArea.setText(displayData + "\n Total: " + count + "\n All years with name: " + nameParam);
    }
    
    /**********************************
     * Displays all the names in a year
     **********************************/
    private void displayByYear(int year) {
        try {
            //initialize a string for results and count
            String displayData = "";
            int count = 0;
            for(BabyName name: database.searchForYear(year)) {
                //adds each name with given year to string
                //also adds to count
                //throws error if no data is loaded
                displayData += name.toString();
                count += 1;
            }
            //check for valid year
            if(year == 0 || year > 2016 || year < 1880) {
                resultsArea.setText("Error: Invalid year (<1880 or >2016) or no year entered.");
            }
            else {
                //if valid, display the data, number of unique names, and given year
                resultsArea.setText(displayData + "\n Total: " + count + " unique names in year " + year);
            }
        }
        catch(IndexOutOfBoundsException e) {
            //catches if no data has been loaded
            resultsArea.setText("Error: No data file loaded.");
        }
    }
    
    /**********************************
     * Displays the counts of the names
     **********************************/
    private void displayCounts() {
        //initialize string
        String displayData = "Total Counts: \n\n";
        //add unique names to string
        displayData += "All unique names: " + database.countAllNames() + "\n";
        //add boys to string + formatting
        displayData += "All boys: " + database.countAllBoys() + "\n";
        //add girls to string + formatting
        displayData += "All girls: " + database.countAllGirls() + "\n";
        //set text to the string
        resultsArea.setText(displayData);
    }

    /*******************************************************
    Creates the menu items
     *******************************************************/    
    private void setupMenus(){
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        countItem = new JMenuItem("Counts");
        openItem = new JMenuItem("Open...");
        fileMenu.add(countItem);
        fileMenu.add(openItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

        // FIX ME: register the menu items with the action listener
        fileMenu.addActionListener(this);
        quitItem.addActionListener(this);
        countItem.addActionListener(this);
        openItem.addActionListener(this);

    }
}