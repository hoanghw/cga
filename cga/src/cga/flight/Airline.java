package cga.flight;
import java.util.*;

public class Airline {
	String name;

	public Airline(String _name){
		name = _name;
	}

	public String getName(){
		return name;
	}

	public ArrayList<Gate> getGates(Airport airport){
		ArrayList<Gate> allGates = airport.gates;
		ArrayList<Gate> airportGates = new ArrayList<Gate>();
		for(int i = 0; i < allGates.size(); i++) {
			if (allGates.get(i).airlines.contains(this)) {
				airportGates.add(allGates.get(i));
			}
		}
		return airportGates;
	}
	
	public boolean equals(Airline a){
		return name.equals(a.name);
	}
	
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}