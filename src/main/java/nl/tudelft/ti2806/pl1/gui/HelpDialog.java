package nl.tudelft.ti2806.pl1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * @author Marissa, Maarten
 * @since 15-6-2015
 */
public class HelpDialog extends JDialog {

	/** The serial version UID. */
	private static final long serialVersionUID = -4612253023180773554L;

	/** The file name of the source text for the 'Using DNApp' tab. */
	private static final String HELP_USING_DNAPP = "usingDNApp.help";

	/** The file name of the source text for the 'Shortcuts' tab. */
	private static final String HELP_SHORTCUTS_FILE = "shortcuts.help";

	/** The size of the help dialog. */
	private static final int WIDTH = 600, HEIGHT = 500;

	/** The padding around the labels. */
	private static final Border EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);

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
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(size);
		setResizable(false);
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

		JLabel info = new JLabel();
		String help = readHelpFile(HELP_USING_DNAPP);
		info.setText(help);
		pack();
		info.setBorder(EMPTY_BORDER);
		JScrollPane jsp = new JScrollPane(info,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ret.add(jsp, BorderLayout.CENTER);
		return ret;
	}

	/**
	 * @return The panel with information about the available shortcuts.
	 */
	private JPanel makeShortcuts() {
		JPanel ret = new JPanel(new BorderLayout());
		JLabel info = new JLabel();
		info.setText(readHelpFile(HELP_SHORTCUTS_FILE));
		info.setBorder(EMPTY_BORDER);
		ret.add(info, BorderLayout.NORTH);
		return ret;
	}

	/**
	 * @param fileName
	 *            The file name of the help file.
	 * @return The content of the help file.
	 */
	private String readHelpFile(final String fileName) {
		InputStream is = HelpDialog.class.getClassLoader().getResourceAsStream(
				fileName);
		Scanner sc = new Scanner(is, "UTF-8");
		StringBuilder sb = new StringBuilder();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			sb.append(line); // append("<br>");
		}
		sc.close();
		return sb.toString();
	}
}
