
/**
 * Describes the methods used by the BabyNamesGUI class
 *
 * @author Alex Porter
 * @version 11/8/2017
 */
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;
public class BabyNamesDatabase
{
    ArrayList<BabyName> nameList = new ArrayList<BabyName>();
    //spec says to put this up here as well as the constructor
    public BabyNamesDatabase  () 
    {
        ArrayList<BabyName> nameList = new ArrayList<BabyName>();
    }
    
    public void readBabyNameData(String filename) 
    {
            try{ 
    
            // open the text file and use a Scanner to read the text
            FileInputStream fileByteStream = new FileInputStream(filename);
            Scanner scnr = new Scanner(fileByteStream);
            scnr.useDelimiter("[,\r\n]+");
               
            // keep reading as long as there is more data
            while(scnr.hasNext()) {
                // FIX ME: read the name, gender, count and year
                String name = scnr.next();
                String gender = scnr.next();
                int count = scnr.nextInt();
                int year = scnr.nextInt();
    
                // FIX ME: assign true/false to boolean isFemale based on
                // the gender String
                boolean isFemale = false;
                if(gender.equalsIgnoreCase("f")) {
                    isFemale = true;
                }
    
    
                // FIX ME: instantiate a new Baby Name and add to ArrayList
                BabyName entry = new BabyName(name, isFemale, count, year);
                nameList.add(entry);
    
                
            }
            fileByteStream.close();
        }
        catch(IOException e) {
            System.out.println("Failed to read the data file: " + filename);
        }
    }
    
    //returns the number of elements in the Array
    public int countAllNames() 
    {
        return nameList.size();
    }
    
    //returns the amount of girls in the array
    public int countAllGirls()
    {
        int girlsCount = 0;
        for (BabyName name : nameList) {
            if (name.isFemale()) {
                //all girls, not just unique names
                girlsCount += name.getCount();
            }
        }
        return girlsCount;
    }
    
    //returns the amount of boys in the array
    public int countAllBoys()
    {
        int boysCount = 0;
        for (BabyName name: nameList){
            if(!name.isFemale()) {
                //all boys, not just unique names
                boysCount += name.getCount();
            }
        }  
        return boysCount;
    }
    
    //returns the most popular girl in a given year
    public BabyName mostPopularGirl(int year) 
    {
        //creates an object to store the max
        //"true" implies the name is female
        BabyName currentChecking = new BabyName("Initial",true,0,year);
        for (BabyName name : nameList) {
            //parses through entire list, checks if female and year is valid
            if (name.isFemale() && name.getYear() == year) {
                if(name.getCount() > currentChecking.getCount()) {
                    currentChecking = name;
                    //changes currentChecking to current name if it has a higher count
                }
            }
        }
        //returns max
        return currentChecking; 
    }
    
    //returns most popular boy in a given year
    //see above comments for explanation
    public BabyName mostPopularBoy(int year) 
    {
        BabyName currentChecking = new BabyName("Initial",false,0,year);
        for (BabyName name : nameList) {
            if (!name.isFemale() && name.getYear() == year) {
                if(name.getCount() > currentChecking.getCount()) {
                    currentChecking = name;
                }
            }
        }
        return currentChecking;
    }
    
    //returns all years and genders with a given name
    public ArrayList <BabyName> searchForName(String searchName)
    {
        //create a list to store the result
        ArrayList<BabyName> searchResult = new ArrayList<BabyName>();
        for(BabyName name : nameList) {
            //for each name, if name equals parameter ignore case, add it to the list
            if (name.getName().equalsIgnoreCase(searchName)) {
                searchResult.add(name);
            }
        }
        //sorts the result before returning for easy reading
        Collections.sort(searchResult);
        return searchResult;
    }
    
    //returns all unique names in a given year
    public ArrayList <BabyName> searchForYear(int year)
    {
        //create a list to store the result
        ArrayList<BabyName> searchResult = new ArrayList<BabyName>();
        for(BabyName name : nameList) {
            if(name.getYear() == year) {
                //if year given is equal, add it to the list
                searchResult.add(name);
            }
        }
        //sort the list before returning
        Collections.sort(searchResult);
        return searchResult;
    }
    
    //returns the top ten most common names for a given year
    public ArrayList <BabyName> topTenNames(int year)
    {
        //create a list to store the result
        ArrayList<BabyName> topTenResult = new ArrayList<BabyName>();
        //add the last 10 elements to the list to fill it with initial values
        for (int i = 270; i < 280; i++) {
            topTenResult.add(nameList.get(i));
        }
        //sort the list so the lowest # of births is element 9
        Collections.sort(topTenResult);
        //go through each name in the list
        for(BabyName name: nameList) {
            //check if the name has the same year, and is at least larger than the last element
            if(name.getYear() == year && topTenResult.get(9).getCount() < name.getCount()) {
                //if so, replace the last/least element
                topTenResult.set(9, name);
                //re-sort the list so the lowest is at the last position again
                Collections.sort(topTenResult);
            }
        }
        return topTenResult;
    }
}
