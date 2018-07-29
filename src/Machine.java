import java.util.ArrayList;
import java.util.HashMap;

public class Machine {

	//Fields below that hold info from parsed file.
	private HashMap<String, ArrayList<String>> mappedTransitions = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> messages = new ArrayList<String>();
	
	/**
	 * Constructor to create automata, uses methods to fill in Lists and Maps from the Parsed Data
	 * @param parsedData
	 * @param initialState
	 */
	public Machine(ArrayList<String> parsedData){
		convertTransitionsToMap(parsedData);
	}

	/**
	 * This method will convert machine transitions into a map, with the key being the state.
	 */
	public void convertTransitionsToMap(ArrayList<String> transitions) {
		for(int i = 0; i < transitions.size(); i++) {
			String[] stateName = transitions.get(i).split(" ");
			if(!mappedTransitions.containsKey(stateName[0])) {
				mappedTransitions.put(stateName[0], new ArrayList<String>());
			}
			String transition = transitions.get(i).replace(stateName[0], "");
			transition = transition.trim();
			mappedTransitions.get(stateName[0]).add(transition);
		}
	}
	
	public ArrayList<String> successors(String currentState){
		//Check current state and return transitions based on that		
		return mappedTransitions.get(currentState);
	}
	
	public void saveMessages(String message) {
		this.messages.add(message);
	}
	      	
}