

/**
 * PowerSet: A class that generates all the elements of
 * a PowerSet of integers
 * @author Christian Trefftz
 * @version Version 0.1 January 16 / 2019
 */

import java.util.TreeSet;

public class PowerSet
{
    
    private int n;
    private int twoToN;
    private int currentSet;

    /**
     * Constructor for objects of class PowerSet
     */
    public PowerSet(int n)
    {
        this.n = n;
        twoToN = 1;
        for(int i = 0; i < n;i++) {
            twoToN = twoToN * 2;
        }
        currentSet = 0;
    }

    
    public boolean hasNext()
    {
      
        return currentSet < twoToN;
    }
    
    public TreeSet < Integer > next() {
        TreeSet < Integer > result = new TreeSet < Integer > ();
        int mask = 1;
        for(int i = 0;i < n;i++) {
            if ( (mask & currentSet) != 0 ) {
                result.add(i);
            }
            mask = mask*2;
        }
        currentSet++;
        return result;
    }
}