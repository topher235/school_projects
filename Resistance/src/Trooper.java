
public class Trooper extends Resistance {
	private Blaster weapon;
	
	//Constructor that receives name and sets blaster str
	Trooper(String name) {
		super(name);
		weapon = new Blaster(25);
	}
	
	//Constructor that receives name and blaster str
	Trooper(String name, Blaster blaster) {
		super(name);
		weapon = new Blaster(blaster.getStrength());
	}
	
	public Blaster getBlaster() {
		return weapon;
	}
	
	//Two troopers are equal if:
	//   their weapons are equal
	//   their names are equal (equal based on superclass)
	public boolean equals(Object other) {
		if (other == null  || (getClass() != other.getClass())) {
			return false;
		}
		Trooper otherTrooper = (Trooper) other;
		if (this.weapon.equals(otherTrooper.weapon) && super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
}
