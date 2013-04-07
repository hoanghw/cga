package cga.flight;
import java.util.*;

public class Gate {
	Set compatible;
	Airline airline;
	
	public Gate(Airline _airline){
		airline = _airline;
	}

	public boolean canBeAssigned(Flight flight) {
		int id = flight.id;
		String flightAirline = flight.airline.name;
		return compatible.contains(id)&((airline.equals(flightAirline)|(airline.equals("collaborative"))));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
