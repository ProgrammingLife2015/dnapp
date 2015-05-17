package nl.tudelft.ti2806.pl1.main;

import javax.swing.SwingUtilities;

import nl.tudelft.ti2806.pl1.gui.Window;

/**
 * @author Maarten
 *
 */
public final class Start {

	/**
	 * Private constructor to prevent this utility class being instantiated.
	 */
	private Start() {
	}

	/**
	 * @param args
	 *            jwz
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}

}
