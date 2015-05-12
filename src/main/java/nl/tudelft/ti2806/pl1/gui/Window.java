package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

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
	private JToolBar toolBar;

	/**
	 * The main pane of the window.
	 */
	private JPanel content;

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

		pack();
		setLocationRelativeTo(null);
		// dispose();
		setVisible(true);
		revalidate();
		// repaint();
		// paint(getGraphics());
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
		// pack();
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
	 * Shows an information message in the status bar.
	 * 
	 * @param message
	 *            The info message to show in the status bar.
	 */
	public final void info(final String message) {
		statusBar.info(message);
	}

	/**
	 * Shows an error message in the status bar.
	 * 
	 * @param message
	 *            The error message to show in the status bar.
	 */
	public final void error(final String message) {
		statusBar.error(message);
	}

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// UIManager.put("swing.boldMetal", Boolean.FALSE);
				new Window();
			}
		});
	}

}
