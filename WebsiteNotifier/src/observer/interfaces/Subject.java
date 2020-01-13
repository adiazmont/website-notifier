package observer.interfaces;

/*
 * Each Subject (url-instance) implement this interface
 * to be in contact with its observers.
 */
public interface Subject {
	
	public void registerObserver(Observer observerToAdd);
	public void removeObserver(Observer observerToRemove);
	public void notifyObservers();

}
