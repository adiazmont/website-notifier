package observer.interfaces;

import java.util.Date;
import memento.interfaces.Caretaker;
/*
 * The observers implement this interface in order
 * to behave in a similar way.
 */
public interface Observer {
	public void update(Date modifiedDate, Caretaker caretaker);
}
