package cga.flight;
import java.util.*;

public class Gate {
	String name;
	ArrayList<String> airlines;
	public ArrayList<Period> periods; //list of all the periods when the Gate is not available
	
	static Random gen = new Random();
	
	public Gate(){
		periods = new ArrayList<Period>();
		name = "G"+ gen.nextInt(100);
		airlines = new ArrayList<String>();
	}
	
	public Gate(Airline _airline){
		periods = new ArrayList<Period>();
		name = "G"+ gen.nextInt(100);
		airlines = new ArrayList<String>();
		airlines.add(_airline.name);
	}
	
	public Gate(String _name, ArrayList<String> _airlines){
		periods = new ArrayList<Period>();
		name = _name;
		airlines = _airlines;
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
	public boolean forceAssign(Flight f){
		Period period = new Period(f.realATime,f.realDTime);
		for (Period p : periods){
			if (Period.isOverLap(period, p)){
				long delay = p.end.getTime()-period.start.getTime();
				//System.out.println("Flight Schedule: " + f);
				//System.out.println("Gate overlap: " + p);
				f.delay=delay/60000;
				Period newPeriod =new Period(new Date(f.realATime.getTime()+delay),new Date(f.realDTime.getTime()+delay));
				periods.add(newPeriod);
				//System.out.println("New period added: " +newPeriod);
				return true;
			}
		}
		System.out.println("NOT SUPPOSED TO REACH HERE");
		periods.add(period);
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
		return this.airlines.contains(flight.airline.name);
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
