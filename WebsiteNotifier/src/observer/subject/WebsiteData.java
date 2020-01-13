package observer.subject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import memento.URLMemento;
import memento.URLOriginator;
import memento.interfaces.Caretaker;
import observer.interfaces.Observer;
import observer.interfaces.Subject;

/*
 * THIS CLASS IS THE SUBJECT OF THE OBSERVER PATTERN. IT'S IN CHARGE OF
 * NOTIFYING EACH OBSERVER IN CASE OF A MODIFICATION.
 */

public class WebsiteData implements Subject {
	
	private ArrayList<Observer> observers; // Hold the Observers
	private Date modifiedDate;
	private URL url;
	private Caretaker caretaker;
	
	public WebsiteData(URL url) throws IOException {
		observers = new ArrayList<Observer>();
		this.url = url; //establish access to the information of
					   // the url
	}

	@Override
	public void registerObserver(Observer observerToAdd) {
		observers.add(observerToAdd);
	}

	@Override
	public void removeObserver(Observer observerToRemove) {
		int observerIndex = observers.indexOf(observerToRemove);
		if (observerIndex >= 0) {
		observers.remove(observerIndex); }
	}

	@Override
	public void notifyObservers() {
		for (int currentObserver = 0; 
				currentObserver < observers.size(); 
				currentObserver++) {
			Observer observer = observers.get(currentObserver);
			observer.update(modifiedDate, caretaker);
		}
	}
	
	public void websiteChanged() {
		/*
		 * Format for the Caretaker:
		 * 0 URL LASTDATE
		 */
		URLOriginator originator = new URLOriginator();
		String mementoString = 0 + " " 
							+ url + " " 
							+ modifiedDate.getTime();
		URLMemento urlMemento = new URLMemento();
		urlMemento.setState(mementoString);
		originator.setMemento(urlMemento);
		caretaker.addMemento(originator);
		notifyObservers();
	}
	
	/*
	 *  Saves the new date for this object. As well as the caretaker
	 *  of all the program. It indicates that the observers must
	 *  be notified.
	 */
	
	public void setModifiedDate(long modifiedDate,
			Caretaker caretaker) throws MalformedURLException {
		this.modifiedDate = new Date(modifiedDate);
		this.caretaker = caretaker;
		websiteChanged(); 
	}
	
	public URL getURL() { return this.url; }

}
