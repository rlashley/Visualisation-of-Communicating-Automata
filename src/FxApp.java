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

    private String text = null;
	private String initialState;
	int nextInt = 0;
	
	Random rand = new Random();
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
        Button textButton = new Button("Clear Queues and Restart Program");
        
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        
        Label lbl = new Label();
        VBox vbox = new VBox(lbl, openButton, startButton, textArea, textButton);  
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(15));
        lbl.setText("This tool creates virtual automata\r based on the file.");
        
        Scene scene = new Scene(vbox, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();  
        
        openButton.setOnAction(new EventHandler<ActionEvent>() 
        {
                @Override
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
        
        startButton.setOnAction(new EventHandler <ActionEvent>()
        {
            public void handle(ActionEvent event)
            {
            	machineCreation();
        		states = initialStates;       		
        	    //textArea.appendText("Choose a state to load \n");
        	    for(int k = 0; k < machines.size(); k++) {
        	        //Print out the states possible for each machine
                	ArrayList<String> tempTrans = machines.get(nextInt).getTransitions();
                	//This loops through the list of transitions of the machine and pulls possible transitions from its current state
        	        for(int i = 0; i < tempTrans.size(); i++) {
        	        	String pull = tempTrans.get(i);
        	        	String[] apart = pull.split(" ");
        	        	pull = apart[0];
        	        	//If first word in the string is equal to the first word in the current states, add to list of transitions
        	        	if(states.get(Integer.toString(nextInt)).equals(pull)) {
        	        		listOfCurrentTransitions.add(tempTrans.get(i));
        	        	}
        	        }
        	        if(!(listOfCurrentTransitions.isEmpty())) {
        	        	textArea.appendText("The following transitions are possible. \n" + listOfCurrentTransitions);
        	        	int randTransition = rand.nextInt(listOfCurrentTransitions.size());
        	        	text = listOfCurrentTransitions.get(randTransition);
        	        	textArea.appendText(text + " has been added to the queue.\n");
        	        }     	
        	        else {
        	        	textArea.appendText("No transitions for this machine exist from its current state \n");
        	        }

        	        //Checks if what was chosen by computer is proper selection and places it in the queue
        	        if(listOfCurrentTransitions.contains(text)) {
        	        	//Load choice into proper queue
        	        	String[] apart = text.split(" ");
        	        	String queueName = Integer.toString(nextInt)+apart[1];
        	        	//This creates a bound on the amount of transitions that can be in the queue
        	        	if(queues.get(queueName).size()<6) {
        	        		queues.get(queueName).add(text);
        	        	}
        	        	else {
        	        		textArea.appendText("There are too many items in the queue. Most recent choice will not enter queue. \n");
        	        	}
        		        nextInt++;
        	        }
        	        else {
        	        	textArea.appendText("Your choice is not in the list of possible transactions. \n");
        	        }
            	    listOfCurrentTransitions.clear();
        	    }
        	    
        	        //Counts up to a single queue selection for each machine before automatically firing transitions.
        	        if(nextInt == machines.size()) {
        	        	String[] swap = null;
        	        	String[] apart = null, apart2 = null;
        	        	String compare, compare2;
        	        	
        	        	//Compare items in queue to see if transitions can go through.
        	        	for(String key : queues.keySet()) {
        	        		swap = key.split("");
        	        		String key2 = swap[1]+swap[0];
        	        		if((!queues.get(key).isEmpty()) && (!queues.get(key2).isEmpty())) {
        	        			compare = queues.get(key).getFirst();
        		        		apart = compare.split(" ");
        		        		compare2 = queues.get(key2).getFirst();
        		        		apart2 = compare2.split(" ");
        	        		
        		        		if(apart[1].equals(swap[1]) && apart2[1].equals(swap[0])) {
        		        			if( (apart[2].equals("!") && apart2[2].equals("?")) ) {
        		        				if(apart[3].equals(apart2[3])) {
        		        					//Assign message to proper machine
        		        					machines.get(Integer.parseInt(swap[1])).saveMessages(apart[3]);
        		        					textArea.appendText("Message from machine " + swap[0] + " has been delivered to machine " + swap[1]);
        		        					//Update states of machines
        		        					states.put(swap[0], apart[4]);
        		        					states.put(swap[1], apart2[4]);
        		        					//Removes from the queue the successful transitions.
        		        					queues.get(key).remove();
        		        					queues.get(key2).remove();

        		        				}		        				
        		        				else {
        		        					textArea.appendText("Messages in queue did not match");
        		        				}	        				
        		        			}	
        		        			else {
        		        				textArea.appendText("Send/Receive states did not match");
        		        			}
        		        		}
        	        		}
        	        		
        	        	}
        		        for(String key : queues.keySet()) {
        		        	if(!queues.get(key).isEmpty()) {
        		        		textArea.appendText("Queue for machine " + key.charAt(0) + " is not empty. The following transitions can't occur. " + queues.get(key).toString());
        		        	}
        		        }
        		        nextInt = 0;  
        	        }
        		}     		
        });
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
        	initialState = parsedData.get(key).get(k-1);
        	String[] chop = initialState.split(" ");
        	initialState = chop[1];
        	initialStates.put(Integer.toString(key), initialState);
        	parsedData.get(key).remove(k-1);      
        	
        	machines.add(new Machine(parsedData.get(key), initialState));
        }
	}	
}
