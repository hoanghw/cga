package cga.gate;

public class Gate {
	Set compatible;
	Airline airline;
	
	public Gate(Airline _airline){
		airline = _airline;
	}

	public boolean canBeAssigned(Flight flight) {
		id = flight.id();
		flightAirline = flight.airline();
		return compatible.contains(id)&((airline.equals(flightAirline)|(airline.equals("collaborative")){
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
