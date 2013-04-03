public class Airline {
	String name;

	public Airline(String _name){
		name = _name;
	}

	public String getName(){
		return name;
	}

	public Gate[] getGates(Airport airport){
		allGates = airport.getGates()
		Gate[] airportGates;
		for(int i = 0, i < length(allGates), i++) {
			if allGates[i].getAirline.equals(name) {
				airportGates.append(allgates[i])
			}
		}
	}
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}