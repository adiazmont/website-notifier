package memento.interfaces;

/*
 * This interface is the Caretaker of the Memento 
 * Design Pattern. It's in charge of keeping track
 * of the information.
 * It saves the information in a data file.
 */
public interface Caretaker {
	
	public void addMemento(Originator memento);
	public String getMemento(int index);
	
}
