package cga.algorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cga.flight.*;

public class MappingGate {
	
	Date datetime;
	
	HashMap<Gate,List<Flight>> map;
	
	public MappingGate(HashMap<Gate,List<Flight>> m){
		map = m;
	}
	public MappingGate(Gate[] gates){
		map = new HashMap<Gate,List<Flight>>();
		for (int i = 0; i<gates.length;i++){
			map.put(gates[i], new ArrayList<Flight>());
		}
	}
	public MappingGate(ArrayList<Gate> gates){
		map = new HashMap<Gate,List<Flight>>();
		for (int i = 0; i<gates.size();i++){
			map.put(gates.get(i), new ArrayList<Flight>());
		}
	}
	public HashMap<Gate,Long> calDelay(){
		HashMap<Gate,Long> delay = new HashMap<Gate,Long>(); //K,V: Gate g, Total Delay at Gate g
		for (Map.Entry<Gate, List<Flight>> entry : map.entrySet()) {
		    Gate gate = entry.getKey();
		    List<Flight> flights = entry.getValue();
		    Flight[] copyFlights = new Flight[flights.size()];
		    for (int i = 0; i<flights.size(); i++){
		    	copyFlights[i] = Flight.copy(flights.get(i));
		    }
		    Quick.sort(copyFlights);
		    long totalDelay = 0; //minutes
		    for (int i = 0;i<copyFlights.length-1;i++){
		    	long minutesDelay = (copyFlights[i+1].realATime.getTime() - copyFlights[i].realDTime.getTime())/60000;
		    	if (minutesDelay < 0){
		    		totalDelay += minutesDelay;
		    		copyFlights[i+1].realATime = new Date(copyFlights[i+1].realATime.getTime() + minutesDelay*60000);
		    		copyFlights[i+1].realDTime = new Date(copyFlights[i+1].realDTime.getTime() + minutesDelay*60000);
		    	}
		    }
		    delay.put(gate,totalDelay);
		}
		return delay;
	}
	
	@Override
	public String toString(){
		String s = "{";
		for (Map.Entry<Gate, List<Flight>> entry : map.entrySet()) {
			Gate gate = entry.getKey();
		    List<Flight> flights = entry.getValue();
		    s+="("+gate+": ";
		    for (int i=0;i<flights.size();i++){
		    	s+=flights.get(i)+" ";
		    }
			s+=")";
		}
		s +="}";
		return s;
	}
	public int totalUtility(){
		return 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Gate> gates = new ArrayList<Gate>();
		Flight[] flights = new Flight[10];
		for (int i=0; i<gates.size(); i++){
			gates.add(new Gate());
		}
		MappingGate m = new MappingGate(gates);
		
		for (int i=0; i<flights.length;i++){
			GregorianCalendar start = new GregorianCalendar(2013,12,31,20-i,0+2*i);
			GregorianCalendar end = new GregorianCalendar(2013,12,31,21-i,0);
			flights[i] = new Flight(i,start.getTime(),end.getTime());
		}
		
		Airline fr = new Airline("frontier");
		
		m = Util.populateGates(gates, flights);
		System.out.println(m);
		System.out.println("Total delay at gate = "+m.calDelay());
	}

}
