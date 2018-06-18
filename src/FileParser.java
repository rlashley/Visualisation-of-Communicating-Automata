import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileParser{	
	
	//These strings are keywords checked against lines in the document denoting where important lines begin
	private String outputs = ".outputs";
	private String end = ".end";
	private String init = ".marking";
	private String stateGraph = ".state graph";
	private String initialState;

	
	Map<String, ArrayList<String>> parsedData = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> dataFromFile = new ArrayList<String>();
	ArrayList<Machine> machines = new ArrayList<Machine>();
	ArrayList<String> initialStates = new ArrayList<String>();
	ArrayList<String> machineNames = new ArrayList<String>();
	
    /**
     * Converts File to an Array List before parsing
     * @param file
     */
    public void convertFile(File file) {
    	ArrayList<String> list = new ArrayList<String>();
    	BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	String line;
    	try {
			while ((line = reader.readLine()) != null) {
			    list.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	this.dataFromFile = list;
		parseTheData();
    }
	
    /**
     * Parses the data in the ArrayList and loads automata from that data
     */
	private void parseTheData() {

		for (int i = 0; i < dataFromFile.size(); i++) {		

			//Checks if the word .outputs is next in the list, assigns state to its own arraylist
			if(outputs.equals(dataFromFile.get(i))) {
				String mapLocation = dataFromFile.get(i+2);
				String apart[] = mapLocation.split(" ");
				mapLocation = apart[0];
				mapLocation = mapLocation.substring(0, mapLocation.length() - 1);
				parsedData.put(mapLocation, new ArrayList<String>());
				//Continue looping from that point until .end, assigning all strings to the properly mapped arrays
				while(!end.equals(dataFromFile.get(i))) {
					String str = dataFromFile.get(i);
					if(str.equals(outputs) || str.substring(0,8).equals(init) || str.equals(stateGraph)) {
					}
					else {
						parsedData.get(mapLocation).add(str);
					}
					i++;
				}
				i++;
			}
		}
		machineCreation();
	}
	
	/**
	 * Returns array of initial states
	 * @return
	 */
	public ArrayList<String> getInitialStates() {
		return initialStates;
	}
	
	/**
	 * Prints out value of parsedData array
	 */
	public void printLoadedStates() {
		System.out.println(parsedData);
	}
	
	/**
	 * Returns parsedData array
	 * @return
	 */
	public Map<String, ArrayList<String>> getStatesAndTransitions() {
		return parsedData;
	}
	
	public ArrayList<Machine> getMachines(){
		return machines;
	}
	
	/**
	 * Creates static machines using data that has been parsed. Then runs the program
	 */
	private void machineCreation() {
		
		//Prompt if user would like to create machines
        System.out.println("Would you now like to create machines for the data that has been pulled from the file?");
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        if(nextLine.toLowerCase().equals("yes") || nextLine.toLowerCase().equals("y")) {
        	//Iterate through each entry in the Map, creating a machine and adding it to an Array list called machines
        	for(String key : parsedData.keySet()){
        		initialState = parsedData.get(key).get(0);
        		String apart[] = initialState.split(" ");
        		initialState = apart[0];
        		machineNames.add(initialState.substring(0, initialState.length()-1));
        		machines.add(new Machine(parsedData.get(key), initialState));
        	}
        }
        
        //Loops and runs program
        String end = "no";
        while( (!end.toLowerCase().equals("yes")) || (!end.toLowerCase().equals("y")) ) {
        System.out.println("What machine would you like to change the state of?");
        for(int k=0; k < machineNames.size(); k++) {
        	System.out.println(machineNames.get(k));
        }
        nextLine = scanner.nextLine();
        int nextInt = machineNames.indexOf(nextLine);
        machines.get(nextInt).successors();
        
        System.out.println("Would you like to quit?");
        end = scanner.nextLine();
        
        }
	}
	
}