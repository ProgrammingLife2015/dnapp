package nl.tudelft.ti2806.pl1.exceptions;

/**
 * Lets you throw a custom error on an invalid file format.
 * 
 * @author Marissa, Mark
 * @since 25-04-2015
 */
public class InvalidFileFormatException extends ApplicationException {

	/**
	 * The serial version of this exception.
	 */
	private static final long serialVersionUID = -3175084850174905842L;

	/**
	 * Throw this exception when the file format is incorrect and you want to
	 * give a message with this exception.
	 * 
	 * @param message
	 *            The message which goes with the exception
	 */
	public InvalidFileFormatException(final String message) {
		super(message);
	}
}
