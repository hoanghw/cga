package cga.algorithm;
import java.io.FileNotFoundException;
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
		long total = 0;
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
		    total +=totalDelay;
		}
		System.out.println("Total delay for all gates = "+total);
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
		/*Flight[] flights = new Flight[10];
		
		for (int i=0; i<flights.length;i++){
			GregorianCalendar start = new GregorianCalendar(2013,12,31,20-i,0+2*i);
			GregorianCalendar end = new GregorianCalendar(2013,12,31,21-i,0);
			flights[i] = new Flight(i,start.getTime(),end.getTime());
		}*/
		
		ArrayList<Gate> gates = new ArrayList<Gate>();

		for (int i=0; i<46;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("SKW");a.add("UAL");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");
			Gate g = new Gate("un-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<17; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("SWA");
			Gate g = new Gate("sw-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<16; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("FFT");a.add("RPA");
			Gate g = new Gate("ft-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<6;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("DAL");
			Gate g = new Gate("dt-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<3; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("EGF");a.add("AAL");
			Gate g = new Gate("aa-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<2;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("USA");
			Gate g = new Gate("us-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<2;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("GLA");
			Gate g = new Gate("gl-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<1; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("VOI");
			Gate g = new Gate("vo-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<1; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("JBU");
			Gate g = new Gate("jb-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<1; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("NKS");
			Gate g = new Gate("sp-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<1; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("TRS");
			Gate g = new Gate("at-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<1; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");
			Gate g = new Gate("al-"+i,a);
			gates.add(g);
		}
		for (int i=0; i<5; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");a.add("SKW");a.add("UAL");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");a.add("EGF");a.add("AAL");
			a.add("NKS");a.add("JBU");a.add("VOI");a.add("CCI");a.add("FFT");a.add("DAL");a.add("GLA");a.add("SWA");a.add("TRS");
			a.add("USA");
			Gate g = new Gate("share-"+i,a);
			gates.add(g);
		}
		
		Parser parser = new Parser("/home/hoang/git/cga/cga/src/cga/DEN_8-16-12_Big.csv");
		ArrayList<Flight> f = new ArrayList<Flight>();
		try {
			f = parser.parseDenverBig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MappingGate m = new MappingGate(gates);
		m = Util.populateGates(gates, f, true);
		System.out.println(m);
		System.out.println("Total delay at gate = "+m.calDelay());
	}

}
