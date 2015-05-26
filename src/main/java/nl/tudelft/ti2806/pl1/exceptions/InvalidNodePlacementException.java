package nl.tudelft.ti2806.pl1.exceptions;

/**
 * Placing node at the wrong place.
 * 
 * @author Marissa
 *
 */
public class InvalidNodePlacementException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3138127803765645767L;

	/**
	 */
	public InvalidNodePlacementException() {
		super("Trying to place a node where you cannot place it.");
	}

}
