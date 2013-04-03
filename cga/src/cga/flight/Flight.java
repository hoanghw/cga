package cga.flight;
import java.util.Date;
import cga.gate.*;

public class Flight {
	int id;				// Flight Number
	String airline;		// Name of airline
	Date aTime;			// Time of arrival
	Date dTime;			// Time of departure
	int capacity;		// Total carrying capacity
	int aPax;			// # arriving passengers
	int dPax;			// # departing passengers
	String equipType;	// The type of aircraft
	float delay = 0;	// Time delay
	Gate gate;			// Gate that the flight is assigned to
	int priority;		// Priority in gate assignment algorithm
	
	public Flight(int _id, String _airline, Date _aTime, Date _dTime, int _capacity, int _aPax, int _dPax, String _equipType){
		id = _id;
		airline = _airline;
		aTime = _aTime;
		dTime = _dTime;
		capacity = _capacity;
		aPax = _aPax;
		dPax = _dPax;
		equipType = _equipType;
		}

	public void setDelay(float _delay){
		delay = _delay;
	}

	public Date getArrivalTime(){
		aTime + delay
	}

	public Date getDepartureTime(){
		dTime + delay
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
