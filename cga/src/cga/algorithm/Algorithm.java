package cga.algorithm;

public class Algorithm {
	
	public static void naive(){
		
	}
	public static void generalAssignment(){
		
	}
	public static void csp(){
		
	}
	public static void parse() {
		String line = "Aer Lingus,Aer Lingus,\"Dublin, IE\",Europe,133,332,1300,E ,267.0,"
		String pattern = "(.*),(.*),\"(.*)\",(.*),(.*),(.*),(.*),(.*),(,?)(.*)"
		Pattern pat = Pattern.compile(pattern)
		Matcher m = r.matcher(line);
     	if (m.find( )) {
     		System.out.println("Found value: " + m.group(0) );
	    	System.out.println("Found value: " + m.group(1) );
	    	System.out.println("Found value: " + m.group(2) );
	    	System.out.println("Found value: " + m.group(3) );
         	System.out.println("Found value: " + m.group(4) );
    	   	System.out.println("Found value: " + m.group(5) );
    	   	System.out.println("Found value: " + m.group(6) );
    	   	System.out.println("Found value: " + m.group(7) );
    	   	System.out.println("Found value: " + m.group(8) );
    	   	System.out.println("Found value: " + m.group(9) );
    	   	System.out.println("Found value: " + m.group() );
     	} else {
    	   	System.out.println("NO MATCH");
    	}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
