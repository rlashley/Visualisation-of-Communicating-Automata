import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Configure {

	//This will be the queue in the machine, with keys representing every possible direction of communication.
	private Map<String, ArrayDeque<String>> queue = new HashMap<String, ArrayDeque<String>>();
	//Holds all machine states
	private Map<String, String> states = new HashMap<String, String>();
	//Holds pre-built machines for execution of the program
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	
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
		int nextInt = 0;
		int count = 0;
		

		while(!(exit.toLowerCase().equals("yes"))) {
	        System.out.println("Choose a state to load");
	        //Print out the states possible for each machine
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
	        	String[] swap;
	        	String[] apart = null, apart2 = null;
	        	String compare, compare2;
	        	
	        	//Compare items in queue to see if transitions can go through.
	        	for(String key : queue.keySet()) {
	        		swap = key.split("");
	        		String key2 = swap[1]+swap[0];
	        		if(!queue.get(key).isEmpty()) {
	        			compare = queue.get(key).getFirst();
		        		apart = compare.split(" ");
		        	}
	        		if(!queue.get(key2).isEmpty()) {
	        		compare2 = queue.get(key2).getFirst();
	        		apart2 = compare2.split(" ");
	        		}
	        		if(apart[1].equals(swap[1]) && apart2[1].equals(swap[0])) {
	        			if( (apart[2].equals("!") && apart2[2].equals("?")) ) {
	        				if(apart[3].equals(apart2[3])) {
	        					System.out.println("Message from machine " + swap[0] + " has been delivered to machine " + swap[1]);
	        					//Assign apart[3] to swap[1]
	        					machines.get(Integer.parseInt(swap[1])).saveMessages(apart[3]);
	        					//Update states of machines
	        					states.put(swap[0], apart[4]);
	        					states.put(swap[1], apart2[4]);
	        					//Removes from the queue the successful transitions.
	        					queue.get(key).remove();
	        					queue.get(key2).remove();

	        				}
	        				
	        				else {
	        					System.out.println("Messages in queue did not match");
	        				}
	        				
	        			}	
	        			else {
	        				System.out.println("Send/Receive states did not match");
	        			}
	        			
	        		}
	        		
	        	}
		        for(String key : queue.keySet()) {
		        	if(!queue.get(key).isEmpty()) {
		        		System.out.println("Queue " + key + " is not empty. It has the following transitions " + queue.get(key).toString());
		        	}
		        }
		        nextInt = 0;
		        count = 0;    
	        }
	        listOfCurrentTransitions.clear();

		}
	}  
}
