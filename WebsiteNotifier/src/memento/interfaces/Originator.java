package memento.interfaces;
/*
 * This is the interface for the Originator in the
 * Memento Design Pattern.
 */
public interface Originator {
	public void setMemento(Memento memento);
	public Memento createMemento();
}
