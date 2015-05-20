/**
 * 
 */
package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Maarten
 * @since 20-5-2015
 * @version 1.0
 *
 */
public class ExportDialog extends JFileChooser {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3764514151291068712L;

	/**
	 * 
	 */
	public ExportDialog() {
		super();
		setDialogTitle("Choose a folder to write the graph file to");
		setDialogType(JFileChooser.SAVE_DIALOG);
		setFileSelectionMode(JFileChooser.FILES_ONLY); // default
		setCurrentDirectory(null);
		setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter extDGS = new FileNameExtensionFilter(
				"DGS graph format", "dgs");
		// FileNameExtensionFilter extDGS = new FileNameExtensionFilter(
		// "DGS graph format", "dgs");
		// FileNameExtensionFilter extDGS = new FileNameExtensionFilter(
		// "DGS graph format", "dgs");
		// FileNameExtensionFilter extDGS = new FileNameExtensionFilter(
		// "DGS graph format", "dgs");

		addChoosableFileFilter(extDGS);
	}

	/**
	 * @param currentDirectoryPath
	 */
	public ExportDialog(final String currentDirectoryPath) {
		super(currentDirectoryPath);
	}

	/**
	 * @param currentDirectory
	 */
	public ExportDialog(final File currentDirectory) {
		super(currentDirectory);
	}

}
