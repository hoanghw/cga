package cga.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cga.flight.Flight;
import cga.flight.Gate;

public class Util {
	
	//naive populate gates with flights
	public static MappingGate populateGates(ArrayList<Gate> gates, Flight[] flights){
		MappingGate result = new MappingGate(gates);
		
		IndexMinPQ<Flight> pq = new IndexMinPQ<Flight>(flights.length);
		for (int i=0;i<flights.length;i++){
			pq.insert(i, flights[i]);
		}
		
		while (!pq.isEmpty()){
			int i = pq.delMin();
			List<Flight> value = result.map.get(findGate(gates,flights[i]));
			value.add(flights[i]);
		}
		
		return result;
	}
	
	//given a flight, determine the best gate solution
	//for now: assign flight to the least busy gate & least waitTimeAtGate
	public static Gate findGate(ArrayList<Gate> gates, Flight f){
		IndexMinPQ<Long> pq = new IndexMinPQ<Long>(gates.size());
		for (int i=0;i<gates.size();i++){
			pq.insert(i, gates.get(i).totalOccupiedTime());
		}
		
		long leastWaitTime = 777777;
		int gateIndex = 0;
		while (!pq.isEmpty()){
			int i = pq.delMin();
			if (gates.get(i).assignFlight(f)){
				return gates.get(i);
			}
			else {
				if (leastWaitTime < gates.get(i).waitTimeAtGate(f)){
					leastWaitTime = gates.get(i).waitTimeAtGate(f);
					gateIndex = i;
				}
			}
				
		}
		return gates.get(gateIndex);
	}
	
	public static MappingGate populateGates(ArrayList<Gate> gates, Flight[] flights, boolean restricted){
		if (!restricted) return populateGates(gates, flights);
		
		MappingGate result = new MappingGate(gates);
		
		IndexMinPQ<Flight> pq = new IndexMinPQ<Flight>(flights.length);
		for (int i=0;i<flights.length;i++){
			pq.insert(i, flights[i]);
		}
		
		while (!pq.isEmpty()){
			int i = pq.delMin();
			ArrayList<Gate> g = flights[i].getPossibleGates(gates);
			List<Flight> value = result.map.get(findGate(g,flights[i]));
			value.add(flights[i]);
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
	}

}
