import java.util.ArrayList;

/**
 * Tests the methods within the BabyNamesDatabase class
 *
 * @ Alex Porter
 * @ 11/6/2017
 */
public class BabyNamesTesting
{
    public static void main(String[] args) {
            BabyNamesDatabase db = new BabyNamesDatabase();
            // read small data file created just for testing
            db.readBabyNameData("BabyNamesTesting.txt");
            // check number of records
            if(db.countAllNames() != 15){
                System.out.println("Error: Number of unique names should be 15");
            }
            
            //count the girls in all years
            if(db.countAllGirls() != 12072) {
                System.out.println("Error: Number of girls should be 12072");
            }
            
            //count the boys in all years
            if(db.countAllBoys() != 2575) {
                System.out.println("Error: Number of boys should be 2575");
            }
            
            // check most popular boy
            BabyName popularB = db.mostPopularBoy(1880);
            String nameB = popularB.getName();
            if(nameB.equals("Fredrick") == false){
                System.out.println("Error: Popular boy in 1880 should be Fredrick");
            }
            
            //check the most popular girl
            BabyName popularG = db.mostPopularGirl(1880);
            String nameG = popularG.getName();
            if(nameG.equals("Jenna") == false) {
                System.out.println("Error: Popular girl in 1880 should be Jenna");
                System.out.println(nameG);
            }
           
            // check number of records for one year
            ArrayList<BabyName> tempList = db.searchForYear(1880);
        
            if(tempList.size() != 11){
                System.out.println("Error: Should be 11 records in 1880");
            }
            //clear the templist 
            tempList.clear();
            
            //check the top ten list method
            tempList = db.topTenNames(1990);
            //check if improper size
            if(tempList.size() != 10) {
                System.out.println("Error: Improper size of Array");
            }
            //check if they are out of order
            for (int i = 0; i<=8; i++) {
                if(!(tempList.get(i).getCount() > tempList.get(i+1).getCount())) {
                    System.out.println("Error: Elements out of order");
                }
            }
            //clear the templist
            tempList.clear();
            
            //check the name searching method
            tempList = db.searchForName("John");
            //check if # of names is wrong
            if(tempList.size() != 1) {
                System.out.println("Error: Name search should have size 1");
            }
            
            //check if wrong name is added for element
            if(tempList.get(0).getName().equals("John") == false ) {
                System.out.println("Error: Wrong name added to list");
            }
            
            System.out.println("Scanning complete.");
    }
}
