import java.util.ArrayList;

public class Machine {

	//Fields below that hold info from parsed file.
	private ArrayList<String> transitions = new ArrayList<String>();
	private String initialState;
	private ArrayList<String> messages = new ArrayList<String>();
	
	/**
	 * Constructor to create automata, uses methods to fill in Lists and Maps from the Parsed Data
	 * @param parsedData
	 * @param initialState
	 */
	public Machine(ArrayList<String> parsedData, String initialState){
		this.transitions = parsedData;
		this.initialState = initialState;
		
		//List of transitions for the machine.
	}
	
	public ArrayList<String> getTransitions(){
		return transitions;
	}
	
	public void saveMessages(String message) {
		this.messages.add(message);
	}
	      	
}