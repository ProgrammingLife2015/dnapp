package nl.tudelft.ti2806.pl1.exceptions;

import nl.tudelft.ti2806.pl1.gui.Event;

/**
 * @author Maarten
 * @since 29-5-2015
 * @version 1.0
 */
public class ApplicationException extends RuntimeException {

	/** The serial version UID. */
	private static final long serialVersionUID = 1017534236209952985L;

	/**
	 * @param message
	 *            The message to show in the status bar.
	 */
	public ApplicationException(final String message) {
		super(message);
		Event.statusBarError(message);
	}

}
