import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileParser{	
	
	//These strings are keywords checked against lines in the document denoting where important lines begin
	private String outputs = ".outputs";
	private String end = ".end";
	private String init = ".marking";
	private String initialState;
	
	private ArrayList<String> parsedData = new ArrayList<String>();
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
						initialState = str;
					}
					else{
						parsedData.add(str.replaceAll("\\s", ""));
					}
					i++;
				}						
			}
		}
		System.out.println(parsedData);
		Machine machine = new Machine(parsedData, initialState);
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
	public ArrayList<String> getStatesToLoad() {
		return parsedData;
	}
	
}