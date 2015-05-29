package nl.tudelft.ti2806.pl1.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import com.horstmann.corejava.GBC;

/**
 * 
 * @author Maarten
 * @since 22-5-2015
 * @version 1.0
 */
public class ProgressDialog extends JDialog {

	/** The serial version UID. */
	private static final long serialVersionUID = -4522422165647753236L;

	/** The default dialog title. */
	private static final String DEFAULT_TITLE = "Progress...";

	/** The size of the dialog. */
	private static final Dimension SIZE = new Dimension(400, 100);

	/** The size of the progress bar. */
	private static final Dimension BAR_SIZE = new Dimension(350, 20);

	/** The progress bar. */
	private JProgressBar jpb;

	/**
	 * Initializes a modeless progress dialog with the default title and
	 * centered in the screen.
	 */
	public ProgressDialog() {
		this((Frame) null, DEFAULT_TITLE, false);
	}

	/**
	 * Initializes a progress dialog relative to a given owner frame.
	 * 
	 * @param owner
	 *            the Frame from which the dialog is displayed title
	 * @param title
	 *            the String to display in the dialog's title bar
	 * @param modal
	 *            specifies whether dialog blocks user input to other top-level
	 *            windows when shown. If true, the modality type property is set
	 *            to DEFAULT_MODALITY_TYPE otherwise the dialog is modeless
	 * 
	 * @see JDialog#JDialog(Frame, String, boolean)
	 */
	public ProgressDialog(final Frame owner, final String title,
			final boolean modal) {
		super(owner, title, modal);
		setLocationRelativeTo(owner);
		setSize(SIZE);
		setBackground(Color.ORANGE);
		setResizable(false);
		setLayout(new GridBagLayout());
		jpb = new JProgressBar();
		jpb.setIndeterminate(true);
		jpb.setPreferredSize(BAR_SIZE);
		add(jpb, new GBC(0, 0));
		revalidate();
	}

	/**
	 * Shows the progress dialog.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Hides and disposes the progress dialog.
	 */
	public final void end() {
		setVisible(false);
		dispose();
	}

}
