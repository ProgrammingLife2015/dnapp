package nl.tudelft.ti2806.pl1.exceptions;

/**
 * @author Maarten
 */
public class InvalidGenomeIdException extends Exception {

	/** The serial version UID. */
	private static final long serialVersionUID = 6916229072509410254L;

	/**
	 * @param id
	 *            The genome id/name requested.
	 */
	public InvalidGenomeIdException(final String id) {
		super("Genome " + id + " is not available in this sequence graph.");
	}

}
