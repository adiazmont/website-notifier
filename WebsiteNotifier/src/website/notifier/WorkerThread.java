package website.notifier;
import java.net.MalformedURLException;
import memento.interfaces.Caretaker;

public class WorkerThread extends Thread {
	
	WebsiteNotifier theNotifier;
	long modifiedDate;
	boolean changedState;
	Caretaker caretaker;
	
	/*
	 * Saves the websiteNotifier into an object so
	 * it can keep track of it.
	 */
	public WorkerThread(WebsiteNotifier websiteNotifier) {
		theNotifier = websiteNotifier;
		changedState = false;
		setDaemon(false);
	}
	
	/*
	 * Runs indefinitely checking if a changed has been done.
	 * If not, the thread sleeps for a specific amount of time
	 * and then checks again.
	 */
	public synchronized void run() {
		while(true){
			if(changedState) {
				try {
					// Sends the notifier in order to make changes
					// to it.
					theNotifier.setChanges(modifiedDate, caretaker);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				changedState = false;
			}
			try {
				sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Receives the information that a modification has been done.
	 */
	public void setChanges(long time, Caretaker caretaker) {
		modifiedDate = time;
		changedState = true;
		this.caretaker = caretaker;
	}

}
