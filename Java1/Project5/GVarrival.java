
/**
 * Simulates a customer arrival event
 *
 * @Alex Porter
 * @11/27/2017
 */
public class GVarrival extends GVevent
{
    public GVarrival (MarketPlace store, double time) {
        super(store, time);
    }
    
    public void process() {
        //customer gets in line after a random amount of time shopping
        store.customerGetsInLine();
    }
}
