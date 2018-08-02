
/**
 * Simulates a departure from the store
 *
 * @Alex Porter
 * @11/27/2017
 */
public class GVdeparture extends GVevent
{
    //used to clear the cashier in the customer pays method
    int cashID = 0;
    //used to calculate the wait time for a customer
    double time = 0.0;
    
    //sets the departure to a store, at a time, from a cashier index
    public GVdeparture(MarketPlace store, double time, int id) {
        //invoke the base class constructor to set the MarketPlace object, event time,
        //and cashier ID
        super(store, time, id);
        //cashier id is used to clear the cashier
        cashID = id;
        //time is used to find wait time in customertoCashier
        this.time = time;
    }
    
    //accesses the time to calc wait time
    public double getTime() {
        return time;
    }
    
    //runs when departure is processed
    public void process() {
        //provides an empty cashier for the next customer to move to
        //store.results += "Departure processed.\n";
        //this solves all the problems
        //the following code solves the problem
        //store.cashiersWorking[cashID] = null;
        store.customerPays(cashID);
        //it should be the cashier that the customer is leaving from, which is the ID above
    }
}
