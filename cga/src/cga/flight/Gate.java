package cga.flight;
import java.util.*;

public class Gate {
	Set compatible;
	Airline airline;
	String name;
	
	ArrayList<Period> periods; //list of all the periods when the Gate is not available
	
	static Random gen = new Random();
	
	public Gate(){
		periods = new ArrayList<Period>();
		name = "G"+ gen.nextInt(100);
	}
	public Gate(Airline _airline){
		airline = _airline;
		periods = new ArrayList<Period>();
		name = "G"+ gen.nextInt(100);
	}
	
	//return true if assignment succeeds and update periods variable
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
	
	//Given flight f, return time it has to wait to access gate.
	public long waitTimeAtGate(Flight f){
		long time = 0;
		for (Period p : periods){
			time += Period.overlapLength(p,new Period(f.realATime,f.realDTime));
		}
		return time;
	}
	
	//return total minutes of gate being occupied
	public long totalOccupiedTime(){
		long total = 0;
		for (Period p : periods){
			total += (p.end.getTime()-p.start.getTime())/60000;
		}
		return total;
	}

	//delete all periods after given date
	public void deletePeriods(Date date){
		Iterator<Period> it = periods.iterator();
		while(it.hasNext())
		{
		    Period p = it.next();
		    if (p.start.after(date)){
				it.remove(); //cannot do periods.remove(p) concurrentmodificationexception
			}
		}
	}

	public boolean canBeAssigned(Flight flight) {
		int id = flight.id;
		String flightAirline = flight.airline.name;
		return compatible.contains(id)&((airline.equals(flightAirline)|(airline.equals("collaborative"))));
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gate g = new Gate();
		System.out.println(g.periods.isEmpty());
		GregorianCalendar start = new GregorianCalendar(2013,12,31,20,0);
		GregorianCalendar end = new GregorianCalendar(2013,12,31,21,0);
		g.periods.add(new Period(start.getTime(),end.getTime()));
		System.out.println(g.periods.isEmpty());
		GregorianCalendar ref = new GregorianCalendar(2013,12,31,18,30);
		g.deletePeriods(ref.getTime());
		System.out.println(g.periods.isEmpty());
	}

}
