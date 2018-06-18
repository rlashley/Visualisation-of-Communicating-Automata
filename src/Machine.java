import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Machine {

	//Fields below that hold info from parsed file.
	private ArrayList<String> transitions = new ArrayList<String>();
	private String initialState;
	private String currentState;
	
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
		System.out.println("Current State is " + this.initialState);
		currentState = initialState;
		System.out.println("This machine can make the following moves: " + transitions);

	}
	
	/**
	 * This method prompts user for the next state, adds in to the queue, and makes the transition if possible.
	 */
	public void successors() {
		String exit = "no";
		String nextLine = null;

		while(!(exit.toLowerCase().equals("yes"))) {
	        System.out.println("What transition would you like to make? ");
	        Scanner scan = new Scanner(System.in);
	        nextLine = scan.nextLine();	 
	        
	        for(int i=0; i < transitions.size(); i++) { 
	        	if(nextLine.equals(transitions.get(i))) {
	        		System.out.println("Transition has been found, adding to queue");
	        	    queue.add(transitions.get(i));
	        	}	        	
	        
	        }
	        
	        if(!(queue.isEmpty())) {
		        System.out.println("The next item in the queue is " + queue.element());
	        
		        //Check if current state is able to move to queued state
		        String test = queue.element();
		        String array[] = test.split(" ");
		        test = array[0];
		        if(test.equals(currentState) && array[2].equals("!")) {
	        		currentState = array[4];
	        		System.out.println("Transition from queue successful");
	        		queue.remove();
		        }
	        }
	        
	        System.out.println("The current state is " + currentState + " and there are " + queue.size() + " transitions waiting in the queue.");
	        
	        System.out.println("Would you like to change to another machine? Yes to change.");
	        if(scan.hasNextLine()) {
	        	nextLine = scan.next();
	        }
	        exit = nextLine;
	        
		}
	}        	
}
