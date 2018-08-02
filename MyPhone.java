/**
 * This class defines the methods and functionality of a simple smart phone
 * Author - Alex Porter
 * Version 9/2017
 */
//imports a variety of classes to use many methods
import java.lang.Math;
import java.util.Scanner;
import java.lang.String;
import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Random; 
import java.lang.Integer;
import java.lang.Double;

public class MyPhone
{
    
    //initialize variables that will be used throughout the program
    private int numOfTexts            = 0;
    private double dataConsumed       = 0.0; //this is in MB
    private double batteryLife        = 0.0; // a value from 0 to 1
    private String custName           = "";
    private String phoneNumber        = "";
    private double dataCost           = 0.0; //used to calc data cost
    //define constant values for audio streaming
    private final double DATA_PER_MIN = 65/ 60.0; 
    private final double MAX_MINUTES  = 720.0;
    
    //these objects format values for currency and decimal rounding
    NumberFormat fmtCurrency = NumberFormat.getCurrencyInstance();
    DecimalFormat fmtDecimal = new DecimalFormat("#.##");
    
    //this constructor adds a number and name to the new MyPhone object
    public MyPhone (String custName, String phoneNumber) 
    {
        //sets the provided info to the class wide scope
        this.custName    = custName;
        this.phoneNumber = phoneNumber;
    }
    
    //this constructor allows a phone to be created
    //but without a name or number
    public MyPhone ()
    {
    }
    /********************************************
     * All access methods are below this comment*
     ********************************************/
    
    //returns number of texts sent
    public int getNumTexts()
    {
        return numOfTexts;
    }
    
    //returns battery life of the phone
    public double getBatteryLife()
    {
        return batteryLife;
    }
    
    //returns data used in MB
    public double getDataUsage()
    {
        return dataConsumed; 
    }
    
    /*************************************
     * All setters are below this comment*
     *************************************/
    
    //sets a new customer name
    public void setName(String custName)
    {
        this.custName = custName;
    }
    
    //sets the phone number, as long as it is valid
    //if not, sets the number to 9999999999
    public void setPhoneNumber(String phoneNumber)
    {   
        try{
        //tests if phone number is an integer
        double phoneNumberTest = Double.parseDouble(phoneNumber);
        if (phoneNumber.length() != 10 ) {
            JOptionPane.showMessageDialog(null, "This phone number is invalid");
            this.phoneNumber = "9999999999";
                } 
        else{
            this.phoneNumber = phoneNumber;  
                }
        }
        //the error is displayed elegantly
        //phone number is set to 9999999999
        catch(java.lang.NumberFormatException nonNumber){
            JOptionPane.showMessageDialog(null, "This phone number is invalid");
            this.phoneNumber = "9999999999";
        }
    }
    
    //charges the battery for a given time, this time cannot be negative.
    //Any time longer than it would take for the battery 
    //to fully charge returns batteryLife = 1
    public void chargeBattery(String chargeMinutes)
    { 
      try{
          //converts the string to an integer
          //containing a letter would error
          int chargeMinutesInt = Integer.parseInt(chargeMinutes); 
          if(chargeMinutesInt < 0 ) { //checks if number is negative
              JOptionPane.showMessageDialog(null, "You cannot charge for negative minutes");
            }
          else if (1.0 <= ((chargeMinutesInt / 120) + batteryLife)) {
              batteryLife = 100.0; //checks if overcharging occurs
              JOptionPane.showMessageDialog(null, "Your battery is at: " + (fmtDecimal.format(batteryLife)+"%"));
              batteryLife = 1.0;
            }
          else {
              batteryLife = ((chargeMinutesInt / 120.0) + batteryLife);
              batteryLife *= 100;
              JOptionPane.showMessageDialog(null, "Your battery is at: " + fmtDecimal.format(batteryLife)+"%");
              batteryLife /= 100;
              //if block warns the user to charge more if battery is low
              if (batteryLife <= .10) {
                  JOptionPane.showMessageDialog(null, "Your phone should continue charging, it's still low.");
               }
            }
      }
      //the error is displayed elegantly
      catch(NumberFormatException nonNumber) {
          JOptionPane.showMessageDialog(null, "Please enter a number.");
      }
    }
    
    //this method affects the battery and data usage of streaming
    //a negative number returns an error statement
    //if the audio drains the battery, only a portion is streamed
    //only a portion of data is charged to the account
    public void streamAudio(String audioMinutes)
    {
        //declare a variable for minutes while the battery is dead
        double minutesOver = 0.0;
        try {
            double audioMinutesDouble = Double.parseDouble(audioMinutes); 
            if (audioMinutesDouble < 0) {
                JOptionPane.showMessageDialog(null, "You cannot charge for negative minutes");
            }
            //this block runs if the user runs out of battery
            //it only adds the data used to the final statement
            else if (batteryLife - (audioMinutesDouble / (720)) <= 0) {
                JOptionPane.showMessageDialog(null, "You ran out of battery streaming music.");
                batteryLife = batteryLife - (audioMinutesDouble / (720));
                minutesOver = (Math.abs(batteryLife) * 720.0);
                JOptionPane.showMessageDialog(null, "You streamed music for " + (audioMinutesDouble - minutesOver) + "minutes.");
                JOptionPane.showMessageDialog(null, "Your battery level is now 0, please charge the device");
                batteryLife = 0.0;
                dataConsumed = dataConsumed + (audioMinutesDouble - minutesOver) * DATA_PER_MIN;
            }
            else {
                batteryLife = batteryLife - (audioMinutesDouble / (12.0* 60.0));
                batteryLife *= 100;
                JOptionPane.showMessageDialog(null, "Your battery is at: " + fmtDecimal.format(batteryLife) +"%");
                batteryLife /= 100;
                dataConsumed = dataConsumed + (audioMinutesDouble * DATA_PER_MIN);
                //if block below provides a warning if the battery is low
                if (batteryLife <= .10) {
                    JOptionPane.showMessageDialog(null, "Your phone needs to be charged.");
                }
            }
        }
       catch(java.lang.NumberFormatException nonNumber) {
            JOptionPane.showMessageDialog(null, "Please enter a number.");
        }
    }
    
    //shows a random received message using the 5 options below
    //increments numOfTexts after finishing
    public void readText()
    {
        
        Random randText = new Random();
        int randChoice = randText.nextInt(5);
        //these are the 5 messages possible, using a pop up window
        switch(randChoice) 
        {
            case 0:
                JOptionPane.showMessageDialog(null, "Text sent: Hey how's it going?");
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Text sent: How were your classes today?");
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Text sent: I'm so bored of this math professor right now.");
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Text sent: I got a 94 on that test I'm so happy!");
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Text sent: I only got 4 hours of sleep last night.");
                break;
                
        }
        numOfTexts += 1;
    }
    
    //shows a single sent text to the user in a pop-up window
    public void sendText()
    {
        JOptionPane.showMessageDialog(null, "Text received: Hey how are classes going?");
    }
    
    //prints the necessary information in the monthly statement
    //methods within this method are described below
    //after the statement is printed, a new month begins
    public void printStatement() 
    {
        //prints the monthly statement of the customer
        System.out.println("\n MyPhone Monthly Statement");
        System.out.println("Customer:             " + custName);
        System.out.println("Number:               " + fmtPhoneNumber());
        System.out.println("Texts:                " + numOfTexts);
        System.out.println("Data usage(GB):       " + fmtDecimal.format(dataConsumed / 1000));
        System.out.println("\n2GB Plan:             " + fmtCurrency.format(50.00));
        System.out.println("Additional data fee:  " + fmtCurrency.format(calcAdditionalDataFee()));
        System.out.println("Universal Usage (3%): " + fmtCurrency.format(calcUsageCharge()));
        System.out.println("Administrative fee:   " + fmtCurrency.format(0.61));
        System.out.println("Total Charges:        " + fmtCurrency.format(calcTotalFee()));

        startNewMonth();
    }
    
    /**************************************************************
     * All private methods used for the monthly statment are below*
     **************************************************************/
     
    //resets the texts sent and data usage back to 0
    private void startNewMonth() 
    {
        this.numOfTexts   = 0;
        this.dataConsumed = 0.0;
    }
    
    //determines the price of data if over 2 GB are used
    private double calcAdditionalDataFee()
    {
        //declare a working variable
        double dataGigs = 0.0;
        //convert to GB
        dataGigs = Math.ceil((dataConsumed / 1000));
        if (dataGigs > 2.0) {
            dataCost = ((dataGigs - 2) * 15) + 50;
            //cost if over data limit
        }
        else {
            dataCost = 50.0;
        }
        return dataCost - 50.0;
    }
    
    //finds and returns the usage charge
    private double calcUsageCharge() 
    {
        return dataCost * 0.03;
    }
    
    //finds and returns the total fee
    private double calcTotalFee()
    {   
        double adminFee = 0.61;
        return calcUsageCharge() + dataCost + adminFee;
    }
    
    //formats the phone number to (XXX)-XXX-XXXX
    private String fmtPhoneNumber()
    {
        String fmtPhoneNumber = "(" + phoneNumber.substring(0,3) + ")" + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6,10);
        return fmtPhoneNumber;
    }
    
}