package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.corejava.GBC;

/**
 * @author Marissa, Maarten
 * @since 15-6-2015
 */
public class HelpDialog extends JDialog {

	/** The serial version UID. */
	private static final long serialVersionUID = -4612253023180773554L;

	/** The size of the help dialog. */
	private static final Dimension SIZE = new Dimension(400, 300);

	/** The file name of the source text for the 'Using DNApp' tab. */
	private static final String USING_DNAPP_HELP_FILE = "usingDNApp.help";

	/** The content panel. */
	private JPanel content;

	/** The tabs. */
	private JTabbedPane tabs;

	/**
	 * Initialize the help dialog.
	 * 
	 * @param window
	 *            The parent application window.
	 */
	public HelpDialog(final Window window) {
		super(window);
		this.setTitle("Help");
		setLayout(new BorderLayout());
		setPreferredSize(SIZE);
		setMinimumSize(SIZE);

		content = new JPanel(new BorderLayout());

		tabs = new JTabbedPane();
		tabs.addTab("Using DN/App", makeUsingDNApp());
		tabs.addTab("Keyboard shortcuts", makeShortcuts());

		content.add(tabs, BorderLayout.CENTER);

		add(content, BorderLayout.CENTER);
	}

	/**
	 * @return The panel with information about how to use this application.
	 */
	private JPanel makeUsingDNApp() {
		JPanel ret = new JPanel(new BorderLayout());
		InputStream is = HelpDialog.class.getClassLoader().getResourceAsStream(
				USING_DNAPP_HELP_FILE); // TODO fix...
		Scanner sc = new Scanner(is, "UTF-8");
		StringBuilder sb = new StringBuilder("<html>");
		// System.out.println(sc);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			sb.append(line).append("<br>");
			// System.out.println("READ LINE = " + line);
		}
		sc.close();
		sb.append("</html>");
		// System.out.println(sb.toString());
		JLabel info = new JLabel(sb.toString());
		ret.add(info, BorderLayout.CENTER);
		return ret;
	}

	/**
	 * @return The panel with information about the available shortcuts.
	 */
	private JPanel makeShortcuts() {
		JPanel ret = new JPanel(new GridBagLayout());

		String[][] info = { { "Shortcuts: ", "" }, { "F1", "Help" },
				{ "CTRL + R", "Reset to default view (zoom level)" },
				{ "CTRL + C", "Close the application" },
				{ "CTRL + O", "Open a file" },
				{ "CTRL + UP", "Go up a zoomlevel" },
				{ "CTRL + DOWN", "Go down a zoomlevel" } };

		for (int i = 0; i < info.length; i++) {
			for (int j = 0; j < info[0].length; j++) {
				ret.add(new JLabel(info[i][j]), new GBC(j, i));
			}
		}
		return ret;
	}
}
