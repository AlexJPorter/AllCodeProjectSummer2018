package project1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CountDownTimerTest {


	/****************************************************************** 
	 * The following tests thoroughly test the code in the
	 * CountDownTimer class.
	 * 
	 *@author Alex Porter
	 *@version 1/20/2018
	 * ***************************************************************/

	@Test
	//tests constructors with valid arguments
	public void testConstructor() {
		CountDownTimer s = new CountDownTimer (5,10,30);
		assertEquals(s.toString(),"5:10:30");

		s = new CountDownTimer(0, 59, 59);
		assertEquals(s.toString(), "0:59:59");

		s = new CountDownTimer("20:10:08");
		assertEquals(s.toString(),"20:10:08");

		s = new CountDownTimer("20:08");
		assertEquals(s.toString(),"0:20:08");

		s = new CountDownTimer("8");
		assertEquals(s.toString(),"0:00:08");

		s = new CountDownTimer(8);
		assertEquals(s.toString(),"0:00:08");
	}
	@Test
	//tests all possible string cases in the constructor
	//only error cases are tested
	public void testConstructorsWithForLoop() {
		String toTest[] = {"apple", "a", "1:2:3:4:5", "99",
				"-33", ":0", "0:", "0:00:", "-1:01:09", "1:01:-4", 
				"1:02:03:04", "1:01:01:", "1::01:01", "1:01::01",
				"61", "-1", "61:01", "61:61","61:-01", "-1:61:1", 
				"-1:-1:61", "-01:61", "-01:01", "-01:001", "-1:01:01",
				"1:-01:01", "1:01:-01", "1:01:-1", ":", "1:-1:-1", 
				"1:-01:-01", "1:-1:-01", "1:-01:-1", "-1:-1:-1", 
				"-1:-01:-01", "::", "1:aa:aa", "1:01:aa", "1:aa:01",
				"a:01:01", "a:aa:aa","aa:aa",  "1:-1:0", "1:61:00", 
				"1:-1:00", "1:-1", "1:61", "-1:01", "61:01", "1:59:61",
				"1:61:59", "1:61:61"};
		for(int i = 0; i < toTest.length; i++ ) {
			try {
				new CountDownTimer(toTest[i]);
				throw new AssertionError("Error at index: " + i + "  " 
						+ toTest[i]);
			}
			catch(IllegalArgumentException e) {
				continue;
			}
		}
	}
	//a test simply for complete coverage for CountDownTimer
	@Test
	public void testConstructorWithCoverageString() {
		//without these methods, the class does not give all green
		//lines. 
		CountDownTimer s1 = new CountDownTimer("01:10");
		assertEquals(s1.toString(), "0:01:10");

		s1 = new CountDownTimer("59:59");
		assertEquals(s1.toString(), "0:59:59");
	}
	//the following 8 methods test illegal arguments
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegSeconds() {
		CountDownTimer s1 = new CountDownTimer(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorSecondsTooBig() {
		CountDownTimer s1 = new CountDownTimer(61);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorMinsTooBig() {
		CountDownTimer s1 = new CountDownTimer(61, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorSecsTooBig() {
		CountDownTimer s1 = new CountDownTimer(45, 61);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorMinsTooSmall() {
		CountDownTimer s1 = new CountDownTimer(-1, 11);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorSecsTooSmall() {
		CountDownTimer s1 = new CountDownTimer(45, -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorBothFail() {
		CountDownTimer s1 = new CountDownTimer(-45, 61);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testTwoArgConstructorBothFail2() {
		CountDownTimer s1 = new CountDownTimer(65, -45);
	}

	//check constructor with two arguments
	@Test
	public void testTwoArgConstructor() {
		CountDownTimer s1 = new CountDownTimer(45, 25);
		assertEquals(s1.toString(), "0:45:25");

		s1 = new CountDownTimer(59, 59);
		assertEquals(s1.toString(), "0:59:59");

		s1 = new CountDownTimer(1,1);
		assertEquals(s1.toString(), "0:01:01");
	}

	//The following 11 methods test illegal argument
	//constructors.
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors() {
		new CountDownTimer (5,100,300);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors2() {
		new CountDownTimer(5, -1, -2);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors3() {
		new CountDownTimer(-1, -1, -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors4() {
		new CountDownTimer(-1, -1, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors5() {
		new CountDownTimer(-1, 1, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors6() {
		new CountDownTimer(1, 1, -1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors7() {
		new CountDownTimer(1, -1, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors8() {
		new CountDownTimer(1, 61, 1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors9() {
		new CountDownTimer(1, 1, 61);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors10() {
		new CountDownTimer(1, 61, 61);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorWithErrors12() {
		new CountDownTimer(-1, 61, 61);
	}

	//tests the sub method
	@Test
	public void testSubMethod () {
		CountDownTimer s1 = new CountDownTimer (5,01,30);
		s1.sub(2000);
		assertEquals (s1.toString(),"4:28:10");

		s1 = new CountDownTimer (5,59,30);
		CountDownTimer s2 = new CountDownTimer (1,2,35);
		s1.sub(s2);
		assertEquals (s1.toString(),"4:56:55");

		s2 = new CountDownTimer(1,1,1);
		CountDownTimer.suspend(true);
		s2.sub(3600);
		assertEquals(s2.toString(), "1:01:01");

		CountDownTimer.suspend(false);


		for (int i = 0; i < 15000; i++) {
			s1.dec();
		}

		assertEquals (s1.toString(),"0:46:55");	

		s1 = new CountDownTimer();
		s1.dec();
		assertEquals(s1.toString(), "0:00:00");

		s1 = new CountDownTimer("0:00:01");
		CountDownTimer.suspend(true);
		s1.dec();
		assertEquals(s1.toString(), "0:00:01");

		CountDownTimer.suspend(false);
		s1.dec();
		assertEquals(s1.toString(), "0:00:00");

	}
	//test subtracting null
	@Test(expected=IllegalArgumentException.class)
	public void testSubNull() {
		CountDownTimer s1 = new CountDownTimer(2,0,9);
		CountDownTimer s2 = new CountDownTimer();
		s2 = null;
		s1.sub(s2);

	}
	//test sub negatives
	@Test(expected=IllegalArgumentException.class)
	public void testSubNeg() {
		CountDownTimer s1 = new CountDownTimer(2,0,9);
		s1.sub(-9);
	}

	//test subbing too much
	@Test(expected=IllegalArgumentException.class)
	public void testSubTooMuch() {
		CountDownTimer s1 = new CountDownTimer();
		s1.sub(1);
	}

	//test the add method
	@Test
	public void testAddMethod () {
		CountDownTimer s1 = new CountDownTimer (5,01,30);
		s1.add(3600);
		assertEquals (s1.toString(),"6:01:30");

		s1 = new CountDownTimer(1,01,01);
		CountDownTimer s2 = new CountDownTimer(1,1,1);
		s1.add(s2);
		assertEquals(s1.toString(), "2:02:02");

		CountDownTimer.suspend(true);
		s1.add(10);
		assertEquals(s1.toString(), "2:02:02");
		CountDownTimer.suspend(false);
	}

	//test equals method
	@Test 
	public void testEqual () {
		CountDownTimer s1 = new CountDownTimer (5,59,00);
		CountDownTimer s2 = new CountDownTimer (6,01,00);
		CountDownTimer s3 = new CountDownTimer ("5:59:00");

		assertFalse(s1.equals(s2));
		assertTrue (s1.equals(s3));

		assertTrue(CountDownTimer.equals(s1, s3));
		assertFalse(CountDownTimer.equals(s1, s2));

		s1 = new CountDownTimer(0,0,1);
		s2 = new CountDownTimer(0,0,59);
		s3 = new CountDownTimer(0,0,1);

		assertFalse(s1.equals(s2));
		assertTrue (s1.equals(s3));

		assertTrue(CountDownTimer.equals(s1, s3));
		assertFalse(CountDownTimer.equals(s1, s2));

	}

	//test equal with null
	@Test
	public void testEqualsNull() {
		CountDownTimer s1 = new CountDownTimer();
		CountDownTimer toNull = new CountDownTimer();
		toNull = null;
		assertFalse(s1.equals(toNull));
	}

	//test equals with non CountDownTimer object
	@Test
	public void testEqualsNonInstance() {
		CountDownTimer s1 = new CountDownTimer();
		Object nonTimer = new Object();
		assertFalse(s1.equals(nonTimer));

	}
	//test compareTo method
	@Test 
	public void testCompareTo () {
		CountDownTimer s1 = new CountDownTimer (5,59,00);
		CountDownTimer s2 = new CountDownTimer (6,01,00);
		CountDownTimer s3 = new CountDownTimer (5,50,20);
		CountDownTimer s4 = new CountDownTimer ("5:59:00");

		assertTrue (s2.compareTo(s1) > 0);
		assertTrue (s3.compareTo(s1) < 0);
		assertTrue (s1.compareTo(s4) == 0);	

		CountDownTimer greater = new CountDownTimer("50:00:00");
		CountDownTimer lesser = new CountDownTimer("49:00:00");
		CountDownTimer lesser2 = new CountDownTimer("49:00:00");

		assertEquals(CountDownTimer.compareTo(lesser, greater), -1);
		assertEquals(CountDownTimer.compareTo(greater,lesser), 1);
		assertEquals(CountDownTimer.compareTo(lesser,lesser2), 0);
	}

	//test load and save
	@Test 
	public void testLoadSave () {
		CountDownTimer s1 = new CountDownTimer (5,59,30);
		CountDownTimer s2 = new CountDownTimer ("5:59:30");

		s1.save("saveIt");
		s1 = new CountDownTimer ();  // resets to zero

		s1.load("saveIt");
		assertTrue (s1.equals(s2));	

		CountDownTimer.suspend(true);
		s1 = new CountDownTimer(1,1,1);
		s1.save("saveIt");

		s2.load("saveIt");
		assertFalse(s1.equals(s2));
	}

	//test suspend method
	@Test
	public void testSuspendMethod() {
		CountDownTimer.suspend(true);
		assertEquals(CountDownTimer.getFlag(), true);

		CountDownTimer.suspend(false);
		assertEquals(CountDownTimer.getFlag(), false);
	}

	//test save and load errors
	@Test(expected=IllegalArgumentException.class)
	public void testSaveError() {
		CountDownTimer s1 = new CountDownTimer("5:09:40");
		s1.save(s1.toString());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testLoadError() {
		CountDownTimer s1 = new CountDownTimer("6:17:18");

		s1.save("temp.txt");

		s1.load("wrongName.txt");
	}

	//test inc method
	@Test
	public void testIncrement() {
		CountDownTimer s1 = new CountDownTimer("6:17:18");
		CountDownTimer.suspend(false);

		s1.inc();
		assertEquals(s1.toString(), "6:17:19");

		CountDownTimer.suspend(true);
		s1.inc();
		assertEquals(s1.toString(), "6:17:19");

		CountDownTimer.suspend(false);
	}

	//test add method with null
	@Test(expected=IllegalArgumentException.class)
	public void testAddErrorObj() {
		CountDownTimer s1 = new CountDownTimer("6:17:18");
		CountDownTimer s2 = new CountDownTimer("6:17:18");
		s2 = null;
		s1.add(s2);
	}

	//test add with negative
	@Test(expected=IllegalArgumentException.class)
	public void testAddErrorNeg() {
		CountDownTimer s1 = new CountDownTimer("6:17:18");
		s1.add(-9);
	}

	//test the setters
	@Test
	public void testSetters() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setHours(10);
		assertEquals(s1.getHours(), 10);

		s1.setMinutes(10);
		assertEquals(s1.getMinutes(), 10);

		s1.setSeconds(10);
		assertEquals(s1.getSeconds(), 10);
	}

	//test errors with setters
	@Test(expected=IllegalArgumentException.class)
	public void testSecondsSetterNeg() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setSeconds(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testSecondsSetterTooBig() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setSeconds(61);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testMinutesSetterNeg() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setMinutes(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testMinutesSetterTooBig() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setMinutes(61);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testHoursSetterNeg() {
		CountDownTimer s1 = new CountDownTimer();
		s1.setHours(-1);
	}
	//test empty string constructor
	@Test
	public void testEmptyStringConstructor() {
		CountDownTimer s1 = new CountDownTimer("");

		assertEquals(s1.toString(), "0:00:00");
	}
	//test object constructor
	@Test
	public void testObjectConstructor() {
		CountDownTimer s1 = new CountDownTimer();
		CountDownTimer s2 = new CountDownTimer(s1);
		assertEquals(s2.toString(), "0:00:00");
	}
}