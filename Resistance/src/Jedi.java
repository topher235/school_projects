
public class Jedi extends Resistance implements Force {
	private int force;
	private Lightsaber weapon;
	
	//Constructor that receives a name and lightsaber, sets force to 10
	Jedi(String name, Lightsaber x) {
		super(name);
		weapon = new Lightsaber(x.getStrength(), x.getForce());
		force = 10;
	}
	
	public void setForce(int f) {
		if (f > 0) {
			force = f;
		}
	}
	
	public int getForce() {
		return force;
	}
	
	public Lightsaber getLightsaber() {
		return weapon;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || (getClass() != other.getClass())) {
			return false;
		}
		Jedi otherJedi = (Jedi) other;
		if ((this.force == otherJedi.force) && (this.weapon.equals(otherJedi.weapon)) && super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int compareTo(Force other) {
		return this.getForce() - other.getForce();
	}
}
