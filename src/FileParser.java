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
	private int machineNumber = 0;

	private Map<Integer, ArrayList<String>> parsedData = new HashMap<Integer, ArrayList<String>>();
	private ArrayList<String> dataFromFile = new ArrayList<String>();
	private Map<String, ArrayDeque<String>> queues = new HashMap<String, ArrayDeque<String>>();
	
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
	
	public Map<String, ArrayDeque<String>> getQueues() {
		return queues;
	}
	
	public Map<Integer, ArrayList<String>> getParsedData() {
		return parsedData;
	}
	
}