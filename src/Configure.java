import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
		

		while(!(exit.toLowerCase().equals("yes"))) {
	        System.out.println("Choose a state to load");
	        //Print out the states possible for each machine
        	ArrayList<String> tempTrans = machines.get(nextInt).getTransitions();
        	//This loops through the list of transitions of the machine and pulls possible transitions from its current state
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
	        //Checks if what was entered by user matches one of the possible transitions
	        if(listOfCurrentTransitions.contains(nextLine)) {
	        	//Load choice into proper queue
	        	String[] apart = nextLine.split(" ");
	        	String queueName = Integer.toString(nextInt)+apart[1];
	        	//This creates a bound on the amount of transitions that can be in the queue
	        	if(queue.get(queueName).size()<5) {
	        		queue.get(queueName).add(nextLine);
	        	}
	        	else {
	        		System.out.println("There are too many items in the queue. Most recent choice will not enter queue.");
	        	}
		        nextInt++;
	        }
	        else {
	        	System.out.println("Your choice is not in the list of possible transactions.");
	        }
	        //Counts up to a single queue selection for each machine before automatically firing transitions.
	        if(nextInt == machines.size()) {
	        	String[] swap = null;
	        	String[] apart = null, apart2 = null;
	        	String compare, compare2;
	        	
	        	//Compare items in queue to see if transitions can go through.
	        	for(String key : queue.keySet()) {
	        		swap = key.split("");
	        		String key2 = swap[1]+swap[0];
	        		if((!queue.get(key).isEmpty()) && (!queue.get(key2).isEmpty())) {
	        			compare = queue.get(key).getFirst();
		        		apart = compare.split(" ");
		        		compare2 = queue.get(key2).getFirst();
		        		apart2 = compare2.split(" ");
	        		
		        		if(apart[1].equals(swap[1]) && apart2[1].equals(swap[0])) {
		        			if( (apart[2].equals("!") && apart2[2].equals("?")) ) {
		        				if(apart[3].equals(apart2[3])) {
		        					//Assign message to proper machine
		        					machines.get(Integer.parseInt(swap[1])).saveMessages(apart[3]);
		        					System.out.println("Message from machine " + swap[0] + " has been delivered to machine " + swap[1]);
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
	        		
	        	}
		        for(String key : queue.keySet()) {
		        	if(!queue.get(key).isEmpty()) {
		        		System.out.println("Queue for machine " + key.charAt(0) + " is not empty. The following transitions can't occur. " + queue.get(key).toString());
		        	}
		        }
		        nextInt = 0;  
	        }
	        listOfCurrentTransitions.clear();
		}
	}		
}

class SecondScene {
	
	public SecondScene() 
	{
		Stage subStage = new Stage();
		subStage.setTitle("");
		Label runningText = new Label();
		Button runButton = new Button("Click to run");
		runButton.setPrefSize(200, 80);
		HBox hbox = new HBox(runningText, runButton);
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(15));
		Scene scene2 = new Scene(hbox, 400, 400);
		subStage.setScene(scene2);
		subStage.show();
	}	
}
