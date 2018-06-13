import java.util.ArrayDeque;
import java.util.ArrayList;

public class Machine {

	//Fields below that hold info from parsed file.
	private ArrayList<String> transitions = new ArrayList<String>();
	private String initialState;
	
	//This will be the queue in the machine that will hold next moves after user has been prompted
	private ArrayDeque<String> queue = new ArrayDeque<String>();
	
	/**
	 * Constructor to create automata, uses methods to fill in Lists and Maps from the Parsed Data
	 * @param parsedData
	 * @param initialState
	 */
	public Machine(ArrayList<String> parsedData, String initialState){
		this.transitions = parsedData;
		this.initialState = initialState;
		
		System.out.println("Machine has been created");
		System.out.println("Initial State is " + this.initialState);
		System.out.println("The following transitions can occur " + transitions);
	}
	

}
