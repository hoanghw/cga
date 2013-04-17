package cga.algorithm;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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
		HashMap<Gate,Integer> occupancy = new HashMap<Gate,Integer>(); //K,V: Gate g, Total Flights assigned
		HashMap<Gate,Integer> numUngated = new HashMap<Gate,Integer>(); //K,V: Gate g, Total Flights ungated
		ArrayList<Long> flightStat = new ArrayList<Long>();
		ArrayList<Long> gateStat = new ArrayList<Long>();
		
		int arrivalCount = 0; //late arrival count
		long arrivalDelay = 0; //delay arrival in minutes 
		
		long totalDelay = 0;
		int totalUngated = 0;
		int totalFlights = 0;
		for (Map.Entry<Gate, List<Flight>> entry : map.entrySet()) {
		    Gate gate = entry.getKey();
		    List<Flight> flights = entry.getValue();
		    /*Flight[] copyFlights = new Flight[flights.size()];
		    for (int i = 0; i<flights.size(); i++){
		    	copyFlights[i] = Flight.copy(flights.get(i));
		    }
		    Quick.sort(copyFlights);
		    long totalDelay = 0; //minutes
		    for (int i = 0;i<copyFlights.length-1;i++){
		    	long minutesDelay = (copyFlights[i+1].realATime.getTime() - copyFlights[i].realDTime.getTime())/60000;
		    	if (minutesDelay < 0){
		    		totalDelay += minutesDelay;
		    		copyFlights[i+1].realATime = new Date(copyFlights[i+1].realATime.getTime() - minutesDelay*60000);
		    		copyFlights[i+1].realDTime = new Date(copyFlights[i+1].realDTime.getTime() - minutesDelay*60000);
		    	}
		    }
		    */
		    Collections.sort(flights);
		    long delayAtGate = 0; //minutes
		    int ungatedAtGate = 0;
		    flightStat.add((long) 0);
		    for (int i = 0;i<flights.size()-1;i++){
		    	long minutesDelay = (flights.get(i+1).realATime.getTime() - flights.get(i).realDTime.getTime())/60000;
		    	if (minutesDelay < 0){
		    		delayAtGate += minutesDelay;
		    		flights.get(i+1).realATime = new Date(flights.get(i+1).realATime.getTime() - minutesDelay*60000);
		    		flights.get(i+1).realDTime = new Date(flights.get(i+1).realDTime.getTime() - minutesDelay*60000);
		    		flights.get(i+1).delay = minutesDelay;
		    		ungatedAtGate++;
		    		flightStat.add(minutesDelay);
		    		if (flights.get(i+1).isArrival()){
		    			arrivalCount ++;
		    			arrivalDelay += minutesDelay;
		    		}
		    	}else{
		    		flightStat.add((long) 0);
		    	}
		    }
		    
		    /*
		    System.out.println(""+gate": ");
		    for (int i = 0; i<flights.size(); i++){
		    	System.out.println("\t"+flights.get(i));
		    }*/
		    delay.put(gate,delayAtGate);
		    occupancy.put(gate,flights.size());
		    numUngated.put(gate,ungatedAtGate);
		    totalDelay +=delayAtGate;
		    totalUngated +=ungatedAtGate;
		    totalFlights +=flights.size();
		    gateStat.add(delayAtGate);
		}
		
		System.out.println("Total Delay, "+totalDelay);
		System.out.println("Total Flights, "+totalFlights);
		System.out.println("Total Ungated Flights, "+totalUngated);
		//System.out.println("Gates occupancy = "+occupancy);
		Statistics stat = new Statistics(gateStat);
		System.out.println("Gates Delay Mean, "+stat.getMean());
		System.out.println("Gates Delay Variance, "+stat.getVariance());
		System.out.println("Gates Delay StdDev, "+stat.getStdDev());
		stat = new Statistics(flightStat);
		System.out.println("Flights Ungated Mean, "+stat.getMean());
		System.out.println("Flights Ungated Variance, "+stat.getVariance());
		System.out.println("Flights Ungated StdDev, "+stat.getStdDev());
		System.out.println("Ungated Arrivals, "+arrivalCount);
		System.out.println("Total Delay Arrivals, "+arrivalDelay);
		return delay;
	}
	
	@Override
	public String toString(){
		String s = "";
		for (Map.Entry<Gate, List<Flight>> entry : map.entrySet()) {
			Gate gate = entry.getKey();
		    List<Flight> flights = entry.getValue();
		    s+=gate+": ";
		    for (int i=0;i<flights.size();i++){
		    	s+="\t"+flights.get(i)+"\n";
		    }
			s+="\n";
		}
		return s;
	}
	
	public int totalUtility(){
		return 0;
	}
	
	public static void main(String[] args) {
		
		ArrayList<Gate> gates = new ArrayList<Gate>();

		
		double percentShared = 0; //default; do nothing
		percentShared = 4;
		uniformShare(gates, percentShared);
		//smallShare(gates);
		//termAShare(gates);
		//int addGates = 15; addShare(gates, addGates);
		
		Parser parser = new Parser("C:\\Users\\Alex Cuevas\\Documents\\GitHub\\cga\\cga\\src\\cga\\DEN 8-16-12_Big_edited.csv");
		ArrayList<Flight> f = new ArrayList<Flight>();
		try {
			f = parser.parseDenverBig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MappingGate m = new MappingGate(gates);
		m = Util.populateGates(gates, f, true);
		m.calDelay();
	}
	
	public static void denGateSetup(ArrayList<Gate> gates, int UAL, int SWA, int FFT, int DAL, int AAL, int USA, int VOI, int JBU_VOI, int small, int NKS, int TRS_SWA, int ASA, int BA_ICE) {
		for (int i=0; i<UAL;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("SKW");a.add("UAL");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");
			Gate g = new Gate("ual"+i,a);
			gates.add(g);
		}
		for (int i=0; i<SWA; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("SWA");
			Gate g = new Gate("swa"+i,a);
			gates.add(g);
		}
		for (int i=0; i<FFT; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("FFT");a.add("RPA");
			Gate g = new Gate("fft"+i,a);
			gates.add(g);
		}
		for (int i=0; i<DAL;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("DAL");
			Gate g = new Gate("dal"+i,a);
			gates.add(g);
		}
		for (int i=0; i<AAL; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("EGF");a.add("AAL");
			Gate g = new Gate("aal"+i,a);
			gates.add(g);
		}
		for (int i=0; i<USA;i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("USA");
			Gate g = new Gate("usa"+i,a);
			gates.add(g);
		}
		for (int i=0; i<VOI; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("VOI");
			Gate g = new Gate("voi"+i,a);
			gates.add(g);
		}
		for (int i=0; i<JBU_VOI; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("JBU");a.add("VOI");
			Gate g = new Gate("jbu-voi"+i,a);
			gates.add(g);
		}
		for (int i=0; i<NKS; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("NKS");
			Gate g = new Gate("nks"+i,a);
			gates.add(g);
		}
		for (int i=0; i<TRS_SWA; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("TRS");//a.add("SWA");
			Gate g = new Gate("trs-swa"+i,a);
			gates.add(g);
		}
		for (int i=0; i<ASA; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");
			Gate g = new Gate("asa"+i,a);
			gates.add(g);
		}
		for (int i=0; i<BA_ICE; i++){
			ArrayList<String> a = new ArrayList<String>();
			Gate g = new Gate("ba-ice"+i,a);
			gates.add(g);
		}
		for (int i=0; i<small; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("CCI");
			Gate g = new Gate("small-"+i,a);
			gates.add(g);
		}
	}
	public static void doNothing(ArrayList<Gate> gates) {
		int UAL = 42;
		int SWA = 17;
		int FFT = 14;
		int DAL = 5;
		int AAL = 3;
		int USA = 2;
		int VOI = 1;
		int JBU_VOI = 1;
		int small = 2; //AMX,ACA,ICE,DLH,CCI
		int NKS = 1;
		int TRS_SWA = 2;
		int ASA = 1;
		int BA_ICE = 1;
		
		denGateSetup(gates, UAL, SWA, FFT, DAL, AAL, USA, VOI, JBU_VOI, small, NKS, TRS_SWA, ASA, BA_ICE);
	}
	public static void uniformShare(ArrayList<Gate> gates, double percent) {
		percent *= .01;
		int UAL 		= (int) Math.round(42*(1-percent));
		int SWA 		= (int) Math.round(17*(1-percent));
		int FFT 		= (int) Math.round(14*(1-percent));
		int DAL 		= (int) Math.round( 5*(1-percent));
		int AAL 		= (int) Math.round( 3*(1-percent));
		int USA 		= (int) Math.round( 2*(1-percent));
		int VOI 		= (int) Math.round( 1*(1-percent));
		int JBU_VOI 	= (int) Math.round( 1*(1-percent));
		int small 		= (int) Math.round( 2*(1-percent)); //AMX,ACA,ICE,DLH,CCI
		int NKS 		= (int) Math.round( 1*(1-percent));
		int TRS_SWA 	= (int) Math.round( 2*(1-percent));
		int ASA 		= (int) Math.round( 1*(1-percent));
		int BA_ICE 		= (int) Math.round( 1*(1-percent));
		
		int shared = 92 - (UAL+SWA+FFT+DAL+AAL+USA+VOI+JBU_VOI+small+NKS+TRS_SWA+ASA+BA_ICE);
		denGateSetup(gates, UAL, SWA, FFT, DAL, AAL, USA, VOI, JBU_VOI, small, NKS, TRS_SWA, ASA, BA_ICE);
		
		for (int i=0; i<shared; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");a.add("SKW");a.add("UAL");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");a.add("EGF");a.add("AAL");
			a.add("NKS");a.add("JBU");a.add("VOI");a.add("CCI");a.add("FFT");a.add("DAL");a.add("GLA");a.add("SWA");a.add("TRS");
			a.add("USA");
			Gate g = new Gate("share-"+i,a);
			gates.add(g);
		}
	}
	public static void smallShare(ArrayList<Gate> gates) {
		int UAL = 42;
		int SWA = 17;
		int FFT = 14;
		int DAL = 5;
		int AAL = 0;
		int USA = 0;
		int VOI = 0;
		int JBU_VOI = 0;
		int small = 0; //AMX,ACA,ICE,DLH,CCI
		int NKS = 0;
		int TRS_SWA = 0;
		int ASA = 0;
		int BA_ICE = 0;
	
		int small_shared = 14;
		denGateSetup(gates, UAL, SWA, FFT, DAL, AAL, USA, VOI, JBU_VOI, small, NKS, TRS_SWA, ASA, BA_ICE);
		
		for (int i=0; i<small_shared; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");a.add("SKW");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");a.add("EGF");a.add("AAL");
			a.add("NKS");a.add("JBU");a.add("VOI");a.add("CCI");a.add("GLA");a.add("TRS");a.add("USA");
			Gate g = new Gate("small_share-"+i,a);
			gates.add(g);
		}
	}
	public static void termAShare(ArrayList<Gate> gates) {
		int UAL = 42;
		int SWA = 17;
		int FFT = 0;
		int DAL = 5;
		int AAL = 0;
		int USA = 2;
		int VOI = 0;
		int JBU_VOI = 0;
		int small = 0; //AMX,ACA,ICE,DLH,CCI
		int NKS = 0;
		int TRS_SWA = 0;
		int ASA = 0;
		int BA_ICE = 0;
	
		int term_A_shared = 26;
		denGateSetup(gates, UAL, SWA, FFT, DAL, AAL, USA, VOI, JBU_VOI, small, NKS, TRS_SWA, ASA, BA_ICE);
		
		for (int i=0; i<term_A_shared; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");a.add("SKW");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");a.add("EGF");a.add("AAL");
			a.add("NKS");a.add("JBU");a.add("VOI");a.add("CCI");a.add("FFT");a.add("GLA");a.add("TRS");
			Gate g = new Gate("term_A_shared-"+i,a);
			gates.add(g);
		}
	}
	public static void addShare(ArrayList<Gate> gates, int add) {
		
		int UAL = 42;
		int SWA = 17;
		int FFT = 14;
		int DAL = 5;
		int AAL = 3;
		int USA = 2;
		int VOI = 1;
		int JBU_VOI = 1;
		int small = 2; //AMX,ACA,ICE,DLH,CCI
		int NKS = 1;
		int TRS_SWA = 2;
		int ASA = 1;
		int BA_ICE = 1;
		
		int shared = add;
		denGateSetup(gates, UAL, SWA, FFT, DAL, AAL, USA, VOI, JBU_VOI, small, NKS, TRS_SWA, ASA, BA_ICE);
		
		for (int i=0; i<shared; i++){
			ArrayList<String> a = new ArrayList<String>();
			a.add("ASA");a.add("SKW");a.add("UAL");a.add("ASQ");a.add("RPA");a.add("TCF");a.add("GJS");a.add("EGF");a.add("AAL");
			a.add("NKS");a.add("JBU");a.add("VOI");a.add("CCI");a.add("FFT");a.add("DAL");a.add("GLA");a.add("SWA");a.add("TRS");
			a.add("USA");
			Gate g = new Gate("share-"+i,a);
			gates.add(g);
		}
	}
	
}
