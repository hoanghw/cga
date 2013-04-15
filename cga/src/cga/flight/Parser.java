package cga.flight;

import cga.*;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    File file;	

    public static void main(String[] args) throws FileNotFoundException {
    	Parser parser = new Parser("C:\\Users\\Alex Cuevas\\Documents\\GitHub\\cga\\cga\\src\\cga\\flight\\BOS.txt");
    	ArrayList<Flight> flights = parser.parse();
    	System.out.printf("First Flight ID: %d\n", flights.get(1).id);
    }

    public Parser(String fileName){
    	file = new File(fileName);
    }

    public ArrayList<Flight> parseBoston() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		String pattern ="([^,]*),([^,]*),\"(.*)\",([^,]*),([^,]*),([^,]*),([^,]*),([^,]*)(,*)([^,]*),?";
		/*
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
    	*/
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
	}

	public ArrayList<Flight> parseDenver() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(file));
		ArrayList<Flight> flights = new ArrayList<Flight>();

		String line = scanner.nextLine(); //Kill the first line
		String pattern ="(.*),(.*),(.*),(.*),(.*):?(.*),(.*),(.*),(.*),(.*),(.*)";
		/*
		,Carrier,Dep Arpt,Arr Arpt,Gate Time,Dir,Slack,Gate Dep Dly,Gate Arr Dly,Carrier Delay
		  m.group(1) = Carrier #
		  m.group(2) = Carrier ID Code
		  m.group(3) = Departure Airport
		  m.group(4) = Arrival Airport
		  m.group(5) = Arrival Time Hour
		  m.group(6) = Arrival Time Minute
		  m.group(7) = Direction (IN or OUT)
		  m.group(8) = Slack (?)
		  m.group(9) = Gate Departure Delay
		  m.group(10) = Gate Arrival Delay
		  m.group(11) = Carrier Delay
    	*/
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);

		try {
		    while (scanner.hasNextLine()){
		    	line = scanner.nextLine();
				m = r.matcher(line);
				if (m.find( )) {
					Airline airline = new Airline(m.group(2));
					Airport airport = new Airport(m.group(4));
					long aTime = m.group(5)
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
}
