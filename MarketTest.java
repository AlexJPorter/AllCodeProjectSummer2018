
/**
 * This class tests the MarketPlace simulation
 *
 * @Alex Porter
 * @11/27/2017
 */
public class MarketTest
{
    public static void main(String[] args) {
        MarketPlace testing = new MarketPlace();
        
        //check initial values
        assert(testing.getNumCashiers() == 3) : "Error: Intial cashier value not 3";
        
        assert(testing.getArrivalTime() == 2.5) : "Error: Time between customers not 2.5";
        
        assert(testing.getServiceTime() == 6.6) : "Error: Cashier time is not 6.6";
                
        assert(testing.getNumCustomersServed() == 0) :"Error: Customers served is not 0";
        
        assert((testing.getReport().equals(""))) : "Error: Report initial value is not empty";
        
        assert(testing.getLongestLineLength() == 0) : "Error: Initial line length is not 0";
        
        assert(testing.getAverageWaitTime() == 0) : "Error: Initial avg wait time is not 0";
        
        //check set parameters method
        testing.setParameters(5, 4.0, 3.0, false);
        
        assert(testing.getNumCashiers() == 5) : "Error2: Intial cashier value not 5";
        
        assert(testing.getArrivalTime() == 3.0) : "Error2: Time between customers not 3.0";
        
        assert(testing.getServiceTime() == 4.0) : "Error2: Cashier time is not 4.0";
        
        //check time format
        assert(testing.formatTime(722).equals("12:02pm"));
        
        testing.startSimulation();
        
        // how many customers served with default arrival time
        int before = testing.getNumCustomersServed();
        
        //also get a value for wait time
        double wait1 = testing.getTotalWaitTime();
             
        // change parameters for faster arrival time
        testing.setParameters(3, 6.6, 2.0, false);

        //how many customers served with quicker arrival times?
        testing.startSimulation();
        int after = testing.getNumCustomersServed();
        assert(before < after) : "Should be more customers";
        
        //should be less wait with 10 cashiers
        testing.setParameters(10, 6.6, 2.5, false);
        testing.startSimulation();
        double wait2 = testing.getTotalWaitTime();
        assert(wait1 < wait2) : "Should be longer wait with less cashiers";
        
        //should be more wait with longer service
        testing.setParameters(3, 10, 2.5, false);
        testing.startSimulation();
        double wait3 = testing.getTotalWaitTime();
        assert(wait3 > wait1) : "Should be longer wait with longer service time";
        
        //check reset values
        testing.setParameters(4, 10, 2.5, false);
        testing.startSimulation();
        assert(testing.getNumCashiers() == 4) : "Cashiers should not be reset";
        assert(testing.getServiceTime() == 10) : "Service time should not be reset";
        assert(testing.getArrivalTime() == 2.5) : "Arrival time should not be reset";
        
        
        
        
    }
}
