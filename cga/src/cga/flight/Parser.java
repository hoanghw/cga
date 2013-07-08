package cga.flight;

import cga.*;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    File file;	

    public static void main(String[] args) throws FileNotFoundException {
    	Parser parser = new Parser("/home/hoang/git/cga/cga/src/cga/DEN_8-16-12_Big.csv");
    	ArrayList<Flight> flights = parser.parseDenverBig();
    	System.out.printf("First Flight ID: %d\n", flights.get(1).id);
    }

    public Parser(String fileName){
    	file = new File(fileName);
    }

    /*public ArrayList<Flight> parseBoston() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		String pattern ="([^,]*),([^,]*),\"(.*)\",([^,]*),([^,]*),([^,]*),([^,]*),([^,]*)(,*)([^,]*),?";
		
		  m.group(1) = Carrier
		  m.group(2) = Operator
		  m.group(3) = Origin
		  m.group(4) = Continent
		  m.group(5) = Flight Number
		  m.group(6) = Equip Type
		  m.group(7) = Arrival Time
		  m.group(8) = Dest Term
		  m.group(9) = Inbound / Outbound comma
		  m.group(10) = Pax
    	
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);

		try {
		    while (scanner.hasNextLine()){
		    	line = scanner.nextLine();
				m = r.matcher(line);
				if (m.find( )) {
				    //Flight flight = new Flight(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9), m.group(10));
					flights.add();
				} else {
				    System.out.println("NO MATCH; error!");
				}
		    }
		}
		finally {
			scanner.close();
		}
		
		return flights;
	}*/
/***
	public ArrayList<Flight> parseDenver() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		String pattern ="(.*),(.*),(.*),(.*),(.*):?(.*),(.*),(.*),(.*),(.*),(.*)";
		
		,Carrier,Dep Arpt,Arr Arpt,Gate Time,Dir,Slack,Gate Dep Dly,Gate Arr Dly,Carrier Delay
		  m.group(1) = Carrier #
		  m.group(2) = Carrier ID Code
		  m.group(3) = Departure Airport
		  m.group(4) = Arrival Airport
		  m.group(5) = Arrival Time Hour
		  m.group(6) = Arrival Time Minute
		  m.group(7) = Direction (IN or OUT)
		  m.group(8) = Gate Departure Delay
		  m.group(9) = Gate Arrival Delay
		  m.group(10) = Carrier Delay
    	
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);

		try {
		    while (scanner.hasNextLine()){
		    	line = scanner.nextLine();
				m = r.matcher(line);
				if (m.find( )) {
					Airline airline = new Airline(m.group(2));
					Airport airport = new Airport(m.group(4));
					long aTime = Integer.parseInt(m.group(5))*60 + Integer.parseInt(m.group(6));
				    Flight flight = new Flight(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9), m.group(10));
					flights.add(flight);
				} else {
				    System.out.println("NO MATCH; error!");
				}
		    }
		}
		finally {
			scanner.close();
		}
		
		return flights;
	}
	***/
	/* Returns military time, delimited by :, in minutes since midnight*/
	public static int parseTime(String time) {
		String pattern = "(.*):(.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(time);

		if (m.find( )) {
			return Integer.parseInt(m.group(1))*60 + Integer.parseInt(m.group(2));
		} else {
			System.out.println("NO MATCH; error!");
		}
		return 0;
	}	
	
	public static int parseHour(String time){
		return Integer.parseInt(time.split(":")[0]);
	}
	public static int parseMin(String time){
		return Integer.parseInt(time.split(":")[1]);
	}

	public ArrayList<Flight> parseDenverBig() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		line = scanner.nextLine(); //Kill the second line
		line = scanner.nextLine(); //Kill the third line
		String pattern ="(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*),(.*)";
		/*
		  m.group(1) = Carrier code
		  m.group(2) = Flight ID
		  m.group(3) = Arrival?
		  m.group(4) = Departure Airport
		  m.group(5) = Arrival Airport
		  m.group(6) = equipType
		  m.group(7) = Sched Gate Out
		  m.group(8) = Actual Gate Out
		  m.group(9) = Actual Wheels Off
		  m.group(10) = Taxi Out
		  m.group(11) = Sched Gate In
		  m.group(12) = Actual Gate In
		  m.group(13) = Actual Wheels On
		  m.group(14) = Taxi In
		  m.group(15) = Gate Departure Delay
		  m.group(16) = Taxi Out Delay
		  m.group(17) = Airport Departure Delay
		  m.group(18) = Taxi In Delay
		  m.group(19) = Gate Arrival Delay
    	*/
		Pattern r = Pattern.compile(pattern);
		Matcher m;

		try {
		    while (scanner.hasNextLine()){
		    	line = scanner.nextLine();
				m = r.matcher(line);
				if (m.find( )) {
					String name = m.group(1);
					Airline airline = new Airline(name);
					int id = Integer.parseInt(m.group(2));
					Boolean isArrival = m.group(3).equals("1"); // Else, departure!
					String equipType = m.group(6);
					
					//normal distribution of turn-around time
					Random rnd = new Random();
					int mins = (int) rnd.nextGaussian()*15+60; //mean=60 mins, std=15 mins
					if (name.equals("DAL"))
						mins = (int) rnd.nextGaussian()*10+30;
					else if (name.equals("SWA"))
						mins = (int) rnd.nextGaussian()*5+20;
					
					Date dTime, aTime;
					if (!isArrival) {
						int schedGateOut = parseTime(m.group(7));
						int actGateOut = parseTime(m.group(8));
						int actWheelsOff = parseTime(m.group(9));
						int taxiOut = Integer.parseInt(m.group(10));
						int gateDepartDelay = Integer.parseInt(m.group(15));
						//int taxiOutDelay = Integer.parseInt(m.group(16));
						int airportDepartDelay = Integer.parseInt(m.group(17));

						GregorianCalendar aCalendar = new GregorianCalendar(2012,8,16,parseHour(m.group(8)),parseMin(m.group(8))); //try 7 | 8
						dTime = aCalendar.getTime();
						aTime = new Date(dTime.getTime() - 60000*mins);

					} else { 
						int schedGateIn = parseTime(m.group(11));
						int actGateIn = parseTime(m.group(12));
						int actWheelsOn = parseTime(m.group(13));
						//int taxiIn = m.group(14);
						//int taxiInDelay = m.group(18);
						//int gateArrivalDelay = m.group(19);

						GregorianCalendar aCalendar = new GregorianCalendar(2012,8,16,parseHour(m.group(12)),parseMin(m.group(12))); //try 11 | 12
						aTime = aCalendar.getTime();
						dTime = new Date(aTime.getTime() + 60000*mins);
					}

				    Flight flight = new Flight(id, aTime, dTime, airline);
				    flight.setArrival(isArrival);
					flights.add(flight);

				} else {
				    System.out.println("NO MATCH; error!");
				}
		    }
		}
		finally {
			scanner.close();
		}
		
		return flights;
	}

	/*public ArrayList<Flight> parseGates() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		String pattern ="([^,]*),([^,]*),\"(.*)\",([^,]*),([^,]*),([^,]*),([^,]*),([^,]*)(,*)([^,]*),?";
		
		  m.group(1) = Carrier
		  m.group(2) = Operator
		  m.group(3) = Origin
		  m.group(4) = Continent
		  m.group(5) = Flight Number
		  m.group(6) = Equip Type
		  m.group(7) = Arrival Time
		  m.group(8) = Dest Term
		  m.group(9) = Inbound / Outbound comma
		  m.group(10) = Pax
    	
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);

		try {
		    while (scanner.hasNextLine()){
		    	line = scanner.nextLine();
				m = r.matcher(line);
				if (m.find( )) {
				    Flight flight = new Flight(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9), m.group(10));
					flights.add(flight);
				} else {
				    System.out.println("NO MATCH; error!");
				}
		    }
		}
		finally {
			scanner.close();
		}
		
		return flights;
	}*/
}
