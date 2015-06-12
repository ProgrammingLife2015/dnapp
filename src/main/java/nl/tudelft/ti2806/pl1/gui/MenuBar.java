package nl.tudelft.ti2806.pl1.gui;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	}

	/**
	 * Creates and fills the file menu.
	 * 
	 * @return the file menu
	 */
	private JMenu fileMenu() {
		JMenu ret = new JMenu("File");
		ret.add(makeMI("Import", null, 'I',
				"Import a sequence graph (.node.graph and .edge.graph)",
				Event.IMPORT_FILE));
		ret.add(makeMI("Open database", null, 'O',
				"Open a graph database file", Event.OPEN_GRAPH_DB));

		JMenu export = new JMenu("Export graph layout as...") {
			/** The serial version UID. */
			private static final long serialVersionUID = 6733151149511110189L;

			@Override
			public boolean isEnabled() {
				return window.getContent().isGraphLoaded();
			}
		};
		export.setMnemonic('E');
		JMenuItem exportDGS = makeMI("DGS format", null, 'D', null,
				Event.WRITE_TO_DGS);
		export.add(exportDGS);
		ret.add(export);
		ret.add(makeMI("Exit", null, 'E', "Exit the program", Event.EXIT_APP));
		return ret;
	}

	/**
	 * Creates and fills the edit menu.
	 * 
	 * @return the edit menu
	 */
	private JMenu editMenu() {
		JMenu ret = new JMenu("Edit");
		// ret.add(makeMI("?", null, '?', "?", null));
		return ret;
	}

	/**
	 * Creates and fills the view menu.
	 * 
	 * @return the view menu
	 */
	private JMenu viewMenu() {
		JMenu ret = new JMenu("View");
		final JCheckBoxMenuItem showToolBar = new JCheckBoxMenuItem(
				"Show tool bar", true);
		showToolBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				window.getToolBar().setVisible(showToolBar.isSelected());
				window.revalidate();
			}
		});
		ret.add(showToolBar);

		final JCheckBoxMenuItem showOptionPane = new JCheckBoxMenuItem(
				"Show option panel", false) {
			/** The serial version UID. */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isEnabled() {
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
		ret.add(showOptionPane);
		return ret;
	}

	/**
	 * Creates and sets up a menu item.
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
	 * @return The menu item created.
	 */
	private JMenuItem makeMI(final String text, final String iconName,
			final char mnemonic, final String toolTipText, final Event action) {
		JMenuItem ret = new JMenuItem();
		ret.setText(text);
		ret.setMnemonic(mnemonic);
		ret.setToolTipText(toolTipText);
		ret.addActionListener(action);
		if (iconName != null) {
			String imgLocation = "images/" + iconName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				ret.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}
		return ret;
	}
}
