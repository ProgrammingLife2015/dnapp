package nl.tudelft.ti2806.pl1.gui;

import java.awt.Dimension;
import java.util.Observable;

/**
 * @author Maarten
 *
 */
public class WindowSettings extends Observable {

	/**
	 * 
	 */
	private static final int DEFAULT_MIN_SIZE_X = 500,
			DEFAULT_MIN_SIZE_Y = 350;

	/**
	 * 
	 */
	private static final int DEFAULT_SIZE_X = 700, DEFAULT_SIZE_Y = 500;

	/**
	 * 
	 */
	private static final String DEFAULT_TITLE = "DN/App Genome Browser";

	/**
	 * The minimum size of the window.
	 */
	private Dimension minimumSize = new Dimension(DEFAULT_MIN_SIZE_X,
			DEFAULT_MIN_SIZE_Y);

	/**
	 * The initial size of the window.
	 */
	private Dimension size = new Dimension(DEFAULT_SIZE_X, DEFAULT_SIZE_Y);

	/**
	 * The window title.
	 */
	private String title = DEFAULT_TITLE;

	/**
	 * Creates a new instance of window settings with all variables set to
	 * default values.
	 */
	public WindowSettings() {
	}

	/**
	 * Creates a new instance of window settings with a custom title.
	 * 
	 * @param customTitle
	 *            the window title.
	 */
	public WindowSettings(final String customTitle) {
		setTitle(customTitle);
	}

	/**
	 * @return the minimum size.
	 */
	public final Dimension getMinimumSize() {
		return minimumSize;
	}

	/**
	 * @param newMinimumSize
	 *            the minimum size to set.
	 */
	public final void setMinimumSize(final Dimension newMinimumSize) {
		this.minimumSize = newMinimumSize;
		notifyObservers();
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param newTitle
	 *            the title to set
	 */
	public final void setTitle(final String newTitle) {
		this.title = newTitle;
		notifyObservers();
	}

	/**
	 * @return the size
	 */
	public final Dimension getSize() {
		return size;
	}

	/**
	 * @param newSize
	 *            the size to set
	 */
	public final void setSize(final Dimension newSize) {
		this.size = newSize;
	}

	/**
	 * @return an instance of window settings with all variables set to default
	 *         values.
	 */
	public static final WindowSettings getDefaultSettings() {
		return new WindowSettings();
	}

}
