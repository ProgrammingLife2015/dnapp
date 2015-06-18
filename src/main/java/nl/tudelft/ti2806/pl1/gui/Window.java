package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.tudelft.ti2806.pl1.gui.contentpane.Content;
import nl.tudelft.ti2806.pl1.gui.contentpane.ContentLoadedObserver;
import nl.tudelft.ti2806.pl1.gui.optionpane.OptionsPane;

/**
 * @author Maarten
 *
 */
public class Window extends JFrame implements Observer, ContentLoadedObserver {

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

	/** The help menu. **/
	private HelpDialog helpDialog;

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
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		setWindowSettings(wSettings);

		AppEvent.setWindow(this);

		helpDialog = new HelpDialog(this);

		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);

		optionPanel = new OptionsPane(this);
		optionPanel.setVisible(false);
		add(optionPanel, BorderLayout.WEST);

		content = new Content(this);
		content.setVisible(false);
		content.registerObserver(this);
		add(content, BorderLayout.CENTER);

		toolBar = new ToolBar(this);
		add(toolBar, BorderLayout.NORTH);

		menuBar = new MenuBar(this);
		setJMenuBar(menuBar);

		// now let's call all cross dependent things.
		callAfterInitialization();

		addComponentListener(new WindowEvents(this));
		addWindowListener(new CloseConfirmationAdapter(this));

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * @author Maarten
	 * @since 17-6-2015
	 */
	final class CloseConfirmationAdapter extends WindowAdapter {

		/** The window. */
		private Window window;

		/**
		 * Initialize the close confirmation adapter.
		 * 
		 * @param w
		 *            The window.
		 */
		public CloseConfirmationAdapter(final Window w) {
			this.window = w;
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			if (JOptionPane.showConfirmDialog(window,
					"Are you sure you want to close the application?",
					"Exit DN/App?", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				AppEvent.EXIT_APP.actionPerformed(null);
			}
		}
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

	@Override
	public void phyloLoaded() {
		content.setVisible(true);
	}

	@Override
	public void graphLoaded() {
		content.setVisible(true);
		optionPanel.setVisible(true);
	}

	/**
	 * Gets called from the windows events class when the window is resized.
	 */
	public final void resized() {
		AppEvent.statusBarRight("[" + (int) getSize().getWidth() + ","
				+ (int) getSize().getHeight() + "]");
	}

	// ***** Getters for the high level window elements. ***** //

	/**
	 * @return the optionPanel
	 */
	public final OptionsPane getOptionPanel() {
		return optionPanel;
	}

	/**
	 * @return the content
	 */
	public final Content getContent() {
		return content;
	}

	/**
	 * @return the toolBar
	 */
	public final ToolBar getToolBar() {
		return toolBar;
	}

	/**
	 * @return the statusBar
	 */
	public final StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @return the help menu
	 */
	public HelpDialog getHelpDialog() {
		return helpDialog;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}

}
