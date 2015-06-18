package nl.tudelft.ti2806.pl1.file;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Maarten
 * @since 22-5-2015
 */
public class ExportDialog extends JFileChooser {

	/** The serial version UID. */
	private static final long serialVersionUID = -3764514151291068712L;

	/**
	 * Initialize the export dialog.
	 */
	public ExportDialog() {
		super();
		setDialogTitle("Choose a folder to write the graph file to");
		setDialogType(JFileChooser.SAVE_DIALOG);
		setFileSelectionMode(JFileChooser.FILES_ONLY); // default
		setCurrentDirectory(null);
		setAcceptAllFileFilterUsed(false);
		addChoosableFileFilter(new FileNameExtensionFilter("DGS graph format",
				"dgs"));
	}
}
