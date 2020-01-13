package memento;

import memento.interfaces.Memento;

/*
 * Data class to have a reference of each 
 * Memento object.
 */
public class URLMemento implements Memento {
	
	String state;

	@Override
	public void setState(String state) {
		this.state = new String(state);
	}

	@Override
	public String getState() {
		return state;
	}

}
