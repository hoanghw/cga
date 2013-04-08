package cga.flight;
import java.util.*;

public class Gate {
	Set compatible;
	Airline airline;
	
	ArrayList<Period> periods; //list of all the periods when the Gate is not available
	
	public Gate(){
		periods = new ArrayList<Period>();
	}
	public Gate(Airline _airline){
		airline = _airline;
		periods = new ArrayList<Period>();
	}
	
	//return true if assignment succeeds
	public boolean assignFlight(Flight f){
		Period newPeriod = new Period(f.realATime,f.realDTime);
		for (Period p : periods){
			if (Period.isOverLap(newPeriod, p)){
				return false;
			}
		}
		periods.add(newPeriod);
		return true;		
	}
	public long totalOccupiedTime(){
		long total = 0;
		for (Period p : periods){
			total += (p.end.getTime()-p.start.getTime())/60000;
		}
		return total;
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
