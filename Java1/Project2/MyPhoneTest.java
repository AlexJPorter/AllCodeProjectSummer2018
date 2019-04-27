/**
 * This class creates three phones, Alex's phone, Evan's phone, and Bob's phone
 * 
 * Alex will use the phone accurately, Evan will use the phone more, 
 * and Bob will imput inaccuarte values to check for error catching
 * 
 * Author: Alex Porter
 * Version 9/2017
 */

public class MyPhoneTest
{
public static void main(String [] args) 
    {
        // MyPhone test = new MyPhone("Test Tester", "6168675309");
        
        // test.chargeBattery("60");
        // test.streamAudio("120000");
        
        // test.printStatement();
        // //create the two phones with valid names and numbers 
        MyPhone alexPhone = new MyPhone("Alex", "6162593446");
        // MyPhone evanPhone = new MyPhone("Evan", "6162600162");
        
        // //creates a third phone that will be set later
        // MyPhone bobPhone  = new MyPhone();
        
        
        // //tests the name and phone number setter methods
        // alexPhone.setName("Alexander");
        // alexPhone.setPhoneNumber("6162600612");
        
        // //charges the battery fully, and streams audio so battery = 0
        // alexPhone.chargeBattery("120");
        // alexPhone.streamAudio("720");
        
        // //charges the battery over max needed
        // alexPhone.chargeBattery("200");
        // //streams until battery is almost 0, gives a low battery warning
        // alexPhone.streamAudio("710");
        
        // //tests getting random texts
        // alexPhone.readText();
        // alexPhone.readText();
        // alexPhone.readText();
        // alexPhone.readText();
        // alexPhone.readText();
        
        // //tests sending a text and increments text counter
        // alexPhone.sendText();
        // alexPhone.sendText();
        
        // //prints the statement. Data will be < 2gb
        // alexPhone.printStatement();
        
        // //tests charging the phone a little, which gives a warning
        alexPhone.chargeBattery("5");
        
        // //streams more than possible with the battery
        // //only streams 720 minutes and charges data accordingly
        // evanPhone.chargeBattery("120");
        // evanPhone.streamAudio("721");
        // evanPhone.chargeBattery("120");
        
        // //streams audio for more than 2 gbs
        // evanPhone.streamAudio("700");
        // evanPhone.chargeBattery("120");
        // evanPhone.streamAudio("700");
        // evanPhone.chargeBattery("120");
        // evanPhone.streamAudio("700");
        // evanPhone.chargeBattery("120");
        
        // //prints monthly statement, data > 2gbs
        // evanPhone.printStatement();
        
        // //this checks errors 
        // //sadly, even numbers could be a valid name
        // bobPhone.setName("5555555555");
        
        // //error of characters other than numbers
        // //error of more than 10 digits
        // bobPhone.setPhoneNumber("Bob");
        // bobPhone.setPhoneNumber("12345678901");
        
        // //error for negative time
        // //messages should be sent for each
        // bobPhone.streamAudio("-9");
        // bobPhone.chargeBattery("-9");
        
        // //error for containing letters or symbols
        // bobPhone.streamAudio("till it's empty");
        // bobPhone.chargeBattery("till it's full");
        
        
    }
}