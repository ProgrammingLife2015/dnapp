package nl.tudelft.ti2806.pl1.exceptions;

/**
 * @author Maarten
 */
public class InvalidGenomeIdException extends ApplicationException {

	/** The serial version UID. */
	private static final long serialVersionUID = 6916229072509410254L;

	/**
	 * @param genomeId
	 *            The genome id/name which is not present.
	 */
	public InvalidGenomeIdException(final String genomeId) {
		super("Genome " + genomeId
				+ " is not available in this sequence graph.");
	}

}
