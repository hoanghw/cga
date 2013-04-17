package cga.algorithm;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import cga.flight.Airline;
import cga.flight.Flight;
import cga.flight.Gate;
import cga.flight.Period;

public class Util {
	
	//given a flight, determine the best gate solution
	//for now: assign flight to the least busy gate & least waitTimeAtGate
	public static Gate findGate(ArrayList<Gate> gates, Flight f){
		IndexMinPQ<Long> pq = new IndexMinPQ<Long>(gates.size());
		for (int i=0;i<gates.size();i++){
			pq.insert(i, gates.get(i).totalOccupiedTime());
		}
		
		long leastWaitTime = Long.MAX_VALUE;
		int gateIndex = 0;
		while (!pq.isEmpty()){
			int i = pq.delMin();
			if (gates.get(i).assignFlight(f)){
				return gates.get(i);
			}
			else {
				if (leastWaitTime > gates.get(i).waitTimeAtGate(f)){
					leastWaitTime = gates.get(i).waitTimeAtGate(f);
					gateIndex = i;
				}
			}		
		}
		gates.get(gateIndex).forceAssign(f);
		return gates.get(gateIndex);
	}
	
	//naive populate gates with flights
	//restricted: TRUE - flight is only mapped to allowed gates
	public static MappingGate populateGates(ArrayList<Gate> gates, ArrayList<Flight> flights, boolean restricted){	
		MappingGate result = new MappingGate(gates);
		
		IndexMinPQ<Flight> pq = new IndexMinPQ<Flight>(flights.size());
		for (int i=0;i<flights.size();i++){
			pq.insert(i, flights.get(i));
		}
		
		while (!pq.isEmpty()){
			int i = pq.delMin();
			ArrayList<Gate> g = new ArrayList<Gate>();
			if (restricted)
				g = flights.get(i).getPossibleGates(gates);
			else 
				g = gates;
			List<Flight> value = result.map.get(findGate(g,flights.get(i))); 
			value.add(flights.get(i));
		}
		return result;
	}
	
	
	public static void parser(){
		
	}
	public static void calculateUtitlity(){
		
	}
	public static void calculateCost(String parameter){
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,List<Integer>> m = new HashMap<String,List<Integer>>();
		String[] strings = {"a","b","c","d"};
		for (int i = 0; i< strings.length;i++){
			List<Integer> v = new ArrayList<Integer>();
			v.add(i);
			m.put(strings[i], v);
		}
		List <Integer> v = m.get("a");
		v.add(77);
		System.out.print(m.get("a"));
		ArrayList<String> al = new ArrayList<String>();
		al.add("HI");
		Gate g1 = new Gate("g1",al);
		Gate g2 = new Gate("g2",al);
		ArrayList<Gate> gates = new ArrayList<Gate>();
		gates.add(g1);
		gates.add(g2);
		
		ArrayList<Flight> flights = new ArrayList<Flight>();
		
		GregorianCalendar start = new GregorianCalendar(2013,12,31,20,0);
		GregorianCalendar end = new GregorianCalendar(2013,12,31,21,0);
		Flight f1 = new Flight(1,start.getTime(),end.getTime(),new Airline("HI"));
		
		start = new GregorianCalendar(2013,12,31,20,1);
		end = new GregorianCalendar(2013,12,31,21,1);
		Flight f2 = new Flight(2,start.getTime(),end.getTime(),new Airline("HI"));
		
		flights.add(f1);flights.add(f2);
		
		start = new GregorianCalendar(2013,12,31,20,0);
		end = new GregorianCalendar(2013,12,31,21,0);
		flights.add(new Flight(3,start.getTime(),end.getTime(),new Airline("HI")));
		
		start = new GregorianCalendar(2013,12,31,20,0);
		end = new GregorianCalendar(2013,12,31,21,0);
		flights.add(new Flight(4,start.getTime(),end.getTime(),new Airline("HI")));
		
		
		MappingGate r = populateGates(gates,flights,true);
		System.out.println("Total delay at gate = "+r.calDelay());
		for (Period p : g1.periods){
			System.out.println(""+g1+" - "+p);
		}
		for (Period p : g2.periods){
			System.out.println(""+g2+" - "+p);
		}
	}

}
