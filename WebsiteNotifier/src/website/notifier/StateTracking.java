package website.notifier;
import memento.interfaces.Caretaker;

/*
 * Initializes the Daemon(thread) that will
 * keep track of the state of the url.
 */
public class StateTracking {
	
	WorkerThread tracker;
	
	public StateTracking(WebsiteNotifier websiteNotifier, int id) {
		tracker = new WorkerThread(websiteNotifier);
		tracker.setName(Integer.toString(id));
		tracker.start();
	}
	
	public void setChanges(long time, Caretaker caretaker) {
		tracker.setChanges(time, caretaker);
	}
}
