
public abstract class Weapon {
	private int strength = 0;
	
	Weapon() {
		strength = 0;
	}
	
	Weapon(int strength) {
		this.strength = strength;
	}
	
	int getStrength() {
		return strength;
	}
	
	void changeStrength(int change) {
		this.strength = strength + change;
		if (strength < 0) {
			strength = 0;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || (getClass() != other.getClass())) {
			return false;
		} 
		Weapon otherWeapon = (Weapon) other;
		if (this.strength == otherWeapon.strength) {
			return true;
		} else {
			return false;
		}
	}
}
