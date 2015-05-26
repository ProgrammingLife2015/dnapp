package nl.tudelft.ti2806.pl1.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.jfree.ui.ExtensionFileFilter;

/**
 * 
 * @author Maarten
 * @since 20-5-2015
 * @version 1.0
 */
public class ImportDialog extends JFileChooser {

	/** The serial version UID. */
	private static final long serialVersionUID = -3764514151291068712L;

	/** The type of files this dialog will be able to load. */
	private ImportType filterType;

	/**
	 * Initialize the import dialog.
	 * 
	 * @param type
	 *            The type of files this dialog should be able to load.
	 */
	public ImportDialog(final ImportType type) {
		this(type, (File) null);
	}

	/**
	 * Initialize the import dialog.
	 * 
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
	 * Enumeration of the type of files the application can import. All types
	 * have a file filter.
	 * 
	 * @author Maarten
	 * @since 20-5-2015
	 * @version 1.0
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
		},

		/**
		 * 
		 */
		PHYLO {
			@Override
			public FileFilter getFileFilter() {
				return new ExtensionFileFilter("Newick file", "nwk");
			}

			@Override
			public String getTitle() {
				return "Load phylogenetic tree file";
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
