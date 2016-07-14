import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class ResistanceArmyTest {
	

	@Test
	public void testAddOneResistanceSoldier() {
		int expected, actual;
		boolean actualBool;

		ResistanceArmy army = new ResistanceArmy();
		army.addSoldier(new Resistance("Rey"));

		ArrayList<Resistance> armyList = army.getArmy();

		actual = armyList.size();
		expected = 1;
		assertEquals("Incorrect result", expected, actual);

		actualBool = armyList.contains(new Resistance("Rey"));
		assertTrue("Incorrect result", actualBool);

	}

	@Test
	public void testAddManyResistanceSoldierSize() {
		Jedi jedi1, jedi2;
		Trooper trooper;
		Droid droid;
		ResistanceArmy army;
		ArrayList<Resistance> armyList;
		int expected, actual;

		jedi1 = new Jedi("Rey", new Lightsaber(85, 23));
		jedi2 = new Jedi("Luke", new Lightsaber(27, 42));
		trooper = new Trooper("Poe", new Blaster(34));
		droid = new Droid("BB-8");

		army = new ResistanceArmy();
		army.addSoldier(jedi1);
		army.addSoldier(jedi2);
		army.addSoldier(trooper);
		army.addSoldier(droid);

		armyList = army.getArmy();

		actual = armyList.size();
		expected = 4;
		assertEquals("Incorrect result", expected, actual);
	}
	
	@Test
	public void testAddManyResistanceSoldierContains() {
		Jedi jedi1, jedi2;
		Trooper trooper;
		Droid droid;
		ResistanceArmy army;
		ArrayList<Resistance> armyList;
		boolean actual;

		jedi1 = new Jedi("Rey", new Lightsaber(85, 23));
		jedi2 = new Jedi("Luke", new Lightsaber(27, 42));
		trooper = new Trooper("Poe", new Blaster(34));
		droid = new Droid("BB-8");

		army = new ResistanceArmy();
		army.addSoldier(jedi1);
		army.addSoldier(jedi2);
		army.addSoldier(trooper);
		army.addSoldier(droid);

		armyList = army.getArmy();

		actual = armyList.contains(jedi1);
		assertTrue("Incorrect result", actual);

		actual = armyList.contains(jedi2);
		assertTrue("Incorrect result", actual);

		actual = armyList.contains(trooper);
		assertTrue("Incorrect result", actual);

		actual = armyList.contains(droid);
		assertTrue("Incorrect result", actual);

	}
	
	

}