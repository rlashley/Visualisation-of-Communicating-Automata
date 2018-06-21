import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configure {

	//This will be the queue in the machine, with keys representing every possible direction of communication.
	Map<String, ArrayDeque<String>> queue = new HashMap<String, ArrayDeque<String>>();
	//Holds all machine states
	Map<String, String> states = new HashMap<String, String>();
	//Holds pre-built machines for execution of the program
	ArrayList<Machine> machines = new ArrayList<Machine>();
	
	//Constructor
	public Configure(){	
	}
	
	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}
	
	public Map<String, ArrayDeque<String>> getQueue() {
		return queue;
	}

	//Write method to build queue from data passed from FileParser
	public void setQueue(Map<String, ArrayDeque<String>> queues) {
		this.queue = queues;
	    }

	public Map<String, String> getStates() {
		return states;
	}

	//Method to build states list from data passed from FileParser
	public void setStates(Map<String, String> initialStates) {
		this.states = initialStates;
	}


	/**
	 * This method runs the program loop 
	 **/
		public void successors() {
		String exit = "no";
		String nextLine = null;
		ArrayList<String> listOfCurrentTransitions = new ArrayList<String>();
		int nextInt;
		int count = 0;
		

		while(!(exit.toLowerCase().equals("yes"))) {
	        System.out.println("Choose a state to load");
	        //Print out the machines and the states possible
	        nextInt = 0;
        	ArrayList<String> tempTrans = machines.get(nextInt).getTransitions();
        	//This loops through the list of transitions of the machine and pulls possible things that can happen from it's current state
	        for(int i = 0; i < tempTrans.size(); i++) {
	        	String pull = tempTrans.get(i);
	        	String[] apart = pull.split(" ");
	        	pull = apart[0];
	        	if(states.get(Integer.toString(nextInt)).equals(pull)) {
	        		listOfCurrentTransitions.add(tempTrans.get(i));
	        	}
	        }
	        if(!(listOfCurrentTransitions.isEmpty())) {
	        	System.out.println("The following transitions are possible. Choose one: " + listOfCurrentTransitions);
	        }     	
	        else {
	        	System.out.println("No transitions for this machine exist from its current state");
	        }
	        Scanner scan = new Scanner(System.in);
	        nextLine = scan.nextLine();
	        if(listOfCurrentTransitions.contains(nextLine)) {
	        	//Load choice into proper queue
	        	String[] apart = nextLine.split(" ");
	        	String queueName = Integer.toString(nextInt)+apart[1];
	        	queue.get(queueName).add(nextLine);
		        count++;
		        nextInt++;
	        }
	        else {
	        	System.out.println("Your choice is not in the list of possible transactions.");
	        }
	        //Counts up to a single queue selection for each machine before automatically firing transitions.
	        if(count == machines.size()) {
	        	//Compare items in queue to see if transitions can go through. If not, send error to user.
	        }

	        listOfCurrentTransitions.clear();
		}
	}  
}
