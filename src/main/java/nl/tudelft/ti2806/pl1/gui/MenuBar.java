package nl.tudelft.ti2806.pl1.gui;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	 * 
	 */
	private JMenu file = fileMenu(), edit = editMenu(), view = viewMenu();

	/**
	 * 
	 */
	public MenuBar() {
		this.add(file);
		this.add(edit);
		this.add(view);
	}

	/**
	 * 
	 * @return the file menu
	 */
	private JMenu fileMenu() {
		JMenu ret = new JMenu("File");
		ret.add(makeMI("Import...", null, 'I', "Import a graph file.", null));
		ret.add(makeMI("Exit", null, 'E', "Exit the program.", Event.EXIT_APP));
		return ret;
	}

	/**
	 * 
	 * @return the edit menu
	 */
	private JMenu editMenu() {
		JMenu ret = new JMenu("Edit");
		ret.add(makeMI("?", null, '?', "?", null));
		return ret;
	}

	/**
	 * 
	 * @return the view menu
	 */
	private JMenu viewMenu() {
		JMenu ret = new JMenu("View");
		ret.add(makeMI("?", null, '?', "?", null));
		return ret;
	}

	/**
	 * 
	 * @param text
	 *            The text to show.
	 * @param iconName
	 *            The name of the icon file.
	 * @param mnemonic
	 *            The mnemonic (key)
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
		JMenuItem mi = new JMenuItem();
		mi.setText(text);
		mi.setMnemonic(mnemonic);
		mi.setToolTipText(toolTipText);
		mi.addActionListener(action);

		if (iconName != null) {
			String imgLocation = "images/" + iconName;
			URL imageURL = ToolBar.class.getResource(imgLocation);
			if (imageURL != null) {
				mi.setIcon(new ImageIcon(imageURL, text));
			} else {
				System.err.println("Resource not found: " + imgLocation);
			}
		}
		return mi;
	}
}
