import static org.junit.Assert.*;

import java.lang.reflect.*;

import org.junit.Test;

public class WeaponTest {

	/****************
	 * Test Weapons * (and Force)
	 ****************/

	@Test
	public void testForceIsInterface() {
		assertTrue("Force should be interface.", Modifier.isInterface(Force.class.getModifiers()));
	}

	@Test
	public void testWeaponIsAbstract() {
		assertTrue("Weapon should be abstract.", Modifier.isAbstract(Weapon.class.getModifiers()));
	}

	@Test
	public void testWeaponAndLightsaberPrivateFields() {
		Field[] allFields = Weapon.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Weapon fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

		allFields = Lightsaber.class.getDeclaredFields();
		for (Field field : allFields) {
			assertTrue("All Lightsaber fields should be private.", Modifier.isPrivate(field.getModifiers()));
		}

	}

	@Test
	public void testBlasterAndXWingNoFields() {
		Field[] allFields = Blaster.class.getDeclaredFields();
		assertEquals("Blaster does not need fields.", 0, allFields.length);

		allFields = XWing.class.getDeclaredFields();
		assertEquals("XWing does not need fields.", 0, allFields.length);

	}

	@Test
	public void testWeaponGetStrength() {
		Weapon[] weapons = new Weapon[3];
		int expected, actual;

		weapons[0] = new Lightsaber(23, 65);
		weapons[1] = new Blaster(84);
		weapons[2] = new XWing(93);

		actual = weapons[0].getStrength();
		expected = 23;
		assertEquals("Incorrect result", expected, actual);

		actual = weapons[1].getStrength();
		expected = 84;
		assertEquals("Incorrect result", expected, actual);

		actual = weapons[2].getStrength();
		expected = 93;
		assertEquals("Incorrect result", expected, actual);

	}

	@Test
	public void testWeaponChangeStrength() {
		Weapon[] weapons = new Weapon[3];
		int expected, actual;

		weapons[0] = new Lightsaber(23, 65);
		weapons[1] = new Blaster(84);
		weapons[2] = new XWing(93);

		weapons[0].changeStrength(-45);
		actual = weapons[0].getStrength();
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		weapons[1].changeStrength(23);
		actual = weapons[1].getStrength();
		expected = 107;
		assertEquals("Incorrect result", expected, actual);

		weapons[2].changeStrength(-35);
		actual = weapons[2].getStrength();
		expected = 58;
		assertEquals("Incorrect result", expected, actual);

	}

	@Test
	public void testWeaponOverridesEquals() {
		try { //tests to make sure Weapon's .equals in defined in Weapon not Object
			assertEquals(".equals not defined in Weapon", Weapon.class,
					Weapon.class.getMethod("equals", new Class[] { Object.class }).getDeclaringClass());
		} catch (Exception e) {
			fail("Weapon is missing .equals.");
		}
	}

	/*****************
	 * Test Blasters *
	 *****************/

	@Test
	public void testBlastersAreWeapons() {
		Blaster blaster = new Blaster(37);
		assertTrue(blaster instanceof Weapon);
	}

	@Test
	public void testBlasterEquals() {
		Blaster blaster1, blaster2;
		boolean actual;

		// equal to itself
		blaster1 = new Blaster(37);
		actual = blaster1.equals(blaster1);
		assertTrue("Incorrect result", actual);

		// equal to another Blaster same strength
		blaster1 = new Blaster(63);
		blaster2 = new Blaster(63);
		actual = blaster1.equals(blaster2);
		assertTrue("Incorrect result", actual);

		// not equal to Blaster with different strength
		blaster1 = new Blaster(37);
		blaster2 = new Blaster(23);
		actual = blaster1.equals(blaster2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		blaster1 = new Blaster(73);
		actual = blaster1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		blaster1 = new Blaster(48);
		actual = blaster1.equals("A Blaster");
		assertFalse("Incorrect result", actual);

	}

	/********************
	 * Test Lightsabers *
	 ********************/

	@Test
	public void testLightsabersAreWeapons() {
		Lightsaber lightsaber = new Lightsaber(25, 40);
		assertTrue(lightsaber instanceof Weapon);
	}

	@Test
	public void testLightsabersAreForce() {
		Lightsaber lightsaber = new Lightsaber(25, 40);
		assertTrue(lightsaber instanceof Force);

	}

	@Test
	public void testLightsaberEquals() {
		Lightsaber lightsaber1, lightsaber2;
		boolean actual;

		// equal to itself
		lightsaber1 = new Lightsaber(25, 40);
		actual = lightsaber1.equals(lightsaber1);
		assertTrue("Incorrect result", actual);

		// equal to another Lightsaber same force and strength
		lightsaber1 = new Lightsaber(75, 60);
		lightsaber2 = new Lightsaber(75, 60);
		actual = lightsaber1.equals(lightsaber2);
		assertTrue("Incorrect result", actual);

		// not equal to Lightsaber with different force
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(25, 86);
		actual = lightsaber1.equals(lightsaber2);
		assertFalse("Incorrect result", actual);

		// not equal to Lightsaber with different strength
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(17, 40);
		actual = lightsaber1.equals(lightsaber2);
		assertFalse("Incorrect result", actual);

		// not equal to Lightsaber with different strength + force
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(17, 56);
		actual = lightsaber1.equals(lightsaber2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		lightsaber1 = new Lightsaber(25, 40);
		actual = lightsaber1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		lightsaber1 = new Lightsaber(25, 40);
		actual = lightsaber1.equals("A Lightsaber");
		assertFalse("Incorrect result", actual);

	}

	@Test
	public void testLightsaberCompareToLightsaber() {
		Lightsaber lightsaber1, lightsaber2;
		int expected, actual;

		// compare to same Lightsaber
		lightsaber1 = new Lightsaber(25, 40);
		actual = lightsaber1.compareTo(lightsaber1);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to Lightsaber same force
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(46, 40);
		actual = lightsaber1.compareTo(lightsaber2);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to equal Lightsaber
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(25, 40);
		actual = lightsaber1.compareTo(lightsaber2);
		expected = 0;
		assertEquals("Incorrect result", expected, actual);

		// compare to weaker Lightsaber
		lightsaber1 = new Lightsaber(65, 40);
		lightsaber2 = new Lightsaber(46, 17);
		actual = lightsaber1.compareTo(lightsaber2);
		expected = 23;
		assertEquals("Incorrect result", expected, actual);

		// compare to stronger Lightsaber
		lightsaber1 = new Lightsaber(25, 40);
		lightsaber2 = new Lightsaber(46, 54);
		actual = lightsaber1.compareTo(lightsaber2);
		expected = -14;
		assertEquals("Incorrect result", expected, actual);

	}

	@Test
	public void testLightsaberGetForce() {
		Lightsaber lightsaber = new Lightsaber(85, 67);
		int actual = lightsaber.getForce();
		int expected = 67;
		assertEquals("Incorrect result", expected, actual);
	}

	/***************
	 * Test XWings *
	 ***************/

	@Test
	public void testXWingsAreWeapons() {
		XWing xwing = new XWing(37);
		assertTrue(xwing instanceof Weapon);
	}

	@Test
	public void testXWingEquals() {
		XWing xwing1, xwing2;
		boolean actual;

		// equal to itself
		xwing1 = new XWing(37);
		actual = xwing1.equals(xwing1);
		assertTrue("Incorrect result", actual);

		// equal to another XWing same strength
		xwing1 = new XWing(63);
		xwing2 = new XWing(63);
		actual = xwing1.equals(xwing2);
		assertTrue("Incorrect result", actual);

		// not equal to XWing with different strength
		xwing1 = new XWing(37);
		xwing2 = new XWing(23);
		actual = xwing1.equals(xwing2);
		assertFalse("Incorrect result", actual);

		// not equal to null
		xwing1 = new XWing(73);
		actual = xwing1.equals(null);
		assertFalse("Incorrect result", actual);

		// not equal to some other object
		xwing1 = new XWing(48);
		actual = xwing1.equals("A XWing");
		assertFalse("Incorrect result", actual);

	}

}