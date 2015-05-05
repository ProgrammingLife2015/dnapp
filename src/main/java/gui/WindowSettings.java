package gui;

import java.awt.Dimension;
import java.util.Observable;

/**
 * @author Maarten
 *
 */
public class WindowSettings extends Observable {

	/**
	 * The minimum size of the window.
	 */
	private Dimension minimumSize = new Dimension(Defaults.minSizeX(),
			Defaults.minSizeY());

	/**
	 * The window title.
	 */
	private String title = Defaults.title();

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
		this();
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
	 * @return an instance of window settings with all variables set to default
	 *         values.
	 */
	public static final WindowSettings getDefaultSettings() {
		return new WindowSettings();
	}

}
