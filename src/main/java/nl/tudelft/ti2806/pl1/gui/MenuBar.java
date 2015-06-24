package nl.tudelft.ti2806.pl1.gui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Maarten
 *
 */
public class MenuBar extends JMenuBar {

	/** The serial version UID. */
	private static final long serialVersionUID = -3046759850795865308L;

	/** The window this menu bar is part of. */
	private Window window;

	/**
	 * Initializes the menu bar.
	 * 
	 * @param w
	 *            The window this menu bar is part of.
	 */
	public MenuBar(final Window w) {
		this.window = w;
		this.add(fileMenu());
		this.add(viewMenu());
		this.add(helpMenu());
	}

	/**
	 * Creates and fills the file menu.
	 * 
	 * @return the file menu
	 */
	private JMenu fileMenu() {
		JMenu ret = new JMenu("File");
		ret.setMnemonic('F');
		JMenuItem impGraph = new JMenuItem();
		setMenuItem(impGraph, "Import graph", 'I',
				"Import a sequence graph (.node.graph and .edge.graph)",
				AppEvent.IMPORT_GRAPH);

		setAcc(impGraph, KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		ret.add(impGraph);

		JMenu export = new JMenu("Export graph layout as...") {
			/** The serial version UID. */
			private static final long serialVersionUID = 6733151149511110189L;

			@Override
			public boolean isEnabled() {
				return window.getContent().isGraphLoaded();
			}
		};
		export.setMnemonic('E');
		JMenuItem exportDGS = new JMenuItem();
		setMenuItem(exportDGS, "DGS format", 'D', null, AppEvent.WRITE_TO_DGS);
		export.add(exportDGS);
		ret.add(export);

		JMenuItem exit = new JMenuItem();
		setMenuItem(exit, "Exit", 'E', "Exit the program", AppEvent.EXIT_APP);
		setAcc(exit, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		ret.add(exit);

		return ret;
	}

	/**
	 * Creates and fills the info menu.
	 * 
	 * @return the info menu.
	 */
	private JMenu helpMenu() {
		JMenu ret = new JMenu("Help");
		ret.setMnemonic('H');
		JMenuItem help = new JMenuItem();
		setMenuItem(help, "Help", 'H', "Press to show shortcuts", AppEvent.HELP);
		setAcc(help, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		ret.add(help);
		return ret;
	}

	/**
	 * Creates and fills the view menu.
	 * 
	 * @return the view menu.
	 */
	private JMenu viewMenu() {
		JMenu ret = new JMenu("View") {

			/** The serial version UID. */
			private static final long serialVersionUID = -8732114494095898765L;

			@Override
			public boolean isEnabled() {
				return window != null && window.getContent().isGraphLoaded();
			}
		};
		ret.setMnemonic('V');

		JMenuItem reset = new JMenuItem();
		setMenuItem(reset, "Reset initial view", 'R',
				"Reset to the initial zoom level view.",
				AppEvent.RESET_INITIAL_LEVEL);
		setAcc(reset,
				KeyStroke.getKeyStroke(reset.getMnemonic(), Event.CTRL_MASK));
		ret.add(reset);

		JMenuItem resetCurrent = new JMenuItem();
		setMenuItem(resetCurrent, "Reset current view", 'C',
				"Reset the current zoom level view.",
				AppEvent.RESET_CURRENT_LEVEL);
		setAcc(resetCurrent, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		ret.add(resetCurrent);

		JMenuItem zoomIn = new JMenuItem(AppEvent.ZOOM_IN);
		setMenuItem(zoomIn, "Zoom in", 'I',
				"<html>Zoom in one zoom level.<br>Nodes might be uncollapsed.",
				null);
		setAcc(zoomIn,
				KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.CTRL_MASK));
		ret.add(zoomIn);
		addKeyBinding(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK,
				AppEvent.ZOOMLEVEL_OUT);
		addKeyBinding(KeyEvent.VK_ADD, Event.CTRL_MASK, AppEvent.ZOOMLEVEL_IN);

		JMenuItem zoomOut = new JMenuItem(AppEvent.ZOOM_OUT);
		setMenuItem(zoomOut, "Zoom out", 'O',
				"<html>Go to previous zoomlevel.<br>Nodes might be collapsed.",
				null);
		setAcc(zoomOut,
				KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Event.CTRL_MASK));
		ret.add(zoomOut);
		ret.add(makeShowToolBar());
		ret.add(makeShowOptionPane());
		return ret;
	}

	/**
	 * @param keyCode
	 *            The key code.
	 * @param modifiers
	 *            The key mask(s).
	 * @param event
	 *            The app event to bind to the key stoke.
	 */
	private void addKeyBinding(final int keyCode, final int modifiers,
			final AppEvent event) {
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(keyCode, modifiers), event.name());

		getActionMap().put(event.name(), new AbstractAction() {

			/** The serial version UID. */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				event.actionPerformed(null);
			}
		});
	}

	/**
	 * @return The show tool bar check box menu item.
	 */
	private JCheckBoxMenuItem makeShowToolBar() {

		final JCheckBoxMenuItem showToolBar = new JCheckBoxMenuItem(
				"Show tool bar", true);
		showToolBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getToolBar().setVisible(showToolBar.isSelected());
				window.revalidate();
			}
		});
		return showToolBar;
	}

	/**
	 * @return The show option pane check box menu item.
	 */
	private JCheckBoxMenuItem makeShowOptionPane() {

		final JCheckBoxMenuItem showOptionPane = new JCheckBoxMenuItem(
				"Show option panel", true) {
			/** The serial version UID. */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return window.getContent().isGraphLoaded();
			}
		};
		showOptionPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getOptionPanel().setVisible(showOptionPane.isSelected());
				window.revalidate();
			}
		});
		return showOptionPane;
	}

	/**
	 * Creates and sets up a menu item.
	 * 
	 * @param item
	 *            The menuItem we want to set properties for.
	 * 
	 * @param text
	 *            The text to show on the menu item.
	 * @param mnemonic
	 *            The mnemonic (fast key)
	 * @param toolTipText
	 *            The tool tip.
	 * @param action
	 *            The event name.
	 * 
	 * @see AppEvent
	 */
	private void setMenuItem(final JMenuItem item, final String text,
			final char mnemonic, final String toolTipText, final AppEvent action) {
		item.setText(text);
		item.setMnemonic(mnemonic);
		item.setToolTipText(toolTipText);
		item.addActionListener(action);
	}

	/**
	 * 
	 * @param item
	 *            JMenuItem we want to add an accelerator to.
	 * @param keyStroke
	 *            The set of keys it will use.
	 */
	private void setAcc(final JMenuItem item, final KeyStroke keyStroke) {
		item.setAccelerator(keyStroke);
	}
}
