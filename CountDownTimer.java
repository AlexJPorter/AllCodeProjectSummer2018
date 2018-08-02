package project1;
import java.lang.Integer;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import static org.junit.Assert.assertTrue;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**********************************************************************
 * This class outlines the methods of a CountDownTimer. This class
 * simulates a countdown timer like one that would be used at
 * New Year's Eve downtown. Follow method and inline comments for 
 * more detailed explanations.
 * 
 * @author Alex Porter
 * @version 1/20/2018
 *********************************************************************/

public class CountDownTimer {

	/** Holds time in hours */
	private int hours;

	/** Holds time in minutes */
	private int minutes;

	/** Holds time in seconds */
	private int seconds;

	/** Halts all operations on timer instances if true */
	private static boolean suspendFlag = false;

	/******************************************************************
	 * Basic constructor sets internal values to 0
	 * 
	 * @param None
	 * @return None
	 *****************************************************************/
	public CountDownTimer () {
		hours = 0;
		minutes = 0;
		seconds = 0;
	}
	/******************************************************************
	 * A constructor that fills all 3 values based on parameters
	 * Throws exception if parameters are invalid (x>60 || x< 0)
	 * 
	 * @param hours
	 * @param minutes
	 * @param seconds
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public CountDownTimer (int hours, int minutes, int seconds) {
		//checks for invalid input
		if(!(hours < 0 || minutes < 0 || seconds < 0 
				|| minutes > 59 || seconds > 59)) {
			this.hours   = hours;
			this.minutes = minutes;
			this.seconds = seconds;
		}
		//throws error if invalid
		else
			throw new IllegalArgumentException();
	}
	/******************************************************************
	 * A constructor that takes minutes and seconds only as arguments
	 * Throws exception if parameters are invalid (x <0 || x> 60)
	 * 
	 * @param minutes
	 * @param seconds
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public CountDownTimer(int minutes, int seconds) {
		//set hours to 0 since it's not provided
		hours = 0;
		//check minutes and seconds inputs
		if(!(minutes < 0 || minutes > 59 || seconds < 0 
				|| seconds > 59)) {
			this.minutes = minutes;
			this.seconds = seconds;
		}
		//throws error if invalid
		else
			throw new IllegalArgumentException();
	}
	/******************************************************************
	 * A constructor that takes only seconds as a parameter
	 * Throws exception if parameter is invalid (x < 0 || x > 60)
	 * 
	 * @param seconds
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public CountDownTimer (int seconds) {
		//set hours and minutes to 0 since they aren't provided
		hours = 0;
		minutes = 0;
		//check if seconds is valid
		if(!(seconds > 59 || seconds < 0))
			this.seconds = seconds;
		//throws error if invalid
		else 
			throw new IllegalArgumentException();
	}
	/******************************************************************
	 * A constructor that takes a CountDownTimer object as a parameter
	 * Sets the object to internal values of all 0s
	 * 
	 * @param CountDownTimer other
	 *****************************************************************/

	public CountDownTimer(CountDownTimer other) {
		other = new CountDownTimer();
	}

	/******************************************************************
	 * A constructor that takes a string as a parameter. 
	 * and converts it to internal values.
	 * Invalid strings throw an error. 
	 * Takes seconds, seconds and minutes,
	 * or seconds minutes and hours in string form.
	 * 
	 * @param startTime
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public CountDownTimer(String startTime) {

		if(startTime.length() == 0) {
			//set all to 0 if empty string
			hours = 0;
			minutes = 0;
			seconds = 0;
		}
		else 
			//try block catches errors along the way and throws a final
			//error from the catch block
			try {
				//splits the string into an array of ints
				String[] inputArray = startTime.split(":");
				//if only seconds are given
				if(inputArray.length == 1) {
					int testSeconds = Integer.parseInt(inputArray[0]);
					//check if seconds is invalid, or a colon was left
					//at the end of the string
					if(testSeconds > -1 && testSeconds < 60 && 
							startTime.charAt(startTime.length()
									- 1) != ':') {
						hours = 0;
						minutes = 0;
						seconds = testSeconds;
					}
					//throw exception if invalid
					else
						throw new IllegalArgumentException();
				}
				//runs if minutes and seconds are given
				else if(inputArray.length == 2) {
					int testMinutes = Integer.parseInt(inputArray[0]);
					int testSeconds = Integer.parseInt(inputArray[1]);
					//check validity of minutes and seconds
					//also check for that pesky colon
					if(testMinutes > -1 && testSeconds > -1 && 
							testMinutes < 60 && testSeconds < 60 && 
							startTime.charAt(startTime.length()
									- 1) != ':') {
						hours = 0;
						minutes = testMinutes;
						seconds = testSeconds;
					}
					//throw exception if invalid
					else
						throw new IllegalArgumentException();
				}
				//runs if hours, minutes, and seconds are given
				else if(inputArray.length == 3) {
					int testHours = Integer.parseInt(inputArray[0]);
					int testMinutes = Integer.parseInt(inputArray[1]);
					int testSeconds = Integer.parseInt(inputArray[2]);
					//checks for validity (see above comments)
					if(testMinutes > -1 && testSeconds > -1 && 
							testMinutes < 60 && testSeconds < 60 && 
							testHours > -1 && startTime.charAt(
									startTime.length() - 1) != ':') {
						hours = testHours;
						minutes = testMinutes;
						seconds = testSeconds;
					}
					//throws error if invalid
					else
						throw new IllegalArgumentException();
				}
				//throws error if too many or too few arguments
				else
					throw new IllegalArgumentException();
			}
		//catches all errors and throws the proper error at the end
		//if a non-IllegalArg error occurs anywhere along the way,
		//it still throws the IllArg exception.
		catch(Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/******************************************************************
	 * Returns hours
	 * 
	 * @return hours
	 *****************************************************************/
	public int getHours() {
		return hours;
	}

	/******************************************************************
	 * Returns minutes
	 * 
	 * @return minutes
	 *****************************************************************/
	public int getMinutes() {
		return minutes;
	}

	/******************************************************************
	 * Returns seconds
	 * 
	 * @return seconds
	 *****************************************************************/
	public int getSeconds() {
		return seconds;
	}

	/******************************************************************
	 * Sets minutes to the value of i
	 * Throws exception if value is invalid
	 * 
	 * @param int i
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void setMinutes(int i) {
		if (i < 60 && i > -1)
			minutes = i;
		else
			throw new IllegalArgumentException();
	}

	/******************************************************************
	 * Sets hours to value of i
	 * Throws exception if input is invalid
	 * 
	 * @param int i
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void setHours(int i) {
		if(i > -1)
			hours = i;
		else
			throw new IllegalArgumentException();
	}

	/******************************************************************  
	 * Sets seconds to value of i
	 * Throws exception if input is invalid
	 * 
	 * @param int i
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void setSeconds(int i) {
		if(i < 60 && i > -1)
			seconds = i;
		else
			throw new IllegalArgumentException();
	}

	/******************************************************************
	 * Compares two objects internal values
	 * Returns true if the values are equal, returns false otherwise
	 * 
	 * @param Object other
	 * @return boolean
	 *****************************************************************/
	public boolean equals (Object other) {
		//check if object is null and a CountDownTimer
		//if not it returns false
		if(other != null) 
			if(other instanceof CountDownTimer) {
				CountDownTimer temp = (CountDownTimer) other;
				//returns true if equal
				return this.convertToSeconds() == 
						temp.convertToSeconds();
			}
			else
				return false;
		else
			return false;
	}
	/******************************************************************
	 * A static method that compares two objects 
	 * based on which has more time. Converts to seconds to compare.
	 * Returns true if the two values are equal
	 * 
	 * @param CountDownTimer t1
	 * @param CountDownTimer t2
	 * @return boolean
	 *****************************************************************/
	public static boolean equals (CountDownTimer t1, CountDownTimer t2)
	{		
		return t1.convertToSeconds() == t2.convertToSeconds();	
	}
	/******************************************************************
	 * Compares the 'this' object with the other object
	 * returns 1, 0, or -1 if "this" is greater, equal 
	 * or lesser respectively.
	 * 
	 * @param CountDownTimer other
	 * @return int 
	 *****************************************************************/
	public int compareTo(CountDownTimer other) {
		int difference = this.convertToSeconds() - 
				other.convertToSeconds();
		if (difference == 0)
			return 0;
		else if (difference > 0) 
			return 1;
		else
			return -1;
	}
	/******************************************************************
	 * A static method that compares two object parameters
	 * Returns 1, 0, or -1 if t1 >, =, or < t2 respectively
	 * 
	 * @param CountDownTimer t1
	 * @param CountDownTimer t2
	 * @return int
	 *****************************************************************/
	public static int compareTo(CountDownTimer t1, CountDownTimer t2) {
		int difference = t1.convertToSeconds() - t2.convertToSeconds();
		if (difference == 0)
			return 0;
		else if (difference > 0)
			return 1;
		else 
			return -1;
	}
	/******************************************************************
	 * Subtracts the given number of seconds from the this object
	 * Throws exception if parameter is invalid (x < 0)
	 * Or if number to be subtracted makes the object negative
	 * 
	 * @param seconds
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void sub(int seconds) {
		if(seconds >= 0) {
			if(!suspendFlag)
				//checks if subtracting makes the object negative
				if((this.convertToSeconds() - seconds) < 0) 
					throw new IllegalArgumentException();
				else {
					this.seconds = this.convertToSeconds();
					this.seconds -= seconds;
					this.convertToStandard();
				}
		}
		else
			throw new IllegalArgumentException();
	}
	/******************************************************************
	 * Subtracts the seconds value of the other object
	 * from the this object
	 * Throws exception if other object is invalid
	 * Or if other object makes 'this' object negative
	 * 
	 * @param CountDownTimer other
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void sub(CountDownTimer other) {
		if(other != null)
			sub(other.convertToSeconds());
		else
			throw new IllegalArgumentException();

	}
	/******************************************************************
	 * Subtracts one second from the this object
	 * Does nothing if decrementing makes the object negative
	 * Uses the private conversion methods to help with this
	 *****************************************************************/
	public void dec() {
		if(!suspendFlag) {
			seconds = this.convertToSeconds();
			if(seconds - 1 < 0) {	
				this.convertToStandard();
			}
			else {
				seconds -= 1;
				this.convertToStandard();
			}
		}
	}
	/******************************************************************
	 * Adds the given number of seconds to the this object
	 * Throws exception if parameter is negative. 
	 * 
	 * @param int seconds
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void add(int seconds) {
		if(seconds >= 0) {
			if(!suspendFlag) {
				this.seconds = convertToSeconds();
				this.seconds += seconds;
				this.convertToStandard();
			}
		}
		else
			throw new IllegalArgumentException();

	}
	/******************************************************************
	 * Adds the value of the other object to the this object
	 * Throws exception if other object is invalid or null
	 * 
	 * @param CountDownTimer other
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void add(CountDownTimer other) {
		if(other != null)
			add(other.convertToSeconds());
		//suspend flag is checked on add method		
		else
			throw new IllegalArgumentException();
	}
	/******************************************************************
	 * Adds one second to the this object
	 *****************************************************************/
	public void inc() {
		if(!suspendFlag) {
			seconds = this.convertToSeconds() + 1;
			convertToStandard();
		}
	}
	/******************************************************************
	 * Returns the object values in a string form
	 * 
	 * @return String 
	 *****************************************************************/
	public String toString() {
		String result = hours + ":";

		//add 0 if not double digits
		if(minutes < 10) 
			result += "0";

		result += minutes + ":";

		//add 0 if not double digits	
		if(seconds < 10)
			result += "0"; 

		result += seconds;

		return result;
	}
	/******************************************************************
	 * Loads the object's data from a file
	 * File name is specified in the save method
	 * Throws an error if the file isn't found
	 * 
	 * @param fileName
	 * @throws IllegalArgumentException
	 *****************************************************************/
	public void load(String fileName) {
		int someInt;
		if(!suspendFlag)
			try{
				// open the data file
				Scanner fileReader = new Scanner(new File(fileName));
				// read hours int
				someInt = fileReader.nextInt();
				this.hours = someInt;
				//read minutes int
				someInt = fileReader.nextInt();
				this.minutes = someInt;
				//read seconds int
				someInt = fileReader.nextInt();
				this.seconds = someInt;
				//close the fileReader
				fileReader.close();
			}
		// problem reading the file
		catch(Exception error){
			throw new IllegalArgumentException();
		}
	}
	/******************************************************************
	 * Saves the object's data to a file with name 'fileName'
	 * This is accessed by the load method later
	 * 
	 * @param String fileName
	 *****************************************************************/
	public void save(String fileName) {
		//create object that writes to file
		PrintWriter out = null;
		try {
			//attempt to create the printwriter
			out = new PrintWriter(new BufferedWriter(new 
					FileWriter(fileName)));
		}
		//runs if invalid input
		catch (Exception e) {
			throw new IllegalArgumentException();
		}
		//prints hours, minutes, then seconds to the file
		//closes the file after
		out.println(this.hours);
		out.println(this.minutes);
		out.println(this.seconds);
		out.close();
	}
	/******************************************************************
	 * Suspends changes on all CountDownTimer instances if true
	 * 
	 * @param Boolean flag
	 *****************************************************************/
	public static void suspend(Boolean flag) {
		suspendFlag = flag;
	}

	/******************************************************************
	 * Returns the static boolean suspendFlag
	 * 
	 * @return boolean suspendFlag
	 *****************************************************************/
	public static boolean getFlag() {
		return suspendFlag;
	}

	/******************************************************************
	 * Returns the value of the object in seconds
	 * 
	 * @return seconds
	 *****************************************************************/
	private int convertToSeconds() {
		int toReturn = 0;
		//convert hours, then minutes, then adds seconds to toReturn
		toReturn += (getHours() * 60 * 60);
		toReturn += (getMinutes() * 60);
		toReturn += getSeconds();
		return toReturn;
	}
	/******************************************************************
	 * Converts the hours and minutes back from seconds
	 * or, Reverses the convertToSeconds method
	 *****************************************************************/
	private void convertToStandard() {
		//takes advantage of integer division to get whole minutes
		minutes = (seconds / 60);
		//set hours using integer division after minutes are found
		hours = (minutes / 60);
		//take away the minutes and seconds that were converted
		seconds -= 60 * minutes;
		minutes -= 60 * hours;

	}
	/******************************************************************
	 * Provides a short testing suite of the CountDownTimer methods
	 * 
	 * @param String[] args
	 *****************************************************************/
	public static void main(String[] args) {

		CountDownTimer s = new CountDownTimer("2:59:08");
		System.out.println("Time: " + s);

		s = new CountDownTimer("20:08");
		System.out.println("Time: " + s);

		s = new CountDownTimer("8");
		System.out.println("Time: " + s);


		CountDownTimer s1 = new CountDownTimer(25, 2, 20);
		System.out.println("Time: " + s1);

		s1.sub(1000);
		System.out.println("Time: " + s1);

		s1.add(1000);
		System.out.println("Time: " + s1);

		for(int i = 1000; i > 0; i--) {
			s1.inc();
		}
		System.out.println("Time: " + s1);

		s1 = new CountDownTimer(25, 25);
		System.out.println("Time: " + s1);

		s1 = new CountDownTimer(25);
		System.out.println("Time: " + s1);

		s1 = new CountDownTimer();
		System.out.println("Time: " + s1);

		s1 = new CountDownTimer("");
		System.out.println("Time" + s1);

		CountDownTimer objInit = new CountDownTimer(1,1,1);
		s1 = new CountDownTimer(objInit);
		System.out.println(s1);

		CountDownTimer s2 = new CountDownTimer(40, 10, 20);
		s2.sub(100);
		for (int i = 0; i < 4000; i++) {
			s2.dec();
		}
		System.out.println("Time: " + s2);

		s1.add(s2);
		System.out.println("Time: " + s1);

		suspend(true);

		s1.add(10);
		System.out.println("Time: " +s1);

		s1.dec();
		System.out.println("Time: " +s1);

		s1.inc();
		System.out.println("Time: " +s1);

		s1.sub(10);
		System.out.println("Time: " +s1);

		s1.add(s1);
		System.out.println("Time: " +s1);

		s1.sub(s1);
		System.out.println("Time: " +s1);

		CountDownTimer greater = new CountDownTimer("50:00:00");
		CountDownTimer lesser = new CountDownTimer("49:00:00");
		CountDownTimer lesser2 = new CountDownTimer("49:00:00");

		System.out.println(lesser.compareTo(greater));
		System.out.println(greater.compareTo(lesser));
		System.out.println(lesser.compareTo(lesser2));

		System.out.println(compareTo(lesser, greater));
		System.out.println(compareTo(greater,lesser));
		System.out.println(compareTo(lesser,lesser2));

		System.out.println(equals(lesser,lesser2));
		System.out.println(equals(greater,lesser));	

		CountDownTimer s4 = new CountDownTimer (5,01,30);
		s4.sub(2000);
		System.out.println(s4);

		CountDownTimer s5 = new CountDownTimer (3,45,27);
		CountDownTimer s6 = new CountDownTimer ("3:45:27");

		s5.save("saveIt");
		s5 = new CountDownTimer ();  // resets to zero

		s5.load("saveIt");
		assertTrue (s5.equals(s6));	
	} 
}