
public class XWing extends Weapon {
	//Constructor that receives strength
	XWing(int str) {
		this.changeStrength(str);
	}
	
	public boolean equals(XWing other) {
		if (super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
}
