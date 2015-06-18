package nl.tudelft.ti2806.pl1.gui.observable;

import java.util.Collection;

public interface ChosenObservable {

	/**
	 * Registers an observer
	 * 
	 * @param o
	 *            The observer which is registered
	 */
	public void registerObserver(ChosenObserver o);

	/**
	 * Deletes an observer
	 * 
	 * @param o
	 *            The observer which is deleted
	 */
	public void deleteObserver(ChosenObserver o);

	/**
	 * Notify all registered observers.
	 * 
	 * @param chosen
	 *            The new list of highlighted sources
	 */
	public void notifyObservers(Collection<String> chosen);
}
