
/**
 * Used to represent a customer in the store simulation
 *
 * @ Alex Porter
 * @ 11/27/2017
 */
public class Customer
{
    //represents time of arrival
    double arrivalTime = 0.0;
    //shows time the customer has been waiting
    double waitTime = 0.0;
    
    //creates a customer with the arrival time
    public Customer (double time) {
        this.arrivalTime = time;
    }
    
    //set methods are below
    public void setArrivalTime(double t) {
        this.arrivalTime = t;
    }
    
    public void setWaitTime(double t) {
        this.waitTime = t;
    }
    
    //this get method is used in the MarketPlace class
    public double getWaitTime() {
        return waitTime;
    }
    
    public double getArrivalTime() {
        return arrivalTime;
    }
    
    //used to add wait time to the customer if needed
    public void addWaitTime(double time) {
        this.waitTime += time;
    }
    
    //briefly tests the class
    public static void main(String[] args) {
        //write a brief main method to test this class
        Customer customer = new Customer(2.0);
        if(customer.getArrivalTime() != 2.0) {
            System.out.println("Error: Time constructor fail");
        }
        
        customer.setArrivalTime(3.0);
        if(customer.getArrivalTime() != 3.0) {
            System.out.println("Error: Set method fail");
        }
    }
}
