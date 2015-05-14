package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import nl.tudelft.ti2806.pl1.main.Start;

/**
 * @author Maarten
 *
 */
public class Window extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2702972120954333899L;

	/**
	 * The window settings.
	 */
	private WindowSettings settings;

	/**
	 * The menu bar of the window.
	 */
	private JMenuBar menuBar;

	/**
	 * The tool bar of the window.
	 */
	private ToolBar toolBar;

	/**
	 * The left panel showing options.
	 */
	private OptionsPane optionPanel;

	/**
	 * The main pane of the window.
	 */
	private Content content;

	/**
	 * The status bar of the window.
	 */
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

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		setWindowSettings(wSettings);

		Event.setWindow(this);

		menuBar = new MenuBar();
		setJMenuBar(menuBar);

		toolBar = new ToolBar();
		add(toolBar, BorderLayout.NORTH);

		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);

		content = new Content(this);
		add(content, BorderLayout.CENTER);

		optionPanel = new OptionsPane(this);
		add(optionPanel, BorderLayout.WEST);
		System.out.println("Added option panel");

		addComponentListener(new WindowEvents(this));

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
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
	 * @param o
	 *            The changed observable object.
	 * @param arg
	 *            Optional arguments.
	 */
	public final void update(final Observable o, final Object arg) {
		applyWindowSettings();
	}

	/**
	 * 
	 */
	public final void resized() {
		statusBar.right("[" + (int) getSize().getWidth() + ","
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

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		Start.main(args);
	}

}
