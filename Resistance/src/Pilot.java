
public class Pilot extends Trooper {
	private XWing weapon;
	
	//Constructor that receives a name and XWing
	Pilot(String name, XWing x) {
		super(name);
		weapon = new XWing(x.getStrength());
	}
	
	public XWing getXWing() {
		return weapon;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null  || (getClass() != other.getClass())) {
			return false;
		}
		Pilot otherPilot = (Pilot) other;
		if (this.weapon.equals(otherPilot.weapon) && super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
}
