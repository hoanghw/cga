package cga.algorithm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cga.flight.*;

public class MappingGate {
	
	Date datetime;
	
	public HashMap<Gate,List<Flight>> map;
	
	public MappingGate(HashMap<Gate,List<Flight>> m){
		map = m;
	}
	public MappingGate(Gate[] gates){
		for (int i = 0; i<gates.length;i++){
			map.put(gates[i], new ArrayList<Flight>());
		}
	}
	
	public int totalUtility(){
		return 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
