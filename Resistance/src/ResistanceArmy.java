import java.util.ArrayList;

public class ResistanceArmy {
	public ArrayList<Resistance> army;
	
	ResistanceArmy() {
		army = new ArrayList<Resistance>();
	}
	
	public void addSoldier(Resistance member) {
		army.add(member);
	}
	
	public ArrayList<Resistance> getArmy() {
		return army;
	}
}
