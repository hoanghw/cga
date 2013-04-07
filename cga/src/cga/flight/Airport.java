package cga.flight;
import java.util.*;

public class Airport{
		String name;
		ArrayList<Gate> gates;
		ArrayList<Flight> flights;
		HashMap<Flight,Gate> gateMapping;

	public Airport(String _name, ArrayList<Gate> _gates){
		name = _name;
		gates = _gates; //DANGER?!
	}
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}