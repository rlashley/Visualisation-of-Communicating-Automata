import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileParser{	
	
	//These strings are keywords checked against lines in the document denoting where important lines begin
	private String outputs = ".outputs";
	private String end = ".end";
	private String init = ".marking";
	private String stateGraph = ".state graph";
	private String initialState;
	int machineNumber = 0;

	Map<Integer, ArrayList<String>> parsedData = new HashMap<Integer, ArrayList<String>>();
	private ArrayList<String> dataFromFile = new ArrayList<String>();
	ArrayList<Machine> machines = new ArrayList<Machine>();
	Map<String, String> initialStates = new HashMap<String, String>();
	Map<String, ArrayDeque<String>> queues = new HashMap<String, ArrayDeque<String>>();
	Configure configure = new Configure();
	
    /**
     * Converts File to an Array List before parsing
     * @param file
     * @throws IOException 
     */
    public void convertFile(File file) throws IOException {
    	ArrayList<String> list = new ArrayList<String>();
    	BufferedReader reader = new BufferedReader(new FileReader(file));
    	String line;
		while ((line = reader.readLine()) != null) {
			list.add(line);
		}
		reader.close();
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
				int mapLocation = machineNumber;
				parsedData.put(mapLocation, new ArrayList<String>());
				//Continue looping from that point until .end, assigning all strings to the properly mapped arrays
				while(!end.equals(dataFromFile.get(i))) {
					String str = dataFromFile.get(i);
					str = str.trim();
					if(str.equals(outputs) || str.equals(stateGraph)) {
					}
					else {
						parsedData.get(mapLocation).add(str);
						if(!(str.substring(0,8).equals(init))) {
						String[] halfQueue = str.split(" ");
						String name = mapLocation+halfQueue[1];
						String backName = halfQueue[1]+mapLocation;
						queues.put(name, new ArrayDeque<String>());
						queues.put(backName, new ArrayDeque<String>());
						}
					}
					i++;
				}
				i++;
				machineNumber++;
			}
		}
	}
	
	/**
	 * Creates static machines using data that has been parsed. Static machines should only hold their
	 * initial states and possible moves.
	 */
	public void machineCreation() {
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
        
	//Set configure states from initialStates
	configure.setStates(initialStates);
	//Set queue for configure from parsedData
	configure.setQueue(queues);
	//Send built machines to configure for processing
	configure.setMachines(machines);
	configure.successors();
	}	
}