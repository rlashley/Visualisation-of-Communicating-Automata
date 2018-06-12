import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Machine {

	private ArrayList<String> parsedData = new ArrayList<String>();
	
	//Fields below that hold info from parsed file.
	ArrayList<String> states = new ArrayList<String>();
	Map<String, ArrayList<String>> transitions = new HashMap<String, ArrayList<String>>();
	Map<Integer, String> message = new HashMap<Integer, String>();
	
	/**
	 * Constructor to create automata, uses methods to fill in Lists and Maps from the Parsed Data
	 * @param parsedData
	 * @param initialState
	 */
	public Machine(ArrayList<String> parsedData){
		this.parsedData = parsedData;
		listOfStates();
		createTransitionTable();
		
		System.out.println("Automata has been created");
		System.out.println("The following states exist " + states);
		System.out.println("The following transitions can occur for each state " + transitions);
	}
	

	
	/**
	 * Names of automata states get pulled from string of data and placed in arraylist
	 */
	private void listOfStates() {
		for (int i = 0; i < parsedData.size(); i++) {
			String str = parsedData.get(i);
			String str2 = parsedData.get(i);
			str = str.substring(0, 2);
			str2 = str2.substring(str2.length()-2, str2.length());
			if(!states.contains(str)){
				states.add(str);
			}
			if(!states.contains(str2)){
				states.add(str2);
			}
		}
	}
	
	/**
	 * Method returns initial state which is listed after .marking
	 * @return
	 */

	
	/**
	 * Transitions show communication between states to be pulled from parsed data. 
	 * Key is number of state. Value is another map containing send/receive, message and next state.
	 */
	private void createTransitionTable() {
		for(String state : states) { // for each unique state in states
			  if (!transitions.containsKey(states)) {
				  transitions.put(state, new ArrayList<String>());
			  }
			}
		
		for (int i = 0; i < parsedData.size(); i++) {
			String str = parsedData.get(i);
			String str2 = parsedData.get(i);
			str = str.substring(0, 2);
			str2 = str2.substring(3, 7);
			if(transitions.containsKey(str)) {
				transitions.get(str).add(str2);
			}
		}
		
	}
	
}
