import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FxApp extends Application {
	
	//Creates FileParser object with methods that alter the incoming Array of Strings into the format we need
	FileParser fileParser = new FileParser();

    private String randTransition = null;
	private int nextInt = 0;
	private String[] swap = null;
	private String[] apart = null, apart2 = null;
	private String compare, compare2;
	private String queueName, key2;
	private boolean transferCheck;
	
	private Random rand = new Random();
	private ArrayList<String> listOfCurrentTransitions = new ArrayList<String>();
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	private Map<String, String> initialStates = new HashMap<String, String>();
	private Map<String, String> states = new HashMap<String, String>();
	private Map<String, ArrayDeque<String>> queues = new HashMap<String, ArrayDeque<String>>();
	private Map<Integer, ArrayList<String>> parsedData = new HashMap<Integer, ArrayList<String>>();
	
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("File Chooser");
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
        
        Button startButton = new Button("Start");
        Button openButton = new Button("Click to open a file...");
        openButton.setPrefSize(200, 80);
        Button queueButton = new Button("Restart");
        
        TextArea textArea = new TextArea();
        textArea.setMinSize(400, 300);
        textArea.setWrapText(true);
        
        Label lbl = new Label();
        VBox vbox = new VBox(lbl, openButton, startButton, textArea, queueButton);  
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));
        lbl.setText("This tool creates virtual automata\r based on a loading file.");
        
        Scene scene = new Scene(vbox, 640, 520);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Opens and loads the file
        openButton.setOnAction(new EventHandler <ActionEvent>() 
        {
        	public void handle(ActionEvent e) {
            	File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    //Execute the method to convert to string array before sending to file parser                        	
                    try {
						fileParser.convertFile(file);							
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
            }
        });    
        
        //Starts the program running
        startButton.setOnAction(new EventHandler <ActionEvent>()
        {
        	public void handle(ActionEvent event) {
        		if(machines.isEmpty()) {
                	machineCreation();
        		}
        		states = initialStates;       		

        	    for(int k = 0; k < machines.size(); k++) {
                	listOfCurrentTransitions = machines.get(nextInt).successors(states.get(Integer.toString(k)));
                	//This loops through the list of transitions of the machine and pulls possible transitions from its current state
        	        if(!(listOfCurrentTransitions.isEmpty())) {
        	        	textArea.appendText("The following transitions are possible for machine " + k + ". \n" + listOfCurrentTransitions);
						//Create random number and pull transition in that position of list
        	        	randomGenerator();
        	        	textArea.appendText("\n" + randTransition + " has been added to the queue.\n");
        	        }     	
        	        else {
        	        	textArea.appendText("\nNo transitions for machine " + k + " exist from its current state \n");
        	        }
        	        //Checks the first character of the transition and loads choice into proper queue
        	        addTransitionsToQueue();
        	        if(queues.get(queueName).size()>=5) {
        	        	textArea.appendText("There are too many items in the queue. Most recent choice will not enter queue. \n");
        	        }
        		    nextInt++;     	        
            	    listOfCurrentTransitions = null;        	    
        	    }      	    
        	    	//Counts up to a single queue selection for each machine before automatically firing transitions.
        	        if(nextInt == machines.size()) {
        	        	//Compare matching queues to see if transitions can go through.
        	        	for(String key : queues.keySet()) {
        	        		transferBetweenQueues(key);
        	        		if(transferCheck == true) {
        		        		textArea.appendText("\nMessage from machine " + swap[0] + " has been delivered to machine " + swap[1]);
        		        		updateMachines(key);
        	        		}
        	        		if(!(queues.get(key).isEmpty()) && !(queues.get(key2).isEmpty())) {
        	        			if(!(apart[2].equals(apart2[2]))) 
        	        				textArea.appendText("\nMessages in queue did not match");	        					
        	        			if(!(apart[1].equals("!") && apart2[1].equals("?"))) 
        	        				textArea.appendText("\nSend/Receive states did not match");
        	        		}
        	        	}
        	        }
        	        	//Checks all queues and warns if transitions haven't occurred.
        		        for(String key : queues.keySet()) {
        		        	if(!queues.get(key).isEmpty()) {
        		        		textArea.appendText("\nQueue for machine " + key.charAt(0) + " is not empty. The following transitions can't occur. \n" + queues.get(key).toString());
        		            }
        		        }
        		        nextInt = 0;
        		}     		
        });
        
        queueButton.setOnAction(new EventHandler <ActionEvent>() 
        {
        	public void handle(ActionEvent event) {
        		textArea.clear();
        		for(String key : queues.keySet()) {
		        	if(!queues.get(key).isEmpty()) {
		        		queues.get(key).clear();
		            }
        		}
		        for(int i = 0; i < machines.size(); i++) {
		        	initialStates.put((Integer.toString(i)),machines.get(i).getInitialState());
		        }
            }
        });
    }
    
    private void randomGenerator() {
    	int ranNumber = rand.nextInt(listOfCurrentTransitions.size());
    	randTransition = listOfCurrentTransitions.get(ranNumber);
    }
    
    private void addTransitionsToQueue() {
        String[] apart = randTransition.split(" ");
        queueName = Integer.toString(nextInt)+apart[0];
        //This creates a bound on the amount of transitions that can be in the queue
        if(queues.get(queueName).size()<5) {
        	queues.get(queueName).add(randTransition);
        }
    }
    
    private void transferBetweenQueues(String key) {
    	transferCheck = false;
		swap = key.split("");
		key2 = swap[1]+swap[0];
		if((!queues.get(key).isEmpty()) && (!queues.get(key2).isEmpty())) {
			compare = queues.get(key).getFirst();
    		apart = compare.split(" ");
    		compare2 = queues.get(key2).getFirst();
    		apart2 = compare2.split(" ");
		
    		if(apart[0].equals(swap[1]) && apart2[0].equals(swap[0])) {
    			if(apart[1].equals("!") && apart2[1].equals("?")) {
    				if(apart[2].equals(apart2[2])) {
    					//Assign message to proper machine
    					machines.get(Integer.parseInt(swap[1])).saveMessages(apart[2]);
    					transferCheck = true;
    					
    				}
    			}
    		}
		}
    }
    
    private void updateMachines(String key) {
		//Update states of machines
		states.put(swap[0], apart[3]);
		states.put(swap[1], apart2[3]);
		//Removes from the queue the successful transitions.
		queues.get(key).remove();
		queues.get(key2).remove();
    }

	/**
	 * Creates static machines using data that has been parsed. Static machines should only hold their
	 * initial states and possible moves.
	 */
	public void machineCreation() {
		queues = fileParser.getQueues();
		parsedData = fileParser.getParsedData();
        //Iterate through each entry in the Map, creating a machine and adding it to an Array list called machines
        for(Integer key : parsedData.keySet()){
        	int k = parsedData.get(key).size();
        	String initialState = parsedData.get(key).get(k-1);
        	String[] chop = initialState.split(" ");
        	initialState = chop[1];
        	initialStates.put(Integer.toString(key), initialState);
        	parsedData.get(key).remove(k-1);      
        	
        	machines.add(new Machine(parsedData.get(key), initialState));
        }
	}
}
