package website.notifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import memento.*;


/*
 * THIS IS THE MAIN CLASS OF THE PROGRAM
 * Reads the main fail (urls.txt) with the information about
 * each url and its observers.
 * Restores the program to its last modified dates, and
 * indicate to send notifications to all the observers
 * that haven't been notified about a change.
 */

public class WebsiteNotifierCreator {
	
	// Simply access to each URL object and its 
	// observers.
	static ArrayList<WebsiteNotifier> links = 
			new ArrayList<WebsiteNotifier>();
	// Access to the performance of each URL object
	static ArrayList<StateTracking> threads = 
			new ArrayList<StateTracking>();
	static URLCaretaker caretaker = new URLCaretaker();

	public static void main (String[] args) {
		try {
			URLOriginator originator = new URLOriginator();
			long lastModifiedDate = 0;
			int urlID = 0;
			boolean fileExists = false;
			boolean savedInCaretaker = false;
			
			/*
			 * CHECK IF THE FILE TO SAVE EXISTS, AND IF IT DOES
			 * ASSIGNS THE LAST-MODIFIED-DATE TO EACH URL
			 */
			File urlStatesFile = 
					new File("/Users/adiaz/Documents/urlStates.txt");
			if(urlStatesFile.exists())
				fileExists = true;
			
			String currentLine;
			// Creates a way to access the file
			FileInputStream accessToFile = 
					new FileInputStream("/Users/adiaz/Documents/urls.txt"); 
			// Creates a way so the Program understands the file
			InputStreamReader fileStream = 
					new InputStreamReader(accessToFile, "UTF-8"); 
			// Creates a way to manipulate the content of the file
			BufferedReader fileToRead = 
					new BufferedReader(fileStream); 
			while ((currentLine = fileToRead.readLine()) != null) {
				// separates each segment of each line
				String [] elements = currentLine.split("\\s+"); 
				
			    String st = "";
			    for(int x=1; x<elements.length; x++) {
			    	// gets the "chain code", which are the observers
			    	st += elements[x] + " ";	
			    }
			    String url = elements[0];
			    URL realURL = new URL(url);
			    URLConnection urlConnection = realURL.openConnection();
			    lastModifiedDate = urlConnection.getLastModified();
				
				if(!fileExists) {
					/*
					 * Format for Caretaker:
					 * 1 URL DATE
					 */
					String mementoState = 1 + " " 
										+ url + " " 
										+ lastModifiedDate;
					URLMemento mementoObject = new URLMemento();
					mementoObject.setState(mementoState);
					    
					originator.setMemento(mementoObject);
					caretaker.addMemento(originator);
				} else {
					if(!savedInCaretaker) {
						// Saves each line in urlStates into the Caretaker
						String currentLineStates;
						// Creates a way to access the file
						FileInputStream accessToFileStates = 
								new FileInputStream(
										"/Users/adiaz/Documents/urlStates.txt"); 
						// Creates a way so the Program understands the file
						InputStreamReader fileStreamStates = 
								new InputStreamReader(accessToFileStates, "UTF-8"); 
						// Creates a way to manipulate the content of the file
						BufferedReader fileToReadStates = 
								new BufferedReader(fileStreamStates); 
						while ((currentLineStates = fileToReadStates.readLine()) 
								!= null) {
							saveIntoCaretaker(currentLineStates);
						}
						fileToReadStates.close();
						savedInCaretaker = true;
					}
				}
				
				/*
				 * Creates a WebsiteNotifier for each link
				 * with its respective observers
				 */
				
			    WebsiteNotifier link = new WebsiteNotifier(urlID,realURL,st); 
			    links.add(link);										
			    
			    StateTracking thread = new StateTracking(link, urlID);
			    threads.add(thread);
			    
			    urlID++;
			}
			fileToRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * CALL FOR RESTORE()
		 */
		restore();
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		StateTracking threadx = threads.get(1);
		threadx.setChanges(System.currentTimeMillis(), caretaker);
		
	}
	
	/*
	 * In case the program failed in sending the 
	 * notifications, it will
	 * do this un-done task.
	 */
	public static void restore() {
		String sCurrentLine;
		
		try {
			// Creates a way to access the file
			FileInputStream accessToFile = 
					new FileInputStream(
							"/Users/adiaz/Documents/urlStates.txt"); 
			// Creates a way so the Program understands the file
			InputStreamReader fileStream = 
					new InputStreamReader(accessToFile, "UTF-8"); 
			// Creates a way to manipulate the content of the file
			BufferedReader fileToRead = 
					new BufferedReader(fileStream); 
			
			while ((sCurrentLine = fileToRead.readLine()) != null) {
				// separates each segment of each line
				String [] elements = sCurrentLine.split("\\s+"); 
				
			    String notified = elements[0];
			    
			    int isNotified = Integer.parseInt(notified);
			    if(isNotified == 0){
			    	String url = elements[1];
				    String lastDate = elements[2];
			    	// synchronized this method
				    notify(url,lastDate);
			    }
			}
			fileToRead.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Receives the URL with its respective last date of modification.
	 * then, it looks for the Thread that is holding that task and
	 * set the changes to it.
	 */
	public synchronized static void notify(String url, String lastDate) {
		int index = 0;
		long lastModifiedDate = Long.parseLong(lastDate);
		
		for(int count=0; count < links.size(); count++) {
			String currentURL = links.get(count).getURL();
			if(currentURL.equals(url)) {
				index = count;
				break;
			}
		}
		StateTracking threadToNotify = threads.get(index);
		threadToNotify.setChanges(lastModifiedDate, caretaker);
	}
	
	/*
	 * It charges the current urls in urlStates file into the
	 * Caretaker.
	 */
	public static void saveIntoCaretaker(String mementoState) {
		URLOriginator originator = new URLOriginator();
		URLMemento mementoObject = new URLMemento();
		mementoObject.setState(mementoState);
		    
		originator.setMemento(mementoObject);
		caretaker.addMemento(originator);
	}

}
