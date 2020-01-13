package memento;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import memento.interfaces.Caretaker;
import memento.interfaces.Originator;

public class URLCaretaker implements Caretaker {
	
	// Saves each Memento Object
	ArrayList<URLMemento> states = new ArrayList<URLMemento>();
	// Saves the String-version of each Memento Object
	ArrayList<String> statesString = new ArrayList<String>();
	

	@Override
	public void addMemento(Originator originatorPassed) {

		URLOriginator originator = 
				(URLOriginator) originatorPassed;
		URLMemento memento = 
				(URLMemento) originator.createMemento();
		
		String mementoString = memento.getState();		
		
		int index = mementoExists(mementoString);
		if(index < 0) {
			statesString.add(mementoString);
		} else {
			reinsert(mementoString,index);
		}
		
		// Saves the memento objects in case I need them later.
		saveMementos(); 
		
		try {
			write();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * It copies all the states into a Memento object
	 * and it register each Memento object in a structure.
	 */
	private void saveMementos() {
		URLMemento memento = new URLMemento();
		String urlStates = "";
		for(String state : statesString) {
			urlStates = state;
			memento.setState(urlStates);
			states.add(memento);
		}
	}

	@Override
	public String getMemento(int index) {
		return statesString.get(index);
	}
	
	/*
	 * If the Memento (url) has been loaded already,
	 * returns the location in the caretaker.
	 * If not, indicates that.
	 */
	private int mementoExists(String memento) {
		String[] mementoLines = memento.split("\\s+");
		String mementoURL = mementoLines[1];
		
		for(int index=0; index < statesString.size(); index++) {
			String[] currentMementoLines = 
					statesString.get(index).split("\\s+");
			String currentMementoURL = currentMementoLines[1];
			if(currentMementoURL.equals(mementoURL))
				return index;
		}
		return -1;
	}

	/*
	 * Inserts the modified object in its respective
	 * index in the ArrayList.
	 */
	private void reinsert(String memento, int index) {
		statesString.set(index, memento);
	}
	
	/*
	 * Writes into the file urlStates.txt
	 * In case of an interruption, this file is the one
	 * that will re-load the information.
	 */
	public void write() throws IOException {
		String urlStates = getStates();
		
		File urlStatesFile = new File(
				"/Users/adiaz/Documents/urlStates.txt");

		// if file doesnt exists, then create it
		if (!urlStatesFile.exists()) {
			urlStatesFile.createNewFile();
		}

		FileWriter fileToWrite = new FileWriter(
				urlStatesFile.getAbsoluteFile());
		BufferedWriter bufferedWriter = 
				new BufferedWriter(fileToWrite);
		bufferedWriter.write(urlStates);
		bufferedWriter.close();
	}
	
	/*
	 * Returns the String version of all the
	 * url loaded.
	 */
	private String getStates() {
		String urlStates = "";
		for(String state : statesString) {
			urlStates += state+"\n";
		}
		return urlStates;
	}
}
