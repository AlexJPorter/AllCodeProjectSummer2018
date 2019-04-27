
/**
 * Defines the methods used for a simulation of a store
 *
 * @ Alex Porter
 * @ 11/27/2017
 */
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.text.DecimalFormat;
public class MarketPlace
{
   //indicates customer arrival interval
   private double tBetweenCusts = 0.0;
   
   //indicates cashier serivice time
   private double cashServiceTime = 0.0;
   private int numCashiers = 3;
   private boolean dispCheckoutArea = false;
   
   //used to calculate number of customers served
   private int numCustomersServed = 0;
   
   //used for line length data
   private int longestLineLength = 0;
   private String longestLineTime = "";
   
   //used for wait time data
   private double avgWaitTime = 0.0;
   private double totalWaitTime = 0.0;
   
   //sets open and close to 10am and 6pm respectively
   private final int OPEN = 600;
   private final int CLOSE = 1080;
   
   //sets the currentTime to open
   private double currentTime = OPEN;
   
   //creates a list to represent customers in line, and cashiers
   private ArrayList<Customer> customersInLine = new ArrayList<Customer>();
   private Customer[] cashiersWorking = new Customer[numCashiers];
   
   //declares arrival and departure events
   GVdeparture customerDeparture;
   GVarrival customerArrival;
   
   //creates the event list
   private PriorityQueue<GVevent> eventList = new PriorityQueue();
   
   //defines a random object for futureRandomTime()
   GVrandom rnd = new GVrandom();
   
   //creates a results string that will be printed
   private String results = "";
   
   //several other instance variables to track and calculate simulation results
   
   //constructor sets initial values for variables
   public MarketPlace() {
       tBetweenCusts = 2.5;
       cashServiceTime = 6.6;
       numCashiers = 3;
       dispCheckoutArea = false;
       numCustomersServed = 0;
       longestLineLength = 0;
       avgWaitTime = 0;
   
   
       currentTime = OPEN;
       customersInLine = new ArrayList<Customer>();
       cashiersWorking = new Customer[numCashiers];
   
       eventList = new PriorityQueue();
   
       rnd = new GVrandom();
   
       results = "";
    }
   
   //below are all accessor methods
   public int getNumCashiers() {
      return numCashiers;  
   }
   
   public double getArrivalTime() {
       return tBetweenCusts;
   }
   
   public double getServiceTime() {
       return cashServiceTime;
   }
   
   public int getNumCustomersServed() {
       return numCustomersServed;
   }
   
   public String getReport() {
       return results;
   }
   
   public int getLongestLineLength() {
       return longestLineLength;
   }
   
   public double getAverageWaitTime() {
       return avgWaitTime;
   }
   
   public double getTotalWaitTime() {
       return totalWaitTime;
   }
   
   //this set method is used for all parameters
   public void setParameters(int num, double s, double a, boolean ck) {
       numCashiers = num;
       cashServiceTime = s;
       tBetweenCusts = a;
       dispCheckoutArea = ck;
   }
   
   //represents a customer getting in line
   public void customerGetsInLine() {
       //future time is based on a 45 minute time inside the store shopping
       Customer newCustomer = new Customer(randomFutureTime(tBetweenCusts));
       //customer is added to the queue
       customersInLine.add(newCustomer);
       
       //checks if this is the longest line seen
       if(customersInLine.size() > longestLineLength) {
           longestLineLength = customersInLine.size();
           longestLineTime = formatTime(currentTime);
       }
       
       //moves the customer to the cashier if there is a customer
       //and if there is a cashier available
       int cashIndex = cashierAvailable();
       if(customersInLine.size() > 0 && (cashIndex != -1)) {
           customerToCashier(cashIndex);
       }
       
       else { 
           for (Customer customer : customersInLine) {
               customer.addWaitTime(customerDeparture.getTime() - currentTime);
           }
        }
       
       //generates a new customer arrival if they would arrive before close
       if(currentTime + tBetweenCusts < CLOSE) {
           customerArrival = new GVarrival(this, randomFutureTime(tBetweenCusts));
           eventList.add(customerArrival);
       }
   }
    
   public void customerPays(int num) {
       //moves the next customer to the now empty cashier
       customerToCashier(num);
       //move the next person in line
   }
   
   public void startSimulation() {
       //resets the values for the sim
       reset();
       //creates a customer arriving right at opening
       //adds the customer to the eventList
       customerArrival = new GVarrival(this, OPEN);
       eventList.add(customerArrival);
       
       //removes most recent event from list and adds data about the checkout area 
       while(!eventList.isEmpty()) {
           GVevent e = eventList.poll();
           currentTime = e.getTime();
           e.process();
           if(dispCheckoutArea) {
               showCheckoutArea();
           }
       }
       //prints all the data added over the simulation
       createReport();
   }
   
   //creates a formatted string for the time in minutes provided
   public String formatTime(double mins) {
       //uses integer division to get desired whole numbers
       int hours = (int) mins / 60;
       int minutes = (int) mins % 60;
       
       //initialize strings for minutes and am or pm
       String sMinutes = "";
       String timeSuffix = "";
       
       if(hours >= 12) {
           //set the time to pm
           timeSuffix += "pm";
           //if any time over twelve then remove 12 to avoid military time
           if(hours != 12) {
               hours -= 12;
           }
       }
       //else just add am
       else {
           if(hours == 0) {
               hours += 12;
            }
           timeSuffix += "am";
       }
       
       //calculates the minutes
       if(minutes < 10) {
           //if under 10 a 0 needs to be added
           sMinutes += "0" + minutes;
       }
       else {
           //else minutes are simply added
           sMinutes += "" + minutes;
        }
       //string is assembled and returned
       String formattedTime = hours + ":" + sMinutes + timeSuffix;
       return formattedTime;
   }
   
   private void showCheckoutArea() {
       //first adds a time for the output
       String time = formatTime(currentTime);
       
       //if the cashier is not occupied, a "-" is printed
       //else a C is printed
       String cashiers = "";
       for(int i = 0; i < cashiersWorking.length; i++) {
           if(cashiersWorking[i] == null) {
               cashiers += "-";
           }
           else {
               cashiers += "C";
            }
       }
       
       //adds a star for each person in line
       String line = "";
       for(int i = 0; i < customersInLine.size(); i++) {
           line += "*";
       }
       
       //adds all of these to the results
       results += time + " " + cashiers + line + "\n";
    }
    
   private void createReport() {
       //calculate the average wait time
       avgWaitTime = totalWaitTime / (double)numCustomersServed;
       
       //generate the first section of the results output
       String simulationParameters = "SIMULATION PARAMETERS \nNumber of cashiers: " + numCashiers + "\nAverage arrival: " 
       + tBetweenCusts + "\nAverage service: " + cashServiceTime + "\n";
       
       //generate the second part of the results screen
       results += simulationParameters + "\nRESULTS\n" +  "Average wait time: " + formatWaitTime(avgWaitTime) + "\nMax line length: " + longestLineLength + " at " + longestLineTime
       + "\nCustomers served: " + numCustomersServed + "\nLast Departure: " + formatTime(currentTime); 
   }
    
   private void reset() {
       //reset instance variables
       numCustomersServed = 0;
       longestLineLength = 0;
       avgWaitTime = 0;
   
       //set the time, and create new/empty lists
       currentTime = OPEN;
       customersInLine = new ArrayList<Customer>();
       cashiersWorking = new Customer[numCashiers];
   
       eventList = new PriorityQueue();
   
       rnd = new GVrandom();
   
       results = "";
   }
   
   //used to determine if a cashier is available
   private int cashierAvailable() {
       int availCash = -1;
       for(int i = 0; i < cashiersWorking.length; i++) {
           if(cashiersWorking[i] == null) {
               availCash = i;
               //returns cashier index if available
               return availCash;
            }
       }
       //returns -1 if no cashier is available
       return availCash;
   }
   
   //generates a random future time given an average time
   private double randomFutureTime(double avg) {
       GVrandom rand = new GVrandom();
       double future = currentTime + rand.nextPoisson(avg);
       return future;
   }
   
   //moves the customer to the cashier, if any
   private void customerToCashier(int cashIndex) {
       //this clears the cashier, which solves
       //a problem out of the scope of this class
       cashiersWorking[cashIndex] = null;
       //customer is moved only if there is a customer
       if(customersInLine.size() != 0) {
          //sets the cashier index to the customer
          cashiersWorking[cashIndex] = customersInLine.get(0);
          //adds the wait time for avg to be calculated later
          totalWaitTime += customersInLine.get(0).getWaitTime();
          //removes the first person in line from the list
          customersInLine.remove(0);
          //increments number of customers served
          numCustomersServed += 1; 
          //creates and adds new departure event
          //results += "Departure generated";
          customerDeparture = new GVdeparture(this, randomFutureTime(cashServiceTime), cashIndex);
          eventList.add(customerDeparture);
       }
   }
   
   private String formatWaitTime(double mins) {
       DecimalFormat fmtTime = new DecimalFormat("#.# mins");
       return fmtTime.format(mins);
    }
}












