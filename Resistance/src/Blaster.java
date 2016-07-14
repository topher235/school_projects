
public class Blaster extends Weapon {
	//Constructor that receives strength
	Blaster(int strength) {
		this.changeStrength(strength);
	}
	
	//Two blasters are equal if their superclass returns equal
	public boolean equals(Blaster other) {
		if(super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
}
