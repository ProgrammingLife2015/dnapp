/**
 * 
 */
package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * 
 * @author Maarten
 * @since 20-5-2015
 * @version 1.0
 *
 */
public class ImportDialog extends JFileChooser {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3764514151291068712L;

	/**
	 * 
	 */
	private static ImportType filterType;

	/**
	 * @param type
	 *            The type of files this dialog should be able to load.
	 */
	public ImportDialog(final ImportType type) {
		this(type, (File) null);
	}

	/**
	 * @param type
	 *            The type of files this dialog should be able to load.
	 * @param currentDirectory
	 *            The directory to start from.
	 */
	public ImportDialog(final ImportType type, final File currentDirectory) {
		super(currentDirectory);
		filterType = type;
		setDialogType(JFileChooser.OPEN_DIALOG);
		setFileFilter(filterType.getFileFilter());
		setDialogTitle(filterType.getTitle());
		setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	/**
	 * 
	 * @author Maarten
	 * @since 20-5-2015
	 * @version 1.0
	 *
	 */
	public enum ImportType {

		/**
		 * Only graph node files will be shown.
		 */
		NODES {
			@Override
			public FileFilter getFileFilter() {
				return new GraphNodeFileFilter();
			}

			@Override
			public String getTitle() {
				return "Load node file";
			}
		},

		/**
		 * Only graph edge files will be shown.
		 */
		EDGES {
			@Override
			public FileFilter getFileFilter() {
				return new GraphEdgeFileFilter();
			}

			@Override
			public String getTitle() {
				return "Load edge file";
			}
		};

		/**
		 * @return a file filter accepting only files for this type of
		 *         importable files.
		 */
		public abstract FileFilter getFileFilter();

		/**
		 * @return the file chooser dialog title to show for this type of
		 *         importable files.
		 */
		public abstract String getTitle();
	}

}
