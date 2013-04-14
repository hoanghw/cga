import cga.*
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    File file;	

    public static void main(String[] args) throws FileNotFoundException {
	Parser parser = new Parser("BOS.txt");
	parser.parse();
    }

    public Parser(String fileName){
	file = new File(fileName);
    }

    public Flight[] parse() throws FileNotFoundException {
	Scanner scanner = new Scanner(new FileReader(file));
	Flight[] flights = new Flight[];

	String line = scanner.nextLine(); //Kill the first line
	String pattern ="([^,]*),([^,]*),\"(.*)\",([^,]*),([^,]*),([^,]*),([^,]*),([^,]*)(,*)([^,]*),?";
	Pattern r = Pattern.compile(pattern);
	Matcher m = r.matcher(line);

	try {
	    while (scanner.hasNextLine()){
		line = scanner.nextLine();
		m = r.matcher(line);
		if (m.find( )) {
		    flight = new Flight();
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

		} else {
		    System.out.println("NO MATCH");
		}
	    }
	}
	finally {
	    scanner.close();
	}
    }


}
