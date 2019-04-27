
/**
 * Write a description of class BabyName here.
 *
 * @author Alec Porter
 * @version (a version number or a date)
 */
import java.lang.Comparable;
import java.util.ArrayList;
public class BabyName implements Comparable
{
    private String babyName    = "";
    private boolean babyGender = false;
    private int numBabiesYear  = 0;
    private int babyBirthYear  = 0;
    
    public BabyName(String n, boolean g, int count, int year) 
    {
        //changes parameters to understandable variable names
        babyName      = n;
        babyGender    = g;
        numBabiesYear = count;
        babyBirthYear = year;
    }
    
    //checks if baby is female, if so returns true
    public boolean isFemale() 
    {
       return babyGender;
    }
    
    //returns the name string of the name element
    public String getName() 
    {
        return babyName;
    }
    
    //returns number of babies with given name
    public int getCount() 
    {
        return numBabiesYear; 
    }
    
    //returns the birth year of the baby element
    public int getYear() 
    {
        return babyBirthYear;
    }
    
    //sets the name to a new name
    public void setName(String babyName) 
    {
        this.babyName = babyName;
    }
    
    //sets the num of babies born to a new count
    public void setCount(int count)
    {
        this.numBabiesYear = count;
    }
    
    //sets the year of an entry to a new year
    public void setYear(int year)
    {
        this.babyBirthYear = year;
    }
    
    //returns a formatted string for printing to the GUI
    public String toString() 
    {
        String calc = "";
        if (babyGender) {
            calc += "girls";
        }
        else {
            calc += "boys";
        }
        return ("" + numBabiesYear + " " + calc + " named " + babyName + " in " + babyBirthYear + "\n");
    
    }
    
    //used by the sort() method in BabyNameDatabase
    //sorts objects by their counts from most [0] to least [n]
    public int compareTo(Object other){
        BabyName b = (BabyName) other;
        return (b.numBabiesYear - numBabiesYear);
    }
    
    //a short test suite for the methods in this class
    public static void main(String args[]) 
    {
        //initialize an object to work with
        BabyName testing = new BabyName("Jenna", true, 200, 1990);
        
        //change the name using setName()
        testing.setName("Jennifer");
        
        //check name change and getName method
        System.out.println("Name change Jenna to Jennifer Name:" + testing.getName());
        
        //change count using setCount
        testing.setCount(201);
        
        //check setCount and getCount
        System.out.println("Count change from 200 to 201 Count: " + testing.getCount());
        
        //change year using setYear()
        testing.setYear(1991);
        
        //check setYear and getYear
        System.out.println("Year change from 1990 to 1991 Year: " + testing.getYear());
        
        //check the isFemale() method, should return true
        System.out.println("Is Jennifer a female? " + testing.isFemale());
        
        //prints the formatted string of the object, toString called implicitly
        System.out.println("Formatted object string: " + testing);
    }
}
