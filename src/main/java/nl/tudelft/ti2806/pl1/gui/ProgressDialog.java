package nl.tudelft.ti2806.pl1.gui;

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

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -4522422165647753236L;

	/**
	 * 
	 */
	private static final String DEFAULT_TITLE = "Progress...";

	/**
	 * 
	 */
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
		setSize(new Dimension(400, 200));
		setResizable(false);
		setLayout(new GridBagLayout());
		jpb = new JProgressBar();
		jpb.setIndeterminate(true);
		jpb.setPreferredSize(new Dimension(300, 30));
		add(jpb, new GBC(0, 0));
		revalidate();
	}

	/**
	 * Shows the progress dialog.
	 */
	public final void start() {
		start(this);
	}

	/**
	 * Shows the progress dialog.
	 */
	private void start(final ProgressDialog me) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				setVisible(true);
				while (true) {
					try {
						Thread.sleep(1);
						// me.repaint();
						// me.revalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	/**
	 * Hides the progress dialog.
	 */
	public final void end() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				setVisible(false);
			}
		});
		t.start();
	}

}
