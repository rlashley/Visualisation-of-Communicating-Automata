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
	//Loop through parse starting at [0]
		for (int i = 0; i < dataFromFile.size(); i++) {		

			//Checks if the word .outputs is next in the list, assigns state to its own arraylist
			if(outputs.equals(dataFromFile.get(i))) {
				
				//Continue looping from that point until .end, assigning anything that starts with q to its own string
				while(!end.equals(dataFromFile.get(i))) {
					
					String str = dataFromFile.get(i);
					//If line is .marking, save the initial state
					if(str.substring(0,8).equals(init)) {
						initialState = str.substring(9, str.length());
						parsedData.put(initialState, new ArrayList<String>());
					
					}
					i++;
				}													
			}
		}
		for (int i = 0; i < dataFromFile.size(); i++) {		

			//Checks if the word .outputs is next in the list, assigns state to its own arraylist
			if(outputs.equals(dataFromFile.get(i))) {
				String mapLocation = dataFromFile.get(i+2);
				String apart[] = mapLocation.split(" ", 5);
				mapLocation = apart[0];
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
		printStatesToLoad();
        System.out.println("Would you now like to create machines for the data that has been pulled from the file?");
        Scanner scanner = new Scanner(System.in);
        String nextLine = scanner.nextLine();
        scanner.close();
        if(nextLine.toLowerCase().equals("yes") || nextLine.toLowerCase().equals("y")) {

    		//Machine machine = new Machine(name of key, array of data for key);
        }

	}
	
	/**
	 * Returns initial state
	 * @return
	 */
	public String getInitialState() {
		return initialState;
	}
	
	/**
	 * Prints out value of parsedData array
	 */
	public void printStatesToLoad() {
		System.out.println(parsedData);
	}
	
	/**
	 * Returns parsedData array
	 * @return
	 */
	public Map<String, ArrayList<String>> getStatesAndTransitions() {
		return parsedData;
	}
	
}