package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.util.Map;

/**
 * @author Maarten
 * @since 17-6-2015
 */
public interface MetaDataLoadObserver {

	/**
	 * Lets the observer know a reference gene storage instance has been
	 * changed.
	 * 
	 * @param rgs
	 *            The reference gene storage added.
	 */
	void update(Map<Integer, String> rgs);

}
