package nl.tudelft.ti2806.pl1.geneAnnotation;

import java.util.Map;

import nl.tudelft.ti2806.pl1.mutation.ResistanceMutation;

/**
 * @author Maarten
 * @since 17-6-2015
 */
public interface ResistanceMutationObserver {

	/**
	 * Lets the observer know a reference gene storage instance has been
	 * changed.
	 * 
	 * @param rgs
	 *            The reference gene storage added.
	 */
	void update(Map<Long, ResistanceMutation> rgs);

}
