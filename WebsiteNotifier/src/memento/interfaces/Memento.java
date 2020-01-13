package memento.interfaces;
/*
 * This interface is in charge of defining the 
 * Memento object of the Memento Design Pattern.
 */
public interface Memento {
	public void setState(String state);
	public String getState();
}
