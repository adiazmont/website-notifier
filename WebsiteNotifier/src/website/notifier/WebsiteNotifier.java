package website.notifier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import memento.interfaces.Caretaker;
import observer.observers.*;
import observer.subject.*;

/*
 * THIS CLASS IS NECESSARY TO CREATE A WEBSITE NOTIFIER FOR EACH
 * LINK IN THE FILE TO READ.
 */

public class WebsiteNotifier {
	
	WebsiteData websiteData;
	MailObserver mailObserver;
	TranscriptObserver transcriptObserver;
	URL url;
	String observers;
	Long lastModified;
	int urlID;
	
	
	// According to the parameters it will 
	//create the appropriate OBSERVERS
	public WebsiteNotifier(int urlID,URL url, String observers) 
			throws MalformedURLException {
			
		try {
			websiteData = new WebsiteData(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		String [] individualObserver = observers.split("\\s+");
		
		for(int observerIndex = 0; 
				observerIndex < individualObserver.length;
				observerIndex++) {
			
			// Save each observer in an object
			String individualObserverCopy = individualObserver[observerIndex];
			
			// creates the observer that is necessary
			if(individualObserverCopy.equals("mail")) {
				mailObserver = new MailObserver(websiteData);
			} else if(individualObserverCopy.equals("transcript")) {
				transcriptObserver = new TranscriptObserver(websiteData);
			}
		}
		
		this.urlID = urlID;
		this.url = url;
		this.observers = observers;
		
	}
	
	/*
	 * Receives the new Date (in datatype long), and the Caretaker 
	 * (to be able to save the modifications).
	 * Saves this information into the object of this class.
	 */
	public void setChanges(long time, Caretaker caretaker) 
			throws MalformedURLException {
		websiteData.setModifiedDate(time, caretaker);
		lastModified = time;
	}
	
	public String getObservers() { return observers; }
	public int getURLID() { return urlID; }
	public String getURL() { return url.toString(); }	
	
}
