import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Configure {

	//This will be the queue in the machine, with keys representing every possible direction of communication.
	Map<String, ArrayDeque<String>> queue = new HashMap<String, ArrayDeque<String>>();
	//Holds all machine states
	Map<String, String> states = new HashMap<String, String>();
	
	//Constructor
	public Configure(){	
	}
	
	public Map<String, ArrayDeque<String>> getQueue() {
		return queue;
	}

	//Write method to build queue from data passed from FileParser
	public void setQueue(Map<String, ArrayDeque<String>> queues) {
		this.queue = queues;
		System.out.println(queue.keySet());
	    }

	public Map<String, String> getStates() {
		return states;
	}

	//Method to build states list from data passed from FileParser
	public void setStates(Map<String, String> initialStates) {
		this.states = initialStates;
		System.out.println(initialStates.keySet());
	}


	/**
	 * This method prompts user, letting them know which states are possible from current 
	 **/
//		public void successors() {
//		String exit = "no";
//		String nextLine = null;

//		while(!(exit.toLowerCase().equals("yes"))) {
//	        System.out.println("What transition would you like to make? ");
//	        Scanner scan = new Scanner(System.in);
//	        nextLine = scan.nextLine();	 
	        
//	        for(int i=0; i < transitions.size(); i++) { 
//	        	if(nextLine.equals(transitions.get(i))) {
//	        		System.out.println("Transition has been found, adding to queue");
//	        	    queue.add(transitions.get(i));
//	        	}	        	
	        
//	        }
	        
//	        if(!(queue.isEmpty())) {
//		        System.out.println("The next item in the queue is " + queue.element());
	        
		        //Check if current state is able to move to queued state
//		        String test = queue.element();
//		        String array[] = test.split(" ");
//		        test = array[0];
//		        if(test.equals(currentState) && array[2].equals("!")) {
//	        		currentState = array[4];
//	        		System.out.println("Transition from queue successful");
//	        		queue.remove();
//		        }
//	        }	        
//		}
//	}  
}
