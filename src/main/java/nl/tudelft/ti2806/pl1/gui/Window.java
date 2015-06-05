package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.UIManager;

import nl.tudelft.ti2806.pl1.gui.contentpane.Content;
import nl.tudelft.ti2806.pl1.gui.optionpane.OptionsPane;

/**
 * @author Maarten
 *
 */
public class Window extends JFrame implements Observer {

	/** The serial version UID. */
	private static final long serialVersionUID = -2702972120954333899L;

	/** The window settings. */
	private WindowSettings settings;

	/** The menu bar of the window. */
	private MenuBar menuBar;

	/** The tool bar of the window. */
	private ToolBar toolBar;

	/** The left panel showing options. */
	private OptionsPane optionPanel;

	/** The main pane of the window. */
	private Content content;

	/** The status bar of the window. */
	private StatusBar statusBar;

	/**
	 * Initiate a new Window with default settings.
	 */
	public Window() {
		this(new WindowSettings());
	}

	/**
	 * Initiate a new Window with custom settings.
	 * 
	 * @param wSettings
	 *            The window settings to be applied.
	 */
	public Window(final WindowSettings wSettings) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// UIManager.put("swing.boldMetal", Boolean.FALSE);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		setWindowSettings(wSettings);

		Event.setWindow(this);

		toolBar = new ToolBar();
		add(toolBar, BorderLayout.NORTH);

		// ((BasicToolBarUI) toolBar.getUI()).setFloating(true,
		// new Point(100, 100));

		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);

		optionPanel = new OptionsPane(this);
		optionPanel.setVisible(false);
		add(optionPanel, BorderLayout.WEST);

		content = new Content(this);
		add(content, BorderLayout.CENTER);

		menuBar = new MenuBar(this);
		setJMenuBar(menuBar);

		// now let's call all cross dependent things.
		callAfterInitialization();

		addComponentListener(new WindowEvents(this));

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Calls child component methods which have cross dependencies with other
	 * child components in the window.
	 */
	private void callAfterInitialization() {
		optionPanel.componentsLoaded();
	}

	/**
	 * Sets and applies new window settings.
	 * 
	 * @param newWindowSettings
	 *            The new window settings to be applied.
	 */
	public final void setWindowSettings(final WindowSettings newWindowSettings) {
		this.settings = newWindowSettings;
		settings.addObserver(this);
		applyWindowSettings();
	}

	/**
	 * Applies the window settings.
	 */
	private void applyWindowSettings() {
		setTitle(settings.getTitle());
		setMinimumSize(settings.getMinimumSize());
		setSize(settings.getSize());
		setPreferredSize(settings.getSize());
		pack();
		revalidate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void update(final Observable o, final Object arg) {
		applyWindowSettings();
	}

	/**
	 * Gets called from the windows events class when the window is resized.
	 */
	public final void resized() {
		Event.statusBarRight("[" + (int) getSize().getWidth() + ","
				+ (int) getSize().getHeight() + "]");
	}

	// ***** Getters for the high level window elements. ***** //

	/**
	 * @return the optionPanel
	 */
	public final OptionsPane optionPanel() {
		return optionPanel;
	}

	/**
	 * @return the content
	 */
	public final Content content() {
		return content;
	}

	/**
	 * @return the toolBar
	 */
	public final ToolBar toolBar() {
		return toolBar;
	}

	/**
	 * @return the statusBar
	 */
	public final StatusBar statusBar() {
		return statusBar;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
