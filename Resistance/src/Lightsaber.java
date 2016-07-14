
public class Lightsaber extends Weapon implements Force {
	private int force;		//private field variable for force
	
	//Constructor for Lightsaber
	//Sets the strength value from the Weapons class
	Lightsaber(int strength, int force) {
		changeStrength(strength);
		this.force = force;
	}
	
	public void setForce(int f) {
		force = f;
	}
	
	//Defining the getForce() method from the Force interface
	public int getForce() {
		return force;
	}
	
	//Overriding .equals based on force and superclass equivalence
	public boolean equals(Lightsaber other) {
		if (other == null) {
			return false;
		} else if (this.getForce() == other.getForce() && super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Force o) {
		return this.getForce() - o.getForce();
	}

}
