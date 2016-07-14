
public class Resistance {
	private String name;
	
	//Constructor that receives a name
	Resistance(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || (getClass() != other.getClass())) {
			return false;
		}
		Resistance otherRes = (Resistance) other;
		if (this.name == otherRes.name) {
			return true;
		} else {
			return false;
		}
	}
}
