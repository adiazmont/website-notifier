package observer.observers;

import java.net.URL;
import java.util.Date;
import javax.swing.JOptionPane;
import memento.URLMemento;
import memento.URLOriginator;
import memento.interfaces.Caretaker;
import observer.interfaces.Action;
import observer.interfaces.Observer;
import observer.interfaces.Subject;
import observer.subject.WebsiteData;

public class TranscriptObserver implements Observer,Action {
	
	private Subject websiteData;
	private Date modifiedDate;
	private URL url;
	private Caretaker caretaker;
	
	/*
	 * Attaches itself to the respective Subject class.
	 */
	public TranscriptObserver(Subject websiteData) {
		this.websiteData = websiteData;
		this.websiteData.registerObserver(this);
	}

	@Override
	public void performAction() {
		// SENDS A STRING
		if(websiteData instanceof WebsiteData){
			url = ((WebsiteData) websiteData).getURL();
		}
		String transcript = "The website: " + url +
				" has been updated.\n"
				+ "Last modified date: " + modifiedDate;
		
		JOptionPane.showMessageDialog (null, transcript,
				"New Modification", JOptionPane.INFORMATION_MESSAGE);
		
		URLOriginator originator = new URLOriginator();
		String mementoString = 1 + " " 
							+ url + " " 
							+ modifiedDate.getTime();
		URLMemento urlMemento = new URLMemento();
		urlMemento.setState(mementoString);
		originator.setMemento(urlMemento);
		caretaker.addMemento(originator);
	}

	@Override
	public void update(Date modifiedDate, Caretaker caretaker) {
		this.modifiedDate = modifiedDate;
		this.caretaker = caretaker;
		performAction();
	}

}
