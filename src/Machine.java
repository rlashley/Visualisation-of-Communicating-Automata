import java.util.ArrayList;

public class Machine {

	//Fields below that hold info from parsed file.
	private ArrayList<String> transitions = new ArrayList<String>();
	private String initialState;
	
	/**
	 * Constructor to create automata, uses methods to fill in Lists and Maps from the Parsed Data
	 * @param parsedData
	 * @param initialState
	 */
	public Machine(ArrayList<String> parsedData, String initialState){
		this.transitions = parsedData;
		this.initialState = initialState;
		System.out.println("Machine has been created");
		System.out.println("Current State is " + this.initialState);
		
		//List of transitions for the machine.
		System.out.println("This machine can make the following transitions: " + transitions);
	}
	      	
}