
public class Droid extends Resistance {
	//Constructor that receives a name
	Droid(String name) {
		super(name);
	}
	
	public boolean equals(Droid other) {
		if (super.equals(other)) {
			return true;
		} else {
			return false;
		}
	}
}
