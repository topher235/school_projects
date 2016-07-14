import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ResistanceTest {

	/*******************
	 * Test Resistance *
	 *******************/

	@Test
	public void testPrivateOrNoFields() {
		Field[] allFields = Resistance.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Resistance fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

		allFields = Trooper.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Trooper fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

		allFields = Pilot.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Pilot fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

		allFields = Jedi.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Jedi fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

		allFields = Droid.class.getDeclaredFields();
		assertEquals("Droid does not need fields.", 0, allFields.length);

	}

	@Test
	public void testResistanceEquals() {
		Resistance member1, member2;
		boolean actual;

		// test equal to self
		member1 = new Resistance("Rey");
		actual = member1.equals(member1);
		assertTrue("Incorrect result", actual);

		// test equal to equivalent Resistance
		member1 = new Resistance("Rey");
		member2 = new Resistance("Rey");
		actual = member1.equals(member2);
		assertTrue("Incorrect result", actual);

		// not equal to Resistance with different name
		member1 = new Resistance("Rey");
		member2 = new Resistance("BB-8");
		actual = member1.equals(member2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		member1 = new Resistance("Rey");
		actual = member1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		member1 = new Resistance("Rey");
		actual = member1.equals("A Resistance");
		assertFalse("Incorrect result", actual);

	}

	@Test
	public void testResistanceGetName() {
		Resistance[] members = new Resistance[4];
		Lightsaber lightsaber = new Lightsaber(5, 10);
		Blaster blaster = new Blaster(15);
		String expected, actual;

		members[0] = new Jedi("Rey", lightsaber);
		members[1] = new Trooper("Poe", blaster);
		members[2] = new Droid("BB-8");
		members[3] = new Resistance("C-3PO");

		actual = members[0].getName();
		expected = "Rey";
		assertEquals("Incorrect result", expected, actual);

		actual = members[1].getName();
		expected = "Poe";
		assertEquals("Incorrect result", expected, actual);

		actual = members[2].getName();
		expected = "BB-8";
		assertEquals("Incorrect result", expected, actual);

		actual = members[3].getName();
		expected = "C-3PO";
		assertEquals("Incorrect result", expected, actual);

	}

	/*****************
	 * Test Troopers *
	 *****************/

	@Test
	public void testTroopersAreResistance() {
		Blaster blaster = new Blaster(25);
		Trooper trooper = new Trooper("Leia Organa", blaster);
		assertTrue(trooper instanceof Resistance);
	}

	@Test
	public void testTrooperEquals() {
		Blaster blaster1, blaster2;
		Trooper trooper1, trooper2;
		boolean actual;

		// equal to self
		blaster1 = new Blaster(84);
		trooper1 = new Trooper("Han Solo", blaster1);
		actual = trooper1.equals(trooper1);
		assertTrue("Incorrect result", actual);

		// equal to Trooper with same blaster, name
		blaster1 = new Blaster(35);
		blaster2 = new Blaster(35);
		trooper1 = new Trooper("Han Solo", blaster1);
		trooper2 = new Trooper("Han Solo", blaster2);
		actual = trooper1.equals(trooper2);
		assertTrue("Incorrect result", actual);

		// not equal to Trooper with different name
		blaster1 = new Blaster(74);
		blaster2 = new Blaster(74);
		trooper1 = new Trooper("Chewbacca", blaster1);
		trooper2 = new Trooper("Goss Toowers", blaster2);
		actual = trooper1.equals(trooper2);
		assertFalse("Incorrect result", actual);

		// not equal to Trooper with different Blaster
		blaster1 = new Blaster(74);
		blaster2 = new Blaster(53);
		trooper1 = new Trooper("Chewbacca", blaster1);
		trooper2 = new Trooper("Chewbacca", blaster2);
		actual = trooper1.equals(trooper2);
		assertFalse("Incorrect result", actual);

		// not equal to trooper with different name + Blaster
		blaster1 = new Blaster(74);
		blaster2 = new Blaster(53);
		trooper1 = new Trooper("Chewbacca", blaster1);
		trooper2 = new Trooper("Leia Organa", blaster2);
		actual = trooper1.equals(trooper2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		blaster1 = new Blaster(74);
		trooper1 = new Trooper("Chewbacca", blaster1);
		actual = trooper1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		blaster1 = new Blaster(74);
		trooper1 = new Trooper("Chewbacca", blaster1);
		actual = trooper1.equals("A Trooper");
		assertFalse("Incorrect result", actual);

	}

	@Test
	public void testTrooperGetBlaster() {
		Blaster blaster1, blaster2;
		Trooper trooper;
		int expected, actual;

		blaster1 = new Blaster(29);
		trooper = new Trooper("Goss Toowers", blaster1);

		blaster2 = trooper.getBlaster();

		actual = blaster2.getStrength();
		expected = 29;
		assertEquals("Incorrect result", expected, actual);

	}

	/*************
	 * Test Jedi *
	 *************/

	@Test
	public void testJediAreResistance() {
		Lightsaber lightsaber = new Lightsaber(25, 40);
		Jedi jedi = new Jedi("Rey", lightsaber);
		assertTrue(jedi instanceof Resistance);
	}

	public void testJediAreForce() {
		Lightsaber lightsaber = new Lightsaber(25, 40);
		Jedi jedi = new Jedi("Rey", lightsaber);
		assertTrue(jedi instanceof Force);
	}

	@Test
	public void testJediEquals() {
		Lightsaber lightsaber1, lightsaber2;
		Jedi jedi1, jedi2;
		boolean actual;

		// equal to self
		lightsaber1 = new Lightsaber(84, 67);
		jedi1 = new Jedi("Rey", lightsaber1);
		actual = jedi1.equals(jedi1);
		assertTrue("Incorrect result", actual);

		// equal to Jedi with same lightsaber, name
		lightsaber1 = new Lightsaber(35, 47);
		lightsaber2 = new Lightsaber(35, 47);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		actual = jedi1.equals(jedi2);
		assertTrue("Incorrect result", actual);

		// not equal to Jedi with different name
		lightsaber1 = new Lightsaber(74, 47);
		lightsaber2 = new Lightsaber(74, 47);
		jedi1 = new Jedi("Obi Wan Kenobi", lightsaber1);
		jedi2 = new Jedi("Luke", lightsaber2);
		actual = jedi1.equals(jedi2);
		assertFalse("Incorrect result", actual);

		// not equal to Jedi with different Lightsaber
		lightsaber1 = new Lightsaber(74, 47);
		lightsaber2 = new Lightsaber(73, 57);
		jedi1 = new Jedi("Obi Wan Kenobi", lightsaber1);
		jedi2 = new Jedi("Obi Wan Kenobi", lightsaber2);
		actual = jedi1.equals(jedi2);
		assertFalse("Incorrect result", actual);

		// not equal to jedi with different name + Lightsaber
		lightsaber1 = new Lightsaber(74, 47);
		lightsaber2 = new Lightsaber(73, 57);
		jedi1 = new Jedi("Obi Wan Kenobi", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		actual = jedi1.equals(jedi2);
		assertFalse("Incorrect result", actual);

		// not equal to jedi with different force
		lightsaber1 = new Lightsaber(74, 57);
		lightsaber2 = new Lightsaber(74, 57);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		jedi1.setForce(25);
		actual = jedi1.equals(jedi2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		lightsaber1 = new Lightsaber(74, 47);
		jedi1 = new Jedi("Obi Wan Kenobi", lightsaber1);
		actual = jedi1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		lightsaber1 = new Lightsaber(74, 47);
		jedi1 = new Jedi("Obi Wan Kenobi", lightsaber1);
		actual = jedi1.equals("A Jedi");
		assertFalse("Incorrect result", actual);

	}

	@Test
	public void testJediGetLightsaber() {
		Lightsaber lightsaber1, lightsaber2;
		Jedi jedi;
		int expected, actual;

		lightsaber1 = new Lightsaber(29, 62);
		jedi = new Jedi("Luke", lightsaber1);

		lightsaber2 = jedi.getLightsaber();

		actual = lightsaber2.getForce();
		expected = 62;
		assertEquals("Incorrect result", expected, actual);

		actual = lightsaber2.getStrength();
		expected = 29;
		assertEquals("Incorrect result", expected, actual);

	}

	@Test
	public void testJediGetSetForce() {
		int expected, actual;
		Lightsaber lightsaber;
		Jedi jedi;

		// test default jedi force
		lightsaber = new Lightsaber(35, 67);
		jedi = new Jedi("Rey", lightsaber);
		expected = 10; // default
		actual = jedi.getForce();
		assertEquals("Incorrect result", expected, actual);

		// test default jedi positive (OK)
		lightsaber = new Lightsaber(35, 67);
		jedi = new Jedi("Rey", lightsaber);
		jedi.setForce(25);
		expected = 25; // default
		actual = jedi.getForce();
		assertEquals("Incorrect result", expected, actual);

		// test default jedi negative (not OK)
		lightsaber = new Lightsaber(35, 67);
		jedi = new Jedi("Rey", lightsaber);
		jedi.setForce(-25);
		expected = 10; // default
		actual = jedi.getForce();
		assertEquals("Incorrect result", expected, actual);

	}

	@Test
	public void testJediCompareToJedi() {
		Jedi jedi1, jedi2;
		Lightsaber lightsaber1, lightsaber2;
		int expected, actual;

		// compare to same Jedi
		lightsaber1 = new Lightsaber(84, 67);
		jedi1 = new Jedi("Rey", lightsaber1);
		actual = jedi1.compareTo(jedi1);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to Jedi same force
		lightsaber1 = new Lightsaber(35, 47);
		lightsaber2 = new Lightsaber(84, 67);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		actual = jedi1.compareTo(jedi2);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to equal Jedi
		lightsaber1 = new Lightsaber(35, 47);
		lightsaber2 = new Lightsaber(35, 47);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		actual = jedi1.compareTo(jedi2);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to weaker Jedi
		lightsaber1 = new Lightsaber(35, 47);
		lightsaber2 = new Lightsaber(84, 67);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		jedi1.setForce(33);
		actual = jedi1.compareTo(jedi2);
		expected = 23;
		assertEquals("Incorrect result", expected, actual);

		// compare to stronger Jedi
		lightsaber1 = new Lightsaber(35, 47);
		lightsaber2 = new Lightsaber(84, 67);
		jedi1 = new Jedi("Rey", lightsaber1);
		jedi2 = new Jedi("Rey", lightsaber2);
		jedi2.setForce(24);
		actual = jedi1.compareTo(jedi2);
		expected = -14;
		assertEquals("Incorrect result", expected, actual);

	}

	/***************
	 * Test Droids *
	 ***************/

	@Test
	public void testDroidsAreResistance() {
		Droid droid = new Droid("BB-8");
		assertTrue(droid instanceof Resistance);
	}

	@Test
	public void testDroidEquals() {
		Droid droid1, droid2;
		boolean actual;

		// equal to self
		droid1 = new Droid("BB-8");
		actual = droid1.equals(droid1);
		assertTrue("Incorrect result", actual);

		// equal to Droid with same name
		droid1 = new Droid("BB-8");
		droid2 = new Droid("BB-8");
		actual = droid1.equals(droid2);
		assertTrue("Incorrect result", actual);

		// not equal to Droid with different name
		droid1 = new Droid("R2-D2");
		droid2 = new Droid("C-3PO");
		actual = droid1.equals(droid2);
		assertFalse("Incorrect result", actual);
		
		// not equal to Resistance with same name
		droid1 = new Droid("C-3PO");
		actual = droid1.equals(new Resistance("C-3PO"));
		assertFalse("Incorrect result", actual);

		// not equal to null
		droid1 = new Droid("R2-D2");
		actual = droid1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		droid1 = new Droid("R2-D2");
		actual = droid1.equals("A Droid");
		assertFalse("Incorrect result", actual);

	}

	/***************
	 * Test Pilots *
	 ***************/

	@Test
	public void testPilotsAreTroopers() {
		XWing xwing = new XWing(25);
		Pilot pilot = new Pilot("Iolo Arana", xwing);
		assertTrue(pilot instanceof Trooper);

	}

	@Test
	public void testPilotEquals() {
		XWing xwing1, xwing2;
		Pilot pilot1, pilot2;
		boolean actual;

		// equal to self
		xwing1 = new XWing(84);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		actual = pilot1.equals(pilot1);
		assertTrue("Incorrect result", actual);

		// equal to Pilot with same xwing, name
		xwing1 = new XWing(35);
		xwing2 = new XWing(35);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		pilot2 = new Pilot("Poe Dameron", xwing2);
		actual = pilot1.equals(pilot2);
		assertTrue("Incorrect result", actual);

		// not equal to Pilot with different name
		xwing1 = new XWing(74);
		xwing2 = new XWing(74);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		pilot2 = new Pilot("Iola Arana", xwing2);
		actual = pilot1.equals(pilot2);
		assertFalse("Incorrect result", actual);

		// not equal to Pilot with different XWing
		xwing1 = new XWing(74);
		xwing2 = new XWing(53);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		pilot2 = new Pilot("Poe Dameron", xwing2);
		actual = pilot1.equals(pilot2);
		assertFalse("Incorrect result", actual);

		// not equal to pilot with different name + XWing
		xwing1 = new XWing(74);
		xwing2 = new XWing(53);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		pilot2 = new Pilot("Iolo Arana", xwing2);
		actual = pilot1.equals(pilot2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		xwing1 = new XWing(74);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		actual = pilot1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		xwing1 = new XWing(74);
		pilot1 = new Pilot("Poe Dameron", xwing1);
		actual = pilot1.equals("A Pilot");
		assertFalse("Incorrect result", actual);

	}

	@Test
	public void testPilotGetXWing() {
		XWing xwing1, xwing2;
		Pilot pilot;
		int expected, actual;

		xwing1 = new XWing(29);
		pilot = new Pilot("Iola Arana", xwing1);

		xwing2 = pilot.getXWing();

		actual = xwing2.getStrength();
		expected = 29;
		assertEquals("Incorrect result", expected, actual);

	}

}