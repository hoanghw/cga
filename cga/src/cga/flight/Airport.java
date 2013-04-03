public class Airport{
		String name;
		Gate[] gates;
		Flight[] flights;
		Hashmap<Flight,Gate> gateMapping;

	public Airport(String _name, Gate[] _gates){
		name = _name;
		gates = _gates.copy();
	}
		/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}