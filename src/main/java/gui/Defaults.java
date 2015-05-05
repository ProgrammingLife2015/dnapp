package gui;

/**
 * @author Maarten
 *
 */
public final class Defaults {

	/**
	 * 
	 */
	private static final int MIN_SIZE_X = 500;

	/**
	 * 
	 */
	private static final int MIN_SIZE_Y = 350;

	/**
	 * 
	 */
	private static final String TITLE = "Genome browser";

	/**
	 * 
	 */
	private Defaults() {
	}

	/**
	 * 
	 * @return the min size x
	 */
	public static int minSizeX() {
		return MIN_SIZE_X;
	}

	/**
	 * 
	 * @return the min size y
	 */
	public static int minSizeY() {
		return MIN_SIZE_Y;
	}

	/**
	 * 
	 * @return the title
	 */
	public static String title() {
		return TITLE;
	}

}
