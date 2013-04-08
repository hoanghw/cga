package cga.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cga.algorithm.MappingGate;
import cga.algorithm.Quick;
import cga.flight.Flight;
import cga.flight.Gate;

public class ReportDelay {
	public MappingGate mg;
	
	public ReportDelay(MappingGate _mg){
		mg=_mg;
	}

	public void calDelay(){
		HashMap<Gate,Long> delay = new HashMap<Gate,Long>(); //K,V: Gate g, Total Delay at Gate g
		for (Map.Entry<Gate, List<Flight>> entry : mg.map.entrySet()) {
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
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
