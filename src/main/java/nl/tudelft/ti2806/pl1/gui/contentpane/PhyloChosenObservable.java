package nl.tudelft.ti2806.pl1.gui.contentpane;

import java.util.Collection;

/**
 * Observable interface for the phylopanel selection.
 * 
 * @author Chak Shun
 *
 */
public interface PhyloChosenObservable {

	/**
	 * Registers an observer.
	 * 
	 * @param o
	 *            The observer which is registered
	 */
	void registerObserver(PhyloChosenObserver o);

	/**
	 * Deletes an observer.
	 * 
	 * @param o
	 *            The observer which is deleted
	 */
	void deleteObserver(PhyloChosenObserver o);

	/**
	 * Notify all registered observers.
	 * 
	 * @param chosen
	 *            The new list of highlighted sources
	 */
	void notifyObservers(Collection<String> chosen);
}
