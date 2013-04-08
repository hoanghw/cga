package cga.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cga.flight.Flight;
import cga.flight.Gate;

public class Util {
	
	public static MappingGate populateGates(Gate[] gates, Flight[] flights){
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
	
	public static Gate findGate(Gate[] gates, Flight flight){
		
		return new Gate(); //not done
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
