package cga.flight;
import java.util.*;

public class Flight implements Comparable<Flight> {
	int id;				// Flight Number
	Airline airline;		// Name of airline
	Date aTime;			// Time of arrival
	Date dTime;			// Time of departure
	int capacity;		// Total carrying capacity
	int aPax;			// # passengers
	int dPax;
	String equipType;	// The type of aircraft
	public long delay = 0;	    // Time delay in minutes
	
	public boolean isArrival;
	
	public Date realATime;
	public Date realDTime;
	Gate gate;			// Gate that the flight is assigned to
	int priority;		// Priority in gate assignment algorithm
	
	public Flight(int _id, Airline _airline, Date _aTime, Date _dTime, int _capacity, int _pax, String _equipType){
		id = _id;
		airline = _airline;
		aTime = _aTime;
		dTime = _dTime;
		capacity = _capacity;
		aPax = _pax;
		dPax = _pax;
		equipType = _equipType;
		
		realATime = new Date(aTime.getTime()+delay*60000);
		realDTime = new Date(dTime.getTime()+delay*60000);
		}
	
	public Flight(int _id, Date _aTime, Date _dTime){
		id = _id;
		airline = new Airline("DUMMY");
		aTime = _aTime;
		dTime = _dTime;
		capacity = 100;
		aPax = 100;
		dPax = 100;
		equipType = "DUMMY";
		
		realATime = new Date(aTime.getTime()+delay*60000);
		realDTime = new Date(dTime.getTime()+delay*60000);
		}
	
	public Flight(int _id, Date _realATime, Date _realDTime, Airline _airline){
		id = _id;
		airline = _airline;
		aTime = new Date(_realATime.getTime());
		dTime = new Date(_realDTime.getTime());
		capacity = 100;
		aPax = 100;
		dPax = 100;
		equipType = "DUMMY";
		
		realATime = _realATime;
		realDTime = _realDTime;
	}
	
	public static Flight copy(Flight f){
		return new Flight(f.id,f.realATime,f.realDTime,f.airline);
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
	
	public ArrayList<Gate> getPossibleGates(ArrayList<Gate> gates){
		ArrayList<Gate> result = new ArrayList<Gate>();
		for (Gate g: gates){
			if (g.canBeAssigned(this)){
				result.add(g);
			}
		}
		return result;
	}
	
	public ArrayList<Gate> getPossibleGates(Gate[] g){
		ArrayList<Gate> result = new ArrayList<Gate>();
		for (int i=0;i<g.length;i++){
			if (g[i].airlines.contains(this.airline)){
				result.add(g[i]);
			}
		}
		return result;
	}
	//in minutes
	public long ungatedTime(){
		return (this.realATime.getTime()-this.aTime.getTime())/60000;
	}
	public void setArrival(boolean a){
		this.isArrival=a;
	}
	public boolean isArrival(){
		return isArrival;
	}
	@Override
    public int compareTo(Flight otherFlight){
        return this.realATime.compareTo(otherFlight.realATime);
    }
	@Override
	public String toString(){
		if (this.delay == 0)
			return ""+this.id+" Arr "+this.aTime+" Dep "+this.dTime+" UnGated "+this.delay;
		else
			return ""+this.id+" Arr "+this.aTime+" Dep "+this.dTime+" Real Arr "+this.realATime+" UNGATED "+this.delay;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
