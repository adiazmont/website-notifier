package observer.interfaces;
/*
 * All the observers must implement this interface
 * in order to be able to perform a specific task
 * when they are notified of something.
 */
public interface Action {
	public void performAction();
}
