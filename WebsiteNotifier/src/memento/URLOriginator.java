package memento;

import memento.interfaces.Memento;
import memento.interfaces.Originator;

/*
 * Data class to be able to manipulate the
 * Memento object.
 */
public class URLOriginator implements Originator{
	
	String state;

	@Override
	public void setMemento(Memento memento) {
		state = memento.getState();
	}

	@Override
	public Memento createMemento() {
		URLMemento memento = new URLMemento();
		memento.setState(state);
		return memento;
	}
}
