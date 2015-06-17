package nl.tudelft.ti2806.pl1.gui;

import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
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

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3046759850795865308L;

	/**
	 * The window this menu bar is part of.
	 */
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
		this.add(editMenu());
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
		JMenuItem imp = new JMenuItem();
		setMenuItem(imp, "Import", null, 'I',
				"Import a sequence graph (.node.graph and .edge.graph)",
				Event.IMPORT_FILE);
		setAcc(imp,
				KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		ret.add(imp);

		// JMenu export = new JMenu("Export graph layout as...") {
		// /** The serial version UID. */
		// private static final long serialVersionUID = 6733151149511110189L;
		//
		// @Override
		// public boolean isEnabled() {
		// return window.getContent().isGraphLoaded();
		// }
		// };
		// export.setMnemonic('E');
		// JMenuItem exportDGS = new JMenuItem();
		// setMenuItem(exportDGS, "DGS format", null, 'D', null,
		// Event.WRITE_TO_DGS);
		// export.add(exportDGS);
		// ret.add(export);

		JMenuItem exit = new JMenuItem();
		setMenuItem(exit, "Exit", null, 'E', "Exit the program", Event.EXIT_APP);
		setAcc(exit,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		ret.add(exit);

		return ret;
	}

	/**
	 * Creates and fills the info menu.
	 * 
	 * @return the info menu
	 */
	private JMenu helpMenu() {
		JMenu ret = new JMenu("Help");
		JMenuItem help = new JMenuItem();
		setMenuItem(help, "Help", null, 'h', "Press to show shortcuts",
				Event.HELP);
		setAcc(help, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		ret.add(help);
		return ret;
	}

	/**
	 * Creates and fills the edit menu.
	 * 
	 * @return the edit menu
	 */
	private JMenu editMenu() {
		JMenu ret = new JMenu("Edit");
		return ret;
	}

	/**
	 * Creates and fills the view menu.
	 * 
	 * @return the view menu
	 */
	private JMenu viewMenu() {
		JMenu ret = new JMenu("View");

		JMenuItem reset = new LoadedMenuItem(window);
		setMenuItem(reset, "Reset view", null, 'R', "Reset to default view.",
				Event.RESET_GRAPH);
		setAcc(reset, KeyStroke.getKeyStroke(reset.getMnemonic(),
				java.awt.Event.CTRL_MASK));
		ret.add(reset);

		JMenuItem zoomlevelplus = new LoadedMenuItem(window);
		setMenuItem(zoomlevelplus, "Up zoomlevel", null, 'u',
				"Go to next zoomlevel", Event.UPZOOMLEVEL);
		setAcc(zoomlevelplus, KeyStroke.getKeyStroke(KeyEvent.VK_UP,
				java.awt.Event.CTRL_MASK));
		ret.add(zoomlevelplus);

		JMenuItem zoomlevelminus = new LoadedMenuItem(window);
		setMenuItem(zoomlevelminus, "Down zoomlevel", null, 'd',
				"Go to previous zoomlevel", Event.DOWNZOOMLEVEL);
		setAcc(zoomlevelminus, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
				java.awt.Event.CTRL_MASK));
		ret.add(zoomlevelminus);

		ret.add(makeShowToolBar());
		ret.add(makeShowOptionPane());
		return ret;
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
	 * @param iconName
	 *            The name of the icon file in the resources/images folder
	 * @param mnemonic
	 *            The mnemonic (fast key)
	 * @param toolTipText
	 *            The tool tip.
	 * @param action
	 *            The event name.
	 * 
	 * @see Event
	 */
	private void setMenuItem(final JMenuItem item, final String text,
			final String iconName, final char mnemonic,
			final String toolTipText, final Event action) {
		item.setText(text);
		item.setMnemonic(mnemonic);
		item.setToolTipText(toolTipText);
		item.addActionListener(action);
		if (iconName != null) {
			String imgLocation = "images/" + iconName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				item.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}
	}

	/**
	 * 
	 * @param item
	 *            JMenuItem we want to add an acceleator to.
	 * @param keyStroke
	 *            The set of keys it will use.
	 */
	private void setAcc(final JMenuItem item, final KeyStroke keyStroke) {
		item.setAccelerator(keyStroke);
	}
}
