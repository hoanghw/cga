package cga.flight;
import java.util.*;

public class Flight implements Comparable<Flight> {
	int id;				// Flight Number
	Airline airline;		// Name of airline
	Date aTime;			// Time of arrival
	Date dTime;			// Time of departure
	int capacity;		// Total carrying capacity
	int aPax;			// # arriving passengers
	int dPax;			// # departing passengers
	String equipType;	// The type of aircraft
	long delay = 0;	// Time delay in minutes
	
	public Date realATime;
	public Date realDTime;
	Gate gate;			// Gate that the flight is assigned to
	int priority;		// Priority in gate assignment algorithm
	
	public Flight(int _id, Airline _airline, Date _aTime, Date _dTime, int _capacity, int _aPax, int _dPax, String _equipType){
		id = _id;
		airline = _airline;
		aTime = _aTime;
		dTime = _dTime;
		capacity = _capacity;
		aPax = _aPax;
		dPax = _dPax;
		equipType = _equipType;
		
		realATime = new Date(aTime.getTime()+delay*60000);
		realDTime = new Date(dTime.getTime()+delay*60000);
		}
	
	public Flight(int _id, Date _aTime, Date _dTime){
		id = _id;
		airline = new Airline("DUMMY");
		aTime = _aTime;
		dTime = _dTime;
		capacity = 1000;
		aPax = 1000;
		dPax = 1000;
		equipType = "DUMMY";
		
		realATime = new Date(aTime.getTime()+delay*60000);
		realDTime = new Date(dTime.getTime()+delay*60000);
		}
	public static Flight copy(Flight f){
		return new Flight(f.id,f.airline,f.aTime,f.dTime,f.capacity,f.aPax,f.dPax,f.equipType);
	}
	
	public void setDelay(){
		return;
	}

	public Date getArrivalTime(){
		//aTime + delay
		return realATime;
	}

	public Date getDepartureTime(){
		//dTime + delay
		return realDTime;
	}
	
	@Override
    public int compareTo(Flight otherFlight){
        return this.realATime.compareTo(otherFlight.realATime);
    }
	@Override
	public String toString(){
		return ""+this.id;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
